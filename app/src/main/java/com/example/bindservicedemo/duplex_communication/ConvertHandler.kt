package com.example.bindservicedemo.duplex_communication

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.RemoteException
import java.util.*

class ConvertHandler: Handler() {

    companion object {
        const val TO_UPPER_CASE = 10
        const val TO_UPPER_CASE_RESPONSE = 11
    }
    override fun handleMessage(msg: Message) {
        when(msg.what) {
            TO_UPPER_CASE -> {
                try {
                    val data = msg.data["data"].toString()
                    val responseMessage = Message.obtain(null, TO_UPPER_CASE_RESPONSE)
                    val bundle = Bundle()
                    bundle.putString("responseData", data.toUpperCase(Locale.ROOT))
                    responseMessage.data = bundle
                    msg.replyTo.send(responseMessage)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }
            else -> super.handleMessage(msg)

        }

    }

}