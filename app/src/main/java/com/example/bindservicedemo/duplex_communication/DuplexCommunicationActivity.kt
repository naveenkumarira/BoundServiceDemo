package com.example.bindservicedemo.duplex_communication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bindservicedemo.R
import com.example.bindservicedemo.duplex_communication.ConvertHandler.Companion.TO_UPPER_CASE_RESPONSE


class DuplexCommunicationActivity : AppCompatActivity() {

    //TODO: https://www.coursera.org/lecture/androidapps-2/service-and-activity-communication-via-android-messengers-part-1-Crv2A

    lateinit var inputText: EditText
    lateinit var outputText: TextView
    lateinit var submitButton: Button
    lateinit var serviceConnection: ServiceConnection
    var messenger: Messenger? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_duplex_communication)
        inputText = findViewById(R.id.input)
        outputText = findViewById(R.id.output)
        submitButton = findViewById(R.id.submitButton)


        serviceConnection = object: ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                messenger = null
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                messenger = Messenger(service)
            }

        }

        bindService(Intent(this, ConvertService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)


        submitButton.setOnClickListener {
            val msg = Message.obtain(null, ConvertHandler.TO_UPPER_CASE)
            msg.replyTo = Messenger(ResponseHandler())
            val bundle = Bundle()
            bundle.putString("data", inputText.text.toString())
            msg.data = bundle

            try {
                messenger?.send(msg)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }

        }
    }

    inner class ResponseHandler: Handler() {
        override fun handleMessage(msg: Message) {
            //super.handleMessage(msg)

            when(msg.what) {
                TO_UPPER_CASE_RESPONSE -> {
                    outputText.text = msg.data.getString("responseData") ?: ""
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }
}
