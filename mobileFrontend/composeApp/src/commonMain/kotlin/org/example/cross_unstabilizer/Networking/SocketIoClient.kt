package org.example.cross_unstabilizer.Networking

import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URISyntaxException

class SocketClient (val url:String){
    private lateinit var socket: Socket

    fun connect(socketToCallback:Map<String, (Array<Any>) -> Unit> = mapOf()) {
        try {
            socket = IO.socket(url) // Replace with your Socket.IO server URL
            socket.on(Socket.EVENT_CONNECT) {
                println("Connected to Socket.IO server")
                // You can emit events or listen for messages here
                //socket.emit("someEvent", "Hello from KMP!")
            }
            socketToCallback.forEach { (event, callback) ->
                socket.on(event, callback)
            }
           // socket.on("someResponseEvent") { args ->
           //     println("Received response: ${args[0]}")
           // }
            socket.connect()
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    fun disconnect() {
        socket.disconnect()
    }
}
