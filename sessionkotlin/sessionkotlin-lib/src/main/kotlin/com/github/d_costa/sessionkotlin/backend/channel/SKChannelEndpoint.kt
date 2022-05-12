package com.github.d_costa.sessionkotlin.backend.channel

import com.github.d_costa.sessionkotlin.backend.endpoint.SKEndpoint
import com.github.d_costa.sessionkotlin.backend.message.SKMessage

/**
 * Endpoint implementation with channels.
 */
internal class SKChannelEndpoint(private var chan: SKDoubleChannel) : SKEndpoint {

    override suspend fun readMsg(): SKMessage = chan.receive()
    override suspend fun writeMsg(msg: SKMessage) = chan.send(msg)
    override fun close() = chan.close()
}
