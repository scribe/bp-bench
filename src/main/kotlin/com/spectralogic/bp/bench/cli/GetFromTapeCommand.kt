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
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.prompt
import com.github.ajalt.clikt.parameters.options.validate
import com.spectralogic.ds3client.Ds3ClientBuilder
import com.spectralogic.ds3client.helpers.Ds3ClientHelpers
import com.spectralogic.ds3client.models.common.Credentials

class GetFromTapeCommand: CliktCommand(name = "get") {
    private val endpoint by option(
        "-bp",
        "--blackpearl"
    ).prompt("Black Pearl data path").validate { require(it.isNotEmpty()) { "Black Pearl data path cannot be empty" } }
    private val clientId: String by option(
        "-a",
        "--accessid"
    ).prompt("Black Pearl access id?").validate { require(it.isNotEmpty()) { "User name cannot be empty" } }
    private val key: String by option(
        "-s",
        "--secretkey"
    ).prompt("Black Pearl secret key?").validate { require(it.isNotEmpty()) { "Password cannot be empty" } }
    private val bucket: String by option(
        "-b",
        "--bucket"
    ).prompt("Target Black Pearl bucket").validate { require(it.isNotEmpty()) { "Bucket name cannot be empty" } }

    override fun run() {
        val client = Ds3ClientHelpers.wrap(
            Ds3ClientBuilder.create(endpoint, Credentials(clientId, key))
                .withCertificateVerification(false)
                .build()
        )
        client.startReadAllJob(bucket)
            .transfer { _ -> AzSeekableByteChannel() }
    }
}
