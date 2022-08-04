package com.github.bluestormdna.lifecycleservice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.github.bluestormdna.lifecycleservice.Constants.Action
import com.github.bluestormdna.lifecycleservice.Constants.NOTIFICATION_CHANNEL_MAIN_ID
import com.github.bluestormdna.lifecycleservice.Constants.NOTIFICATION_CHANNEL_MAIN_NAME
import com.github.bluestormdna.lifecycleservice.Constants.NOTIFICATION_MAIN_ID
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class MainLifecycleService: LifecycleService() {

    private var somethingJob: Job? = null

    override fun onCreate() {
        super.onCreate()

        doSomething()
    }

    private fun doSomething() {
        somethingJob?.cancel()
        somethingJob = lifecycleScope.launch {
            while (true) {
                Log.d("RandomTag", "I'm doing something")
                delay(1.seconds)
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (intent.action) {
                Action.START_OR_RESUME_SERVICE -> {
                    startForeground()
                }
                Action.STOP_SERVICE -> {
                    stopForeground()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startForeground() {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_MAIN_ID)
            .setContentTitle(applicationContext.getString(R.string.app_name))
            .setSmallIcon(R.drawable.ic_baseline_filter_tilt_shift_24)
            .build()

        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_MAIN_ID,
            NOTIFICATION_CHANNEL_MAIN_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        startForeground(NOTIFICATION_MAIN_ID, notification)
    }

    private fun stopForeground() {
        stopForeground(true)
        stopSelf()
    }
}