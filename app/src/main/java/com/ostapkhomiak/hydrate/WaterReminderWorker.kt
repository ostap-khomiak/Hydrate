package com.ostapkhomiak.hydrate

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class WaterReminderWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        createNotificationChannel()

        val builder = NotificationCompat.Builder(applicationContext, "HydrateReminderChannel")
            .setSmallIcon(R.drawable.icon) // Use your icon
            .setContentTitle("Hydrate")
            .setContentText("Time to drink some water! 💧")

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(1001, builder.build())
        }

        return Result.success()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Hydrate Reminder"
            val descriptionText = "Reminds you to drink water every few hours."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("HydrateReminderChannel", name, importance)
            channel.description = descriptionText
            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
