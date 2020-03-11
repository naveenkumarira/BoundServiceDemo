package com.example.bindservicedemo.intent_service_demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bindservicedemo.R

class IntentServiceDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_service_demo)
        startService(Intent(this, IntentServiceDemo::class.java).putExtra("message", "Hi IntentService"))
    }
}
