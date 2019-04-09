/*
 * ****************************************************************************
 *   Copyright 2014-2019 Spectra Logic Corporation. All Rights Reserved.
 * ***************************************************************************
 */
package com.spectralogic.bp.bench

import com.github.ajalt.clikt.core.CliktCommand
import com.google.inject.Guice
import com.spectralogic.bp.bench.cli.MainCommand
import org.jlleitschuh.guice.module


fun main(args: Array<String>) {
    Guice
        .createInjector(
            module {
                bind<CliktCommand>().to<MainCommand>()
            }
        )
        .getInstance(CliktCommand::class.java)
        .main(args)
}
