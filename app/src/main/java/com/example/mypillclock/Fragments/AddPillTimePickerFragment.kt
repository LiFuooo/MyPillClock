package com.example.mypillclock.Fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.mypillclock.R
import java.util.*

class AddPillTimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // Do something with the time chosen by the user
//        TextViewTimePicker.text = ("$hourOfDay").toString()
        val tv: TextView = activity?.findViewById(R.id.tvPillTimePicker) as TextView
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

