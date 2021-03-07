package com.example.mypillclock.Alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.mypillclock.Database.PillInfoDBHelper

private const val TAG = "MyBroadcastReceiver"

class PillBroadcastReceiver() : BroadcastReceiver() {

    val getSavedPillList = PillInfoDBHelper().getPillListFromDB()
    val pillToNotify = getSavedPillList[0]

    override fun onReceive(context: Context, intent: Intent) {

//        val service = Intent(context, NotificationHelper::class.java)
//        service.putExtra("reason", intent.getStringExtra("reason"))
//        service.putExtra("timestamp", intent.getLongExtra("timestamp", 0))
//
//        context.startService(service)
        NotificationHelper().createPillNotification(context,pillToNotify)
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