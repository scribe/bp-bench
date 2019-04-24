/*
 * ****************************************************************************
 *   Copyright 2014-2019 Spectra Logic Corporation. All Rights Reserved.
 * ***************************************************************************
 */
package com.spectralogic.bp.bench

import com.spectralogic.bp.bench.cli.GetFromTapeCommand
import com.spectralogic.bp.bench.cli.MainCommand
import com.spectralogic.bp.bench.cli.WriteToTapeCommand

fun main(args: Array<String>) {
    MainCommand(
        WriteToTapeCommand(),
        GetFromTapeCommand()
    )
        .main(args)
}
