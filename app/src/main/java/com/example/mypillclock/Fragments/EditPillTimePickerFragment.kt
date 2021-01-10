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

class EditPillTimePickerFragment(val timeOnClock : Date) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        c.time = timeOnClock
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        var am_pm = ""
        if (c.get(Calendar.AM_PM) == Calendar.AM)
            am_pm = "AM";
        else if (c.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";



                // Create a new instance of TimePickerDialog and return it
                return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
            }
//        }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        val tv: TextView = activity?.findViewById(R.id.tvPillTimePickerEdit) as TextView
        val minuteFormatted =
            if(minute < 10){
                "0${minute}"
            } else{
                minute
            }
        tv.text = "${getHourAMPM(hourOfDay)}:$minuteFormatted ${getAMPM(hourOfDay)}"
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

