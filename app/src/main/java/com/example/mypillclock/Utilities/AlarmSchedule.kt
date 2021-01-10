package com.example.mypillclock.Utilities

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.mypillclock.R
import java.util.*
import java.util.Calendar.*

class AlarmSchedule {

    fun createPendingIntent(context:Context): PendingIntent?{
        val intent = Intent(context.applicationContext, AlarmReceiver::class.java).apply {
            // 2
            action = context.getString(R.string.action_notify_administer_medication)
            // 3
            type = "$day-${reminderData.name}-${reminderData.medicine}-${reminderData.type.name}"
            // 4
            putExtra(ReminderDialog.KEY_ID, reminderData.id)
        }
// 5
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }



    fun scheduleAlarm(){
        val datetimeToAlarm = Calendar.getInstance(Locale.getDefault())
        datetimeToAlarm.timeInMillis = System.currentTimeMillis()
        datetimeToAlarm.set(HOUR_OF_DAY, reminderData.hour)
        datetimeToAlarm.set(MINUTE, reminderData.minute)
        datetimeToAlarm.set(SECOND, 0)
        datetimeToAlarm.set(MILLISECOND, 0)
        datetimeToAlarm.set(DAY_OF_WEEK, dayOfWeek)
// 2
        val today = Calendar.getInstance(Locale.getDefault())
        if (shouldNotifyToday(dayOfWeek, today, datetimeToAlarm)) {
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
                datetimeToAlarm.timeInMillis, (1000 * 60 * 60 * 24 * 7).toLong(), alarmIntent)
            return
        }
// 3
        datetimeToAlarm.roll(WEEK_OF_YEAR, 1)
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
            datetimeToAlarm.timeInMillis, (1000 * 60 * 60 * 24 * 7).toLong(), alarmIntent)
    }
}