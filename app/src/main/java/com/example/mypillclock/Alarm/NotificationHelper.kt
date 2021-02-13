package com.example.mypillclock.Alarm

import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mypillclock.Activities.ClockInActivity
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.pillInfoDBHelper
import com.example.mypillclock.R
import java.text.SimpleDateFormat

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
            val notificationManager: NotificationManager =
                context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }



    fun noActionPillNotificationBuilder(context: Context,  pillInfo: PillInfo): NotificationCompat.Builder {
//        get pill Info from DB
        val getSavedPillList = pillInfoDBHelper().getPillListFromDB()
        val sdfTime = SimpleDateFormat("hh:mm a")

        return NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.pill_notification_icon)
            setContentTitle("Pill Clock")
            setContentText("It's time to take ${pillInfo.amount} ${pillInfo.amountType.toString()}  ${pillInfo.name}")
//                setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
            priority = NotificationCompat.PRIORITY_DEFAULT
            setAutoCancel(false)


            val activityActionIntent = Intent(context, ClockInActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            val pendingIntent = PendingIntent.getActivity(context, 0, activityActionIntent, 0)
            setContentIntent(pendingIntent)
        }
    }


    fun withActionPillNotificationBuilder(context: Context,  pillInfo: PillInfo): NotificationCompat.Builder {
//        get pill Info from DB
        val getSavedPillList = pillInfoDBHelper().getPillListFromDB()
        val sdfTime = SimpleDateFormat("hh:mm a")

        val takeItNowPendingIntent = createTakeItNowPendingIntent(context, pillInfo)
        val remindMeLaterPendingIntent = createremindMeLaterPendingIntent(context,pillInfo)

        return NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.pill_notification_icon)
            setContentTitle("Pill Clock")
            setContentText("It's time to take ${pillInfo.amount} ${pillInfo.amountType.toString()}  ${pillInfo.name}")
//                setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(takeItNowPendingIntent)
            addAction(
                R.drawable.ic_baseline_emoji_people_24,
                context.getString(R.string.action1_takingitnow),
                takeItNowPendingIntent
            )
            addAction(
                R.drawable.ic_baseline_snowboarding_24,
                context.getString(R.string.action2_remindMeLater),
                remindMeLaterPendingIntent

            )
        }
    }




    private fun createTakeItNowPendingIntent(context: Context, pillInfo: PillInfo): PendingIntent? {

        val takeItNowIntent = Intent(context, ClockInActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            action = context.getString(R.string.action_medicine_administered)
//            putExtra(AppGlobalReceiver.NOTIFICATION_ID, reminderData.id)
//            putExtra(ReminderDialog.KEY_ID, reminderData.id)
//            putExtra(ReminderDialog.KEY_ADMINISTERED, true)
        }
        return PendingIntent.getActivity(context, 0, takeItNowIntent, 0)

    }


    private fun createremindMeLaterPendingIntent(context: Context, pillInfo: PillInfo): PendingIntent? {

        val remindMeLaterIntent = Intent(context, ClockInActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            action = context.getString(R.string.action_medicine_administered)
//            putExtra(AppGlobalReceiver.NOTIFICATION_ID, reminderData.id)
//            putExtra(ReminderDialog.KEY_ID, reminderData.id)
//            putExtra(ReminderDialog.KEY_ADMINISTERED, true)
        }
        return PendingIntent.getActivity(context, 0, remindMeLaterIntent, 0)

    }







}