/*
 * ****************************************************************************
 *   Copyright 2014-2019 Spectra Logic Corporation. All Rights Reserved.
 * ***************************************************************************
 */

package com.spectralogic.bp.bench.cli

import java.nio.ByteBuffer
import java.nio.channels.ReadableByteChannel
import java.nio.channels.SeekableByteChannel

/**
 * Wraps a non-seekable ReadableByteChannel in order to satisfy the SeekableByteChannel interface.
 * Despite position being tracked, setting the position does not actually perform a seek.
 * This is used in FileSourceImpl to implement the AToZInputStream test tool.
 */
class PositionableReadOnlySeekableByteChannel(private val channel: ReadableByteChannel) : SeekableByteChannel {

    private var size = 0L
    private var position = 0L

    override fun read(dst: ByteBuffer): Int {
        val bytesRead = channel.read(dst)
        size += bytesRead.toLong()
        position += bytesRead.toLong()
        return bytesRead
    }

    override fun write(src: ByteBuffer): Int {
        val l = src.remaining()
        size += l
        position += l
        src.position(src.position() + l)
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
        return channel.isOpen
    }

    override fun close() {
        channel.close()
    }
}