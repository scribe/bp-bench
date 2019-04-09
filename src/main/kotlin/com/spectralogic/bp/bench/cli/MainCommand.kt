/*
 * ****************************************************************************
 *   Copyright 2014-2019 Spectra Logic Corporation. All Rights Reserved.
 * ***************************************************************************
 */
package com.spectralogic.bp.bench.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findObject
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option

class MainCommand : CliktCommand(name = "bpBench") {
    val verbose by option("-v", "--verbose", help = "enable verbose output").flag()

    override fun run() {
        findObject { verbose }
        echo("Test")
    }
}