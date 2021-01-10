package com.example.mypillclock.Fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.mypillclock.R
import java.util.*

class EditPillDatePickerFragment(private val dateOnClock: Date) : DialogFragment(),DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        c.time = dateOnClock
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH) +1
        val year = c.get(Calendar.YEAR)

        // Create a new instance of TimePickerDialog and return it
        return DatePickerDialog(activity!!, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val tv: TextView = activity?.findViewById(R.id.tvPillDatePickerEdit) as TextView
        val monthString = if(month < 10){
            "0$month";
        } else{
            month.toString()
        }
        val dayOfMonthString =
            if(dayOfMonth < 10){
                "0$dayOfMonth";
            } else{
                dayOfMonth.toString()
            }

        Log.i("monthString", monthString)
        Log.i("dayOfMonthString", dayOfMonthString)
        tv.text = "$year-$monthString-$dayOfMonthString"
    }
}