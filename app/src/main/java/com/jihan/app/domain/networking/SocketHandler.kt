package com.jihan.app.domain.networking

import android.util.Log
import com.jihan.app.domain.utils.Constants.BASE_URL
import com.jihan.app.domain.utils.Constants.TAG
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {

    private  var mSocket: Socket?=null

    @Synchronized
    fun setSocket() {
        try {
           // val option = IO.Options()
           // option.auth = mapOf("token" to token)
            if (mSocket==null){
            mSocket = IO.socket(BASE_URL)
            }
        } catch (err: URISyntaxException) {
            Log.e(TAG, "setSocket: ${err.reason}")
        }
        catch (e:Exception){
            Log.e(TAG, "setSocket: ${e.message}")
        }
    }

    @Synchronized
    fun getSocket(): Socket? {
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        mSocket?.connect()
    }

    @Synchronized
    fun closeConnection() {
        mSocket?.disconnect()
    }


}