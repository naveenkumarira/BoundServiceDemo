package com.example.bindservicedemo.intent_service_demo

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.util.Log

class IntentServiceDemo: IntentService(IntentServiceDemo::class.java.name) {

    //Background process limitations
    //1) Start service from background throws illegal state exception
    //      1.1) It doesn't affect the bound services/foreground services

    override fun onHandleIntent(intent: Intent?) {
        Log.d("IntentService", "onHandleIntent")
        val name = intent?.getStringExtra("message")
        if(name?.isEmpty() == true)  return
        val intent = Intent()
        intent.putExtra("greeting", "Greetings from Google, Hi $name")
        intent.action = IntentServiceDemoActivity.INTENT_ACTION_GREETINGS
        sendBroadcast(intent)
    }
}