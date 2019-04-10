package com.spectralogic.bp.bench.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument

class WriteToTapeCommand : CliktCommand() {
    private val blackPearl: String by argument("<black pearl>")
    override fun run() {
        echo("writeToTape with ${blackPearl}")
    }
}