package com.example.mypillclock

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.FragmentActivity
import java.text.SimpleDateFormat
import java.util.*


class EditPillActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pill)

        val spinner: Spinner = findViewById(R.id.spinner)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.pillTypeArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())


    fun showTimePickerDialog(v: View) {
        AddPillTimePickerFragment().show(supportFragmentManager, "timePicker")

    }

}




//class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        // Use the current time as the default values for the picker
//        val c = Calendar.getInstance()
//        val hour = c.get(Calendar.HOUR_OF_DAY)
//        val minute = c.get(Calendar.MINUTE)
//
//        // Create a new instance of TimePickerDialog and return it
//        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
//    }
//
//    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
//        // Do something with the time chosen by the user
//        TextViewTimePicker.text = ("$hourOfDay").toString()
//    }
//}





class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }
    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}




