package com.spectralogic.bp.bench.cli

import java.io.InputStream

class AZInputStream : InputStream() {
    private var currentCharacter = 'a'

    override fun read(): Int {
        val charToReturn = currentCharacter

        currentCharacter = currentCharacter.inc()
        if (currentCharacter == '{') {
            currentCharacter = 'a'
        }
        return charToReturn.toInt()
    }
}
