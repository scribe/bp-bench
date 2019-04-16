/*
 * ****************************************************************************
 *   Copyright 2014-2019 Spectra Logic Corporation. All Rights Reserved.
 * ***************************************************************************
 */
package com.spectralogic.bp.bench

import com.github.ajalt.clikt.core.CliktCommand
import com.google.inject.Guice
import com.google.inject.name.Named
import com.google.inject.name.Names
import com.spectralogic.bp.bench.cli.GetFromTapeCommand
import com.spectralogic.bp.bench.cli.MainCommand
import com.spectralogic.bp.bench.cli.SustainedWriteCommand
import com.spectralogic.bp.bench.cli.WriteToTapeCommand
import org.jlleitschuh.guice.module


fun main(args: Array<String>) {
    Guice
        .createInjector(
            module {
                bind<CliktCommand>().to<MainCommand>()
                bind<CliktCommand>().annotatedWith(Names.named("GetFromTape")).to<GetFromTapeCommand>()
                bind<CliktCommand>().annotatedWith(Names.named("WriteToTape")).to<WriteToTapeCommand>()
            }
        )
        .getInstance(CliktCommand::class.java)
        .main(args)
}
