package org.david.sessionkotlin.backend.channel

import org.david.sessionkotlin.backend.SKEndpoint
import org.david.sessionkotlin.backend.SKMessage

/**
 * Endpoint implementation with channels.
 */
internal class SKChannelEndpoint(private var chan: SKDoubleChannel) : SKEndpoint {

    override suspend fun readMsg(): SKMessage = chan.receive()
    override suspend fun writeMsg(msg: SKMessage) = chan.send(msg)
    override fun close() = chan.close()
}
