package com.github.bluestormdna.lifecycleservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.github.bluestormdna.lifecycleservice.Constants.Action

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.btn_start)
        val btnStop = findViewById<Button>(R.id.btn_stop)

        btnStart.setOnClickListener {
            sendCommandToService(Action.START_OR_RESUME_SERVICE)
        }

        btnStop.setOnClickListener {
            sendCommandToService(Action.STOP_SERVICE)
        }

    }

    private fun sendCommandToService(action: String) {
        Intent(this, MainLifecycleService::class.java).also {
            it.action = action
            startService(it)
        }
    }
}