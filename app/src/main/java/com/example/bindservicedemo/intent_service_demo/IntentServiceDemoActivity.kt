package com.example.bindservicedemo.intent_service_demo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.bindservicedemo.R
import com.example.bindservicedemo.databinding.ActivityIntentServiceDemoBinding

class IntentServiceDemoActivity : AppCompatActivity() {
    lateinit var binding: ActivityIntentServiceDemoBinding
    private val receiver = GreetingReceiver()

    companion object {
        const val INTENT_ACTION_GREETINGS = "INTENT_ACTION_GREETINGS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intent_service_demo)

        binding.submit.setOnClickListener {
            val name = binding.userName.text.toString()
            if (name.isEmpty()) return@setOnClickListener
            startService(Intent(this, IntentServiceDemo::class.java).putExtra("name", name))
        }
    }

    override fun onStart() {
        super.onStart()
        setReceiver()
    }

    private fun setReceiver() {
        val filter = IntentFilter()
        filter.addAction(INTENT_ACTION_GREETINGS)
        registerReceiver(receiver, filter)
    }

    inner class GreetingReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val greetingText = intent?.getStringExtra("greeting") ?: ""
            binding.output.text = greetingText
        }
    }

    override fun onStop() {
        unregisterReceiver(receiver)
        super.onStop()
    }
}
