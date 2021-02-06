package com.example.mypillclock.Alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.System.getString
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mypillclock.Activities.ClockInActivity
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.pillInfoDBHelper
import com.example.mypillclock.R
import java.text.SimpleDateFormat
import java.util.*

class NotificationHelper() {
//    https://www.raywenderlich.com/1214490-android-notifications-tutorial-getting-started

    //  val channelId = "${context.packageName}-$name"
    val CHANNEL_ID = "channel_id_01"
    val notificationId = 1001



    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = R.string.pillNotificationChannelName.toString()
            val descriptionText = R.string.notification_channel_description.toString()
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager:NotificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }








    fun createPillNotification(context: Context,  pillInfo: PillInfo) {
//        get pill Info from DB
        val getSavedPillList = pillInfoDBHelper().getPillListFromDB()
        val sdfTime = SimpleDateFormat("hh:mm a")

            val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
                setSmallIcon(R.drawable.pill_notification_icon)
                setContentTitle("Pill Clock")
                setContentText("It's time to take ${pillInfo.amount} ${pillInfo.amountType.toString() }  ${pillInfo.name}")
//                setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
                priority = NotificationCompat.PRIORITY_DEFAULT
                setAutoCancel(false)


                val intent = Intent(context, ClockInActivity::class.java).apply{
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

                val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
                setContentIntent(pendingIntent)
        }

        val notificationManager = NotificationManagerCompat.from(context)
//        notificationManager.notify(1001, notificationBuilder.build())
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, notificationBuilder.build())
        }



        val snoozeIntent = Intent(this, PillBroadcastReceiver::class.java).apply {
            action = ACTION_SNOOZE
            putExtra(EXTRA_NOTIFICATION_ID, 0)
        }
        val snoozePendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this, 0, snoozeIntent, 0)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_snooze, getString(R.string.snooze),
                snoozePendingIntent)

    }








}