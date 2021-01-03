package com.example.mypillclock.Activities


import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.DatabaseHelper
import com.example.mypillclock.Fragments.EditPillTimePickerFragment
import com.example.mypillclock.R
import kotlinx.android.synthetic.main.activity_add_pill.*
import kotlinx.android.synthetic.main.activity_edit_pill.*
import kotlinx.serialization.json.Json
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class EditPillActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pill)

        var ToBeEditedPillInfoDetail: PillInfo? = null
        if (intent.hasExtra(MainActivity.EXTRA_PILL_DETAIL)) {
            ToBeEditedPillInfoDetail = intent.getSerializableExtra(MainActivity.EXTRA_PILL_DETAIL) as PillInfo
            Log.i("ToBeEditedPillInfo", ToBeEditedPillInfoDetail.toString())
        }



        if (ToBeEditedPillInfoDetail != null) {

//           TODO(set default spinner item)
            val compareValue = ToBeEditedPillInfoDetail.amountType
            val adapter = ArrayAdapter.createFromResource(
                this,
                R.array.pillTypeArray,
                android.R.layout.simple_spinner_item
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerAmountTypeEdit.setAdapter(adapter)
            if (compareValue != null) {
                val spinnerPosition = adapter.getPosition(compareValue)
                spinnerAmountTypeEdit.setSelection(spinnerPosition)}


//                TODO(Set Time Picker default value)

//                val sdf = SimpleDateFormat("HH:mm")
//                var date: Date? = null
//                try {
//                    date = sdf.parse(ToBeEditedPillInfoDetail.RemindTime)
//                    Log.i("date in Clock", date.toString())
//                } catch (e: ParseException) {
//                }
//                val c: Calendar = Calendar.getInstance()
//                c.setTime(date)
//
//                val picker = TimePicker(applicationContext)
////            picker.setIs24HourView(true)
//            if (Build.VERSION.SDK_INT >= 23) {
//                picker.hour = c.get(Calendar.HOUR_OF_DAY)
//                picker.minute = c.get(Calendar.MINUTE)
//            }
//            else {
//                picker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY))
//                picker.setCurrentMinute(c.get(Calendar.MINUTE))
//            }
//
//            Log.i("picker.hour ", picker.hour.toString())
//            Log.i("picker.minute ", picker.minute.toString())




//                ToDo(Set pillInfo )
            val idOfPillBeenEdited = ToBeEditedPillInfoDetail.id

            etPillNameEdit.setText(ToBeEditedPillInfoDetail.name)
            etDurationEdit.setText(ToBeEditedPillInfoDetail.duration.toString())
            etFrequencyEdit.setText(ToBeEditedPillInfoDetail.frequency.toString())
            etPillAmountEdit.setText(ToBeEditedPillInfoDetail.amount.toString())
            tvPillTimePickerEdit.text = ToBeEditedPillInfoDetail.RemindTime
            Log.i("Line tvPillTimePickerE", tvPillTimePickerEdit.text.toString())
            etRxNumberEdit.setText(ToBeEditedPillInfoDetail.rxNumber)
            etDoctorNoteEdit.setText(ToBeEditedPillInfoDetail.doctorNote)

        }


//        UPDATE AND SAVE  Button Part ------------------------------------------------------------------------
        updatePillBtn.setOnClickListener {
            val name = etPillNameEdit.text.toString().trim()
            val duration = etDurationEdit.text.toString().trim().toIntOrNull()
            val frequency = etFrequencyEdit.text.toString().trim().toIntOrNull()
            val amount = etPillAmountEdit.text.toString().trim().toIntOrNull()
            val amountType = spinnerAmountTypeEdit.selectedItem.toString().trim()
            val remindTime = tvPillTimePickerEdit.text.toString().trim()
            val rxNumber = tvRxNumberEdit.text.toString().trim()
            val doctorNote = etDoctorNoteEdit.text.toString().trim()

            var isFormFilled = false


            if (name.isEmpty()) {
                Toast.makeText(this, "Pill Name is Empty!", Toast.LENGTH_SHORT).show()
            }

            if (duration == null) {
                Toast.makeText(this, "Duration is Empty!", Toast.LENGTH_SHORT).show()
            }

            if (frequency == null) {
                Toast.makeText(this, "Frequency is Empty!", Toast.LENGTH_SHORT).show()
            }

            if (amount == null) {
                Toast.makeText(this, "Amount is Empty!", Toast.LENGTH_SHORT).show()
            }

            if (amountType.isEmpty()) {
                Toast.makeText(this, "Please select an Amount Type!", Toast.LENGTH_SHORT).show()
            }

            if (remindTime.isEmpty()) {
                Toast.makeText(this, "Remind Time is Empty!", Toast.LENGTH_SHORT).show()
            }

            if (rxNumber.isEmpty()) {
                Toast.makeText(this, "rxNumber is Empty!", Toast.LENGTH_SHORT).show()
            }

            if (name.isNotEmpty()
                && duration != null
                && frequency != null
                && amount != null
                && amountType.isNotEmpty()
                && remindTime.isNotEmpty()
                && rxNumber.isNotEmpty()
            ) {
                isFormFilled = true

//                after data saved in database correctly, make the toast
                Toast.makeText(this, "Pill Info is Updated and Saved!", Toast.LENGTH_SHORT).show()

//                find the largest id number in database now


                val pillAfterEdit = PillInfo(
                    if (ToBeEditedPillInfoDetail == null) 0 else ToBeEditedPillInfoDetail!!.id,
                    name,
                    duration,
                    frequency,
                    amount,
                    amountType,
                    remindTime,
                    rxNumber,
                    doctorNote
                )


//                Data class instance to Json Text
                val afterEditPillInfoJson =
                    Json.encodeToString(PillInfo.serializer(), pillAfterEdit)
                Toast.makeText(this, afterEditPillInfoJson, Toast.LENGTH_SHORT).show()

////                Json text to Data class instance
//                val pillInfoText =
//                    Json.decodeFromString(PillInfo.serializer(), afterEditPillInfoJson)
//
////              get configuration
//                val json = Json { allowStructuredMapKeys = true }


//                upDate Pill info to Database
                UpdatePillRecord(isFormFilled, pillAfterEdit)


                Intent(this, MainActivity::class.java).also {
//                    it.putExtra("this pill's info", pill)
//                    val pillInfo = intent.getSerializableExtra("this pill's info") as PillInfo
//                    tvMain.text = pillInfo.toString()
                    startActivity(it)
                }
            }

        }
    }


    fun showTimePickerDialogEdit(v: View) {
            EditPillTimePickerFragment().show(supportFragmentManager, "timePickerEdit")
        }


    fun UpdatePillRecord(isFormFilled: Boolean, newPill: PillInfo) {
            val databaseHelper = DatabaseHelper()
            if (isFormFilled) {
                Log.e("AddPillActivity", "isFormFilled = $isFormFilled")
//            val pillInfoJson = Json.encodeToString(PillInfo.serializer(), pill)
                val status = databaseHelper.updatePill(newPill)
                try {
                    databaseHelper.updatePill(newPill)
                    setResult(Activity.RESULT_OK)
                    Toast.makeText(this, "Pill Saved to DataBase", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {

                    Toast.makeText(this, "Pill Save to DataBase FAILED!", Toast.LENGTH_SHORT).show()
                }
                Log.e("AddPillActivity", "status = $status")

            }
        }

    }



