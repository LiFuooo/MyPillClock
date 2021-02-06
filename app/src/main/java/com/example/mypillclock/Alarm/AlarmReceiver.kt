//package com.example.mypillclock.Alarm
//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.provider.Settings.Global.getString
//import android.util.Log
//import com.example.mypillclock.R
//import org.h2.mvstore.DataUtils
//
//class AlarmReceiver: BroadcastReceiver() {
//
//    val TAG = AlarmReceiver::class.java.simpleName
//
//    override fun onReceive(context: Context?, intent: Intent?) {
//        Log.d(TAG, "onReceive( ) called with context = [$context], intent = [$intent]")
//
//        if(context != null && intent != null && intent.action != null){
//            if(intent.action!!.equals(context.getString(R.string.action_notify_administer_medication), ignoreCase = true)){
//                if(intent.extras != null){
//                    val reminderData = DataUtils.getReminderById(intent.extras!!.getInt(ReminderDialog.KEY_ID))
//                    if(reminderData != null){
//                        NotificationHelper.createNotificationForPill(context, reminderData)
//                    }
//                }
//            }
//        }
//
//
//    }
//
//
//
//}