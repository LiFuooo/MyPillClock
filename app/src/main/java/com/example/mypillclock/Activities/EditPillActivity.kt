package com.example.mypillclock.Activities


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.DatabaseHelper
import com.example.mypillclock.Fragments.AddPillTimePickerFragment
import com.example.mypillclock.R
import kotlinx.android.synthetic.main.activity_add_pill.*
import kotlinx.android.synthetic.main.activity_edit_pill.*
import kotlinx.serialization.json.Json

class EditPillActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pill)

        var PillInfoDetail: PillInfo? = null
        if (intent.hasExtra(MainActivity.EXTRA_PILL_DETAIL)) {
            PillInfoDetail = intent.getSerializableExtra(MainActivity.EXTRA_PILL_DETAIL) as PillInfo
        }

        if (PillInfoDetail != null) {
            val IDofPillBeenEdited = PillInfoDetail.id

            etPillNameEdit.setText(PillInfoDetail.name)
            etDurationEdit.setText(PillInfoDetail.duration.toString())
            etFrequencyEdit.setText(PillInfoDetail.frequency.toString())
            etPillAmountEdit.setText(PillInfoDetail.amount.toString())
//            holder.spinnerAmountType.setText(currentItem.amountType)
            tvPillTimePickerEdit.text = PillInfoDetail.RemindTime
            etRxNumberEdit.setText(PillInfoDetail.rxNumber)
            etDoctorNoteEdit.setText(PillInfoDetail.name)

        }


//        UPDATE AND SAVE  Button Part ------------------------------------------------------------------------
        updatePillBtn.setOnClickListener {
            val name = etPillName.text.toString().trim()
            val duration = etDuration.text.toString().trim().toIntOrNull()
            val frequency = etFrequency.text.toString().trim().toIntOrNull()
            val amount = etPillAmount.text.toString().trim().toIntOrNull()
            val amountType = spinnerAmountType.selectedItem.toString().trim()
            val remindTime = tvPillTimePicker.text.toString().trim()
            val rxNumber = tvRxNumber.text.toString().trim()
            val doctorNote = etDoctorNote.text.toString().trim()

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

                val pillAfterEdit = PillInfo(
                    if (PillInfoDetail == null) 0 else PillInfoDetail!!.id,
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

//                Json text to Data class instance
                val pillInfoText =
                    Json.decodeFromString(PillInfo.serializer(), afterEditPillInfoJson)

//              get configuration
                val json = Json { allowStructuredMapKeys = true }


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
            AddPillTimePickerFragment().show(supportFragmentManager, "timePicker")
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



