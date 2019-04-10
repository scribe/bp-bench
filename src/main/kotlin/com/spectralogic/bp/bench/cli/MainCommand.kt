/*
 * ****************************************************************************
 *   Copyright 2014-2019 Spectra Logic Corporation. All Rights Reserved.
 * ***************************************************************************
 */
package com.spectralogic.bp.bench.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findObject
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.google.inject.Inject
import com.google.inject.name.Named

class MainCommand
    @Inject constructor(
        @Named("SustainedWrite") sustainedWriteCommand: CliktCommand,
        @Named("WriteToTape") writeToTapeCommand: CliktCommand,
        @Named("GetFromTape") getFromTapeCommand: CliktCommand
    )
    : CliktCommand(name = "bpBench") {

    init {
        subcommands(
            sustainedWriteCommand,
            writeToTapeCommand,
            getFromTapeCommand
        )
    }

    val verbose by option("-v", "--verbose", help = "enable verbose output").flag()

    override fun run() {
        findObject { verbose }
    }
}