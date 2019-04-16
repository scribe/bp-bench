/*
 * ****************************************************************************
 *   Copyright 2014-2019 Spectra Logic Corporation. All Rights Reserved.
 * ***************************************************************************
 */
/*
 * ****************************************************************************
 *   Copyright 2014-2019 Spectra Logic Corporation. All Rights Reserved.
 * ***************************************************************************
 */
 
package com.spectralogic.bp.bench.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.output.TermUi
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.types.long
import com.spectralogic.ds3client.Ds3ClientBuilder
import com.spectralogic.ds3client.commands.HeadBucketRequest
import com.spectralogic.ds3client.commands.HeadBucketResponse
import com.spectralogic.ds3client.commands.spectrads3.PutBucketSpectraS3Request
import com.spectralogic.ds3client.helpers.Ds3ClientHelpers
import com.spectralogic.ds3client.models.bulk.Ds3Object
import com.spectralogic.ds3client.models.common.Credentials
import com.spectralogic.ds3client.networking.FailedRequestException
import java.io.IOException
import java.util.UUID

class WriteToTapeCommand : CliktCommand(name = "put") {
    companion object {
        val sizeMap = mapOf<String, Long>(
            Pair("b", 1L),
            Pair("Kb", 3L),
            Pair("Mb", 6L),
            Pair("Gb", 9L),
            Pair("Tb", 12L)
        )
    }

    private val endpoint by option(
        "-bp",
        "--blackperl"
    ).prompt("Black Pearl data path").validate { require(it.isNotEmpty()) { "Black Pearl data path cannot be empty" } }
    private val clientId: String by option(
        "-c",
        "--clientid"
    ).prompt("Black Pearl access id?").validate { require(it.isNotEmpty()) { "User name cannot be empty" } }
    private val key: String by option(
        "-p",
        "--password"
    ).prompt("Black Pearl secret key?").validate { require(it.isNotEmpty()) { "Password cannot be empty" } }
    private val bucket: String by option(
        "-b",
        "--bucket"
    ).prompt("Target Black Pearl bucket").validate { require(it.isNotEmpty()) { "Bucket name cannot be empty" } }
    private val itemNumber: Int by option(
        "-n",
        "--number"
    ).int().prompt("Number of files").validate { require(it > 0) { "Number of files must be positive" } }
    private val size: Long by option(
        "-s",
        "--size"
    ).long().prompt("Size of file(s)").validate { require(it >= 0L) { "Size of files must be non-negative" } }
    private val sizeUnits by option("-u", "--units").choice(
        "b",
        "Kb",
        "Mb",
        "Gb",
        "Tb"
    ).prompt("Units for size: (b, Kb, Mb, Gb, Tb")
    private val dataPolicy by option(
        "-d",
        "--datapolicy"
    )
        .prompt()
        .validate { require(it.isNotEmpty()) { "Data policy must not be blank" } }

    override fun run() {
        val client = Ds3ClientHelpers.wrap(
            Ds3ClientBuilder.create(endpoint, Credentials(clientId, key))
                .withCertificateVerification(false)
                .build()
        )
        client.ensureBucketExistsByName(bucket, dataPolicy)
        val job = client.startWriteJob(bucket, ds3ObjectSequence().toList())
        job.transfer { _ -> AzSeekableByteChannel() }
    }

    private var itemName: Long = 0L

    private fun ds3ObjectSequence(): Sequence<Ds3Object> {
        return generateSequence {
            val exponent: Long = sizeMap[sizeUnits]!!
            Ds3Object(
                "bp-benchmark-${itemName++}.txt",
                (size * Math.pow(size.toDouble(), exponent.toDouble())).toLong()
            )
        }.take(itemNumber)
    }
}

fun Ds3ClientHelpers.ensureBucketExistsByName(bucket: String, dataPolicy: String) {
    val response = this.client.headBucket(HeadBucketRequest(bucket))
    if (response.status == HeadBucketResponse.Status.DOESNTEXIST) {
        try {
            this.client.putBucketSpectraS3(PutBucketSpectraS3Request(bucket).withDataPolicyId(dataPolicy))
        } catch (var5: FailedRequestException) {
            if (var5.statusCode != 409) {
                throw var5
            }

            TermUi.echo("Creating $bucket failed because it was created by another thread or process")
        }
    }
}
