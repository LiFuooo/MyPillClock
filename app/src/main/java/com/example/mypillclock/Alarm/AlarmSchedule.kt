package com.example.mypillclock.Alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Entity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.PillClockInDBHelper
import com.example.mypillclock.Database.PillInfoDBHelper
import com.example.mypillclock.Database.PillScheduleTimeDBHelper
import com.example.mypillclock.R
import java.util.*

class AlarmSchedule(pillInfoEntity: PillInfoDBHelper.DBExposedPillEntity) {
    private val pillToAlarm = pillInfoEntity

    fun createPendingIntent(context: Context): PendingIntent?{
        val intent = Intent(context.applicationContext, PillBroadcastReceiver()::class.java).apply {
            action = context.getString(R.string.action1_takingitnow)
            putExtra("pillToAlarmID",pillToAlarm.id.toString())
        }
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }



    fun scheduleAlarms(context: Context) {

        val alarmIntent = createPendingIntent(context)
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val firstAlarmTime = getFirstAlarmTimeInMillis(pillToAlarm)

//        https://developer.android.com/training/scheduling/alarms
//        repeat the alarm every 60 minutes
//        val repeatInMilliseconds = (24 / pillToAlarm.frequency * 60 * 60* 1000).toLong()
        val repeatInMilliseconds = (1000 * 5 ).toLong()
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            repeatInMilliseconds,
            alarmIntent)

//        PillScheduleTimeDBHelper().addOneRecordWithScheduleTime(System.currentTimeMillis(),pillToAlarm)
        Toast.makeText(context, "addPillClockSchedule to DB", Toast.LENGTH_SHORT).show()
        Log.i("Add Schedule",pillToAlarm.name)


    }


//    fun getFirstAlarmTimeInMillis(pillToAlarm: PillInfo): Long {
//        val year = pillToAlarm.remindStartDate.slice(0..3).toInt()
//        val month = pillToAlarm.remindStartDate.slice(4..5).toInt()
//        val dayOfMonth = pillToAlarm.remindStartDate.slice(6..7).toInt()
//
//        val hourOfDay = pillToAlarm.RemindTime.slice(0..2).toInt()
//        val minute = pillToAlarm.RemindTime.slice(3..4).toInt()
//
//
//        val datetimeToAlarm = Calendar.getInstance(Locale.getDefault())
//        datetimeToAlarm.timeInMillis = System.currentTimeMillis()
////        datetimeToAlarm.set(YEAR, year)
////        datetimeToAlarm.set(MONTH, month)
////        datetimeToAlarm.set(DAY_OF_MONTH, dayOfMonth)
////
////        datetimeToAlarm.set(HOUR_OF_DAY, hourOfDay)
////        datetimeToAlarm.set(MINUTE, minute)
////        datetimeToAlarm.set(SECOND, 0)
////        datetimeToAlarm.set(MILLISECOND, 0)
//
//        return datetimeToAlarm.timeInMillis
//    }

//    fun shouldNotifyToday(today: Calender, deteTimeToAlarm: Calender):Boolean{
//        return today.get(HOUR_OF_DAY) <= datetimeToAlarm.get(HOUR_OF_DAY) &&
//                today.get(MINUTE)
//    }
//
//    fun updateAlarmsForReminder(context: Context, reminderData: RemindDateTimeDataClass){
//        if(!reminderData.administered){
//            AlarmSchedule.removeAlarmsForReminder(context, reminderData)
//        }
//    }
//
//    fun removeAlarmsForReminder(context: Context, reminderData: RemindDateTimeDataClass){
//        val intent = Intent(context.applicationContext, AlarmReceiver::class.java)
//        intent.action = context.getString(R.string.action_notify_administer_medication)
//        intent.putExtra(ReminderDialog.KEY_ID, reminderData.id)
//
//        if(reminderData.days != null){
//            for(i in reminderData.days!!.indices){
//                val day = reminderData.days!![i]
//
//                if(day != null){
//                    val type = String.format(
//                        Locale.getDefault(),
//                        "%s-%s-%s-%s",
//                        day,
//                        reminderData.pillName
//                    )
//
//                    intent.type = type
//                    val alarmIntent = PendingIntent.getBroadcast(
//                        context,
//                        0,
//                        intent,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                    )
//
//                    val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//                    alarmMgr.cancel(alarmIntent)
//                }
//            }
//        }
//    }
//


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