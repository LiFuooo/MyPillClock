package com.example.mypillclock.Alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mypillclock.Activities.MainActivity
import com.example.mypillclock.Database.pillInfoDBHelper
import com.example.mypillclock.R
import java.text.SimpleDateFormat
import java.util.*

class NotificationHelper() {
//    https://www.raywenderlich.com/1214490-android-notifications-tutorial-getting-started



    fun createNotificationChannel(context: Context, importance: Int, showBadge: Boolean, name: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelId = "${context.packageName}-$name"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)


            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }




    fun createPillNotification(context: Context, channelId: String) {
//        get pill Info from DB
        val getSavedPillList = pillInfoDBHelper().getPillListFromDB()
        val sdfTime = SimpleDateFormat("hh:mm a")

//        get pill date and time for each pill
        getSavedPillList.forEach {
            val pillName = it.name
            val remindBeginDate = it.remindStartDate
            val remindEndDate = it.remindStartDate + it.duration

            val c = Calendar.getInstance()
            val timeOnClock = sdfTime.parse(it.RemindTime)
            c.time = timeOnClock
            val hourOnClock = c.get(Calendar.HOUR_OF_DAY)
            val minuteOnClock = c.get(Calendar.MINUTE)
            var am_pm = ""
            if (c.get(Calendar.AM_PM) == Calendar.AM)
                am_pm = "AM";
            else if (c.get(Calendar.AM_PM) == Calendar.PM)
                am_pm = "PM";

            val remindHour = hourOnClock + 24 / it.frequency

//            calculate remind date and time

        }



            val notificationBuilder = NotificationCompat.Builder(context, channelId).apply {
                setSmallIcon(R.drawable.pillNotificationIcon)
                setContentTitle("My Pill Clock")
                setContentText("It's time to take Pill_1")
//                setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
                priority = NotificationCompat.PRIORITY_DEFAULT
                setAutoCancel(false)
                val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
                setContentIntent(pendingIntent)
        }

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1001, notificationBuilder.build())

    }






}