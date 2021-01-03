package com.example.mypillclock.Fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.R
import kotlinx.android.synthetic.main.activity_edit_pill.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class EditPillTimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val sdf = SimpleDateFormat("HH:mm")
        var date: Date = sdf.parse(tvPillTimePickerEdit.text.toString())
//        if(sdf.parse(tvPillRemindTimeEdit.text.toString()) != null){
//            val date = sdf.parse(tvPillRemindTimeEdit.text.toString())
//                Log.i("date in Clock", date.toString())
//                val c: Calendar = Calendar.getInstance()
//                c.setTime(date)}
//                var date: Date = sdf.parse(tvPillRemindTimeEdit.text.toString())
//        try {
//            date = sdf.parse(tvPillRemindTimeEdit.text.toString())
//            Log.i("date in Clock", date.toString())
//        } catch (e: ParseException) {
//        }
//                val c: Calendar = Calendar.getInstance()
//                c.setTime(date)
//        val picker = TimePicker(applicationContext)
////            picker.setIs24HourView(true)
//            if (Build.VERSION.SDK_INT >= 23) {
//                picker.hour = c.get(Calendar.HOUR_OF_DAY)
//                picker.minute = c.get(Calendar.MINUTE)
//            }
//            else {
//                picker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY))
//                picker.setCurrentMinute(c.get(Calendar.MINUTE))
//            }
        val c = Calendar.getInstance()
        c.time = date
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        Log.i("time clock", tvPillTimePickerEdit.text.toString())
//        val hour = 1
//        val minute = 3
                // Create a new instance of TimePickerDialog and return it
                return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
            }
//        }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // Do something with the time chosen by the user
//        tvPillRemindTimeEdit.text = ("$hourOfDay").toString()
        val tv: TextView = activity?.findViewById(R.id.tvPillTimePickerEdit) as TextView
        tv.text = "${getHourAMPM(hourOfDay)}:$minute ${getAMPM(hourOfDay)}"
    }

    private fun getAMPM(hour:Int):String{
        return if(hour>11)"PM" else "AM"
    }

    private fun getHourAMPM(hour:Int):Int{
        // Return the hour value for AM PM time format
        var modifiedHour = if (hour>11)hour-12 else hour
        if (modifiedHour == 0){modifiedHour = 12}
        return modifiedHour
    }


}

