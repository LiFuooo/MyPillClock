package com.example.mypillclock.Alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.DataClass.RemindDateTimeDataClass
import com.example.mypillclock.R
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.Calendar.*

class AlarmSchedule {

    fun createPendingIntent(context: Context): PendingIntent?{
        val intent = Intent(context.applicationContext, AlarmReceiver::class.java).apply {
            action = context.getString(R.string.action1_takingitnow)
//            type = "$day-${reminderData.name}-${reminderData.medicine}-${reminderData.type.name}"
//            putExtra(ReminderDialog.KEY_ID, reminderData.id)
        }
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

//    fun shouldNotifyToday(today: Calender, deteTimeToAlarm: Calender):Boolean{
//        return today.get(HOUR_OF_DAY) <= datetimeToAlarm.get(HOUR_OF_DAY) &&
//                today.get(MINUTE)
//    }

    fun updateAlarmsForReminder(context: Context, reminderData: RemindDateTimeDataClass){
        if(!reminderData.administered){
            AlarmSchedule.removeAlarmsForReminder(context, reminderData)
        }
    }

    fun removeAlarmsForReminder(context: Context, reminderData: RemindDateTimeDataClass){
        val intent = Intent(context.applicationContext, AlarmReceiver::class.java)
        intent.action = context.getString(R.string.action_notify_administer_medication)
        intent.putExtra(ReminderDialog.KEY_ID, reminderData.id)

        if(reminderData.days != null){
            for(i in reminderData.days!!.indices){
                val day = reminderData.days!![i]

                if(day != null){
                    val type = String.format(
                        Locale.getDefault(),
                        "%s-%s-%s-%s",
                        day,
                        reminderData.pillName
                    )

                    intent.type = type
                    val alarmIntent = PendingIntent.getBroadcast(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )

                    val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    alarmMgr.cancel(alarmIntent)
                }
            }
        }
    }


    fun getFirstAlarmTimeInMillis(pillToAlarm: PillInfo): Long {
        val year = pillToAlarm.remindStartDate.slice(0..3).toInt()
        val month = pillToAlarm.remindStartDate.slice(4..5).toInt()
        val dayOfMonth = pillToAlarm.remindStartDate.slice(6..7).toInt()

        val hourOfDay = pillToAlarm.RemindTime.slice(0..2).toInt()
        val minute = pillToAlarm.RemindTime.slice(3..4).toInt()


        val datetimeToAlarm = Calendar.getInstance(Locale.getDefault())
        datetimeToAlarm.timeInMillis = System.currentTimeMillis()
        datetimeToAlarm.set(YEAR, year)
        datetimeToAlarm.set(MONTH, month)
        datetimeToAlarm.set(DAY_OF_MONTH, dayOfMonth)

        datetimeToAlarm.set(HOUR_OF_DAY, hourOfDay)
        datetimeToAlarm.set(MINUTE, minute)
        datetimeToAlarm.set(SECOND, 0)
        datetimeToAlarm.set(MILLISECOND, 0)

        return datetimeToAlarm.timeInMillis
    }






    fun scheduleFirstAlarm(pillToAlarm: PillInfo, alarmIntent: PendingIntent?, alarmMgr: AlarmManager) {

        val firstAlarmTime = getFirstAlarmTimeInMillis(pillToAlarm)

//        https://developer.android.com/training/scheduling/alarms
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            firstAlarmTime,
            (1000 * 60 * 60 * 24 * 7).toLong(),
            alarmIntent)


    }
//
//
//// 2
//        val today = Calendar.getInstance(Locale.getDefault())
//        if (shouldNotifyToday(today, datetimeToAlarm)) {
//            alarmMgr.setRepeating(
//                AlarmManager.RTC_WAKEUP,
//                datetimeToAlarm.timeInMillis, (1000 * 60 * 60 * 24 * 7).toLong(), alarmIntent
//            )
//            return
//        }
//// 3
//        datetimeToAlarm.roll(WEEK_OF_YEAR, 1)
//        alarmMgr.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            datetimeToAlarm.timeInMillis, (1000 * 60 * 60 * 24 * 7).toLong(), alarmIntent
//        )






}