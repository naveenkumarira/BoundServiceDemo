package com.example.bindservicedemo.duplex_communication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Messenger

class ConvertService: Service() {
    private val messenger = Messenger(ConvertHandler())

    override fun onBind(intent: Intent?): IBinder? {
        return messenger.binder
    }
}