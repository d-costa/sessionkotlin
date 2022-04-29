package org.david.sessionkotlin.backend.channel

import kotlinx.coroutines.channels.Channel
import org.david.sessionkotlin.backend.SKMessage
import java.io.Closeable

/**
 * Uses two channels for two-way communication.
 * [input] is used to receive messages and [output] to send.
 *
 * Two channels must be used to prevent the sender from reading its own messages.
 */
internal data class SKDoubleChannel(val input: Channel<SKMessage>, val output: Channel<SKMessage>) : Closeable {
    suspend fun receive(): SKMessage = input.receive()
    suspend fun send(msg: SKMessage) = output.send(msg)

    /**
     * Closes the output channel.
     */
    override fun close() {
        output.close()
    }
}
