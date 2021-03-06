package com.example.mypillclock.Fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.DatePicker
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.mypillclock.R
import java.util.*

// https://stackoverflow.com/questions/4467816/datepicker-shows-wrong-value-of-month#4467894

class AddPillDatePickerFragment(
    val textViewId:Int)
    :DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default values for the picker
        val c = Calendar.getInstance()
        val dayOfMonth = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)+1
        val year = c.get(Calendar.YEAR)



        // Create a new instance of TimePickerDialog and return it
        return DatePickerDialog(activity!!, this, year, month, dayOfMonth)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val tv: TextView = activity?.findViewById(textViewId) as TextView

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