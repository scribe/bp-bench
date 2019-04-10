package com.spectralogic.bp.bench.cli

import com.github.ajalt.clikt.core.CliktCommand

class SustainedWriteCommand: CliktCommand() {

    override fun run() {
        echo("Sustained Write")
    }
}