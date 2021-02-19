//package com.example.mypillclock.Alarm
//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.provider.Settings.Global.getString
//import android.util.Log
//import com.example.mypillclock.DataClass.PillInfo
//import com.example.mypillclock.Database.pillInfoDBHelper
//import com.example.mypillclock.R
//import org.h2.mvstore.DataUtils
//
//abstract class AlarmReceiver: BroadcastReceiver() {
//
//    val TAG = AlarmReceiver::class.java.simpleName
//
//    fun onReceive(context: Context?, intent: Intent?, pillInfo:PillInfo) {
//        Log.d(TAG, "onReceive( ) called with context = [$context], intent = [$intent]")
//
//        if(context != null && intent != null && intent.action != null) {
//            if (intent.action!!.equals(
//                    context.getString(R.string.action1_takingitnow),
//                    ignoreCase = true
//                )
//            ) {
//                NotificationHelper().createPillNotification(context, pillInfo)
//            }
//        }}}
//
////                if(intent.extras != null){
////                    val reminderData = DataUtils.getReminderById(intent.extras!!.getInt(ReminderDialog.KEY_ID))
////                    if(reminderData != null){
////                        NotificationHelper.createNotificationForPill(context, reminderData)
////                    }
////                }
////            }
////        }
//
//
//
//
//
//
