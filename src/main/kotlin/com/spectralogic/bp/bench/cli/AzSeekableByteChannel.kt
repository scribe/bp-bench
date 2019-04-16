/*
 * ****************************************************************************
 *   Copyright 2014-2019 Spectra Logic Corporation. All Rights Reserved.
 * ***************************************************************************
 */
 
package com.spectralogic.bp.bench.cli

import com.spectralogic.ds3client.utils.NotImplementedException
import java.nio.ByteBuffer
import java.nio.channels.SeekableByteChannel

class AzSeekableByteChannel : SeekableByteChannel {

    private var size = 0L
    private var position = 0L
    private var open = true
    private var char = 'a'

    override fun read(dst: ByteBuffer): Int {
        val buffer = dst.asCharBuffer()
        val amountToRead = Math.min(buffer.remaining(), buffer.limit())
        val generator = generateSequence {
            val charToReturn = char

            char = char.inc()
            if (char == '{') {
                char = 'a'
            }
            charToReturn
        }
        generator.take(amountToRead).forEach {
            size += 2
            position += 2
            buffer.put(it)
        }

        return amountToRead * 2
    }

    override fun write(src: ByteBuffer): Int {
        val l = src.remaining()
        size += l
        position += l
        src.position(src.position()+l)
        return l
    }

    override fun position(): Long {
        return position
    }

    override fun position(newPosition: Long): SeekableByteChannel {
        this.position = newPosition
        return this
    }

    override fun size(): Long {
        return size
    }

    override fun truncate(size: Long): SeekableByteChannel {
        return this
    }

    override fun isOpen(): Boolean {
        return open
    }

    override fun close() {
        open = false
    }
}
