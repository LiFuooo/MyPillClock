package com.example.mypillclock.Alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.mypillclock.DataClass.RemindDateTimeDataClass
import com.example.mypillclock.R
import java.util.*
import java.util.Calendar.*

class AlarmSchedule {

    fun createPendingIntent(context:Context): PendingIntent?{
        val intent = Intent(context.applicationContext, AlarmReceiver::class.java).apply {
            action = context.getString(R.string.action_notify_administer_medication)
            type = "$day-${reminderData.name}-${reminderData.medicine}-${reminderData.type.name}"
            putExtra(ReminderDialog.KEY_ID, reminderData.id)
        }
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun shouldNotifyToday(dayOfWeek: Int, today:Calender, deteTimeToAlarm: Calender):Boolean{
        return dayOfWeek == today.get(DAY_OF_WEEK) &&
                today.get(HOUR_OF_DAY) <= datetimeToAlarm.get(HOUR_OF_DAY) &&
                today.get(MINUTE)
    }

    fun updateAlarmsForReminder(context:Context, reminderData: RemindDateTimeDataClass){
        if(!reminderData.administered){
            AlarmSchedule.removeAlarmsForReminder(context, reminderData)
        }
    }

    fun removeAlarmsForReminder(context:Context, reminderData: RemindDateTimeDataClass){
        val intent = Intent(context.applicationContext, AlarmReceiver::class.java)
        intent.action = context.getString(R.string.action_notify_administer_medication)
        intent.putExtra(ReminderDialog.KEY_ID, reminderData.id)

        if(reminderData.days != null){
            for(i in reminderData.days!!.indices){
                val day = reminderData.days!![i]

                if(day != null){
                    val type = String.format(Locale.getDefault(), "%s-%s-%s-%s", day, reminderData.pillName)

                    intent.type = type
                    val alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                    val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    alarmMgr.cancel(alarmIntent)
                }
            }
        }
    }


    fun getDayOfWeek(days:Array<String>, dayOfWeek: String):Int{
        return when {
            dayOfWeek.equals(days[0], ignoreCase = true) -> SUNDAY
            dayOfWeek.equals(days[1], ignoreCase = true) -> MONDAY
            dayOfWeek.equals(days[2], ignoreCase = true) -> TUESDAY
            dayOfWeek.equals(days[3], ignoreCase = true) -> WEDNESDAY
            dayOfWeek.equals(days[4], ignoreCase = true) -> THURSDAY
            dayOfWeek.equals(days[5], ignoreCase = true) -> FRIDAY
            else -> SATURDAY
        }
    }




    fun scheduleAlarm(reminderData: RemindDateTimeDataClass, dayOfWeek: Int, alarmIntent:PendingIntent?, alarmMgr:AlarmManager){
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