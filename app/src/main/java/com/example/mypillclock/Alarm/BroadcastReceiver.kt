package com.example.mypillclock.Alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.PillInfoDBHelper
import com.example.mypillclock.Database.PillScheduleTimeDBHelper
import com.example.mypillclock.Utilities.DataClassEntityConverter


class PillBroadcastReceiver : BroadcastReceiver() {

    private val TAG = "PillBroadcastReceiver"
    override fun onReceive(context: Context, intent: Intent) {

        val pillToAlarmID = intent.extras?.getString("pillToAlarmID")?.toInt()
        Log.i(TAG, pillToAlarmID.toString())
        if(pillToAlarmID != null){
            val pillToNotifyEntity = PillInfoDBHelper().queryOnePillById(pillToAlarmID)
            Log.i("$TAG pillToNotifyEntity", pillToNotifyEntity.toString())
            if(pillToNotifyEntity != null){
                NotificationHelper().createPillNotification(context,pillToNotifyEntity)
                PillScheduleTimeDBHelper().addOneRecordWithScheduleTime(System.currentTimeMillis(),pillToNotifyEntity)
            }
        }

    }

}

//class PillBroadcastReceiver : BroadcastReceiver() {
//
//    override fun onReceive(context: Context, intent: Intent) {
////        StringBuilder().apply {
////            append("Action: ${intent.action}\n")
////            append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
////            toString().also { log ->
////                Log.d(TAG, log)
////                Toast.makeText(context, log, Toast.LENGTH_LONG).show()
////            }
//
//
//        }
//    }

//    private class Task(
//        private val pendingResult: PendingResult,
//        private val intent: Intent
//    ) : AsyncTask<String, Int, String>() {
//
//        override fun doInBackground(vararg params: String?): String {
//            val sb = StringBuilder()
//            sb.append("Action: ${intent.action}\n")
//            sb.append("URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
//            return toString().also { log ->
//                Log.d(TAG, log)
//            }
//        }
//
//        override fun onPostExecute(result: String?) {
//            super.onPostExecute(result)
//            // Must call finish() so the BroadcastReceiver can be recycled.
//            pendingResult.finish()
//        }
//    }




//}