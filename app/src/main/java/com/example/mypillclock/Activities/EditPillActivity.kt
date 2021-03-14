package com.example.mypillclock.Activities


import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.PillInfoDBHelper
import com.example.mypillclock.Fragments.EditPillDatePickerFragment
import com.example.mypillclock.Fragments.EditPillTimePickerFragment
import com.example.mypillclock.R
import kotlinx.android.synthetic.main.activity_add_pill.*
import kotlinx.android.synthetic.main.activity_edit_pill.*
import kotlinx.android.synthetic.main.activity_edit_pill.rg_edit_pill
import kotlinx.serialization.json.Json
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


//        Radio Button Group






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





//                ToDo(Set pillInfo )
            val idOfPillBeenEdited = ToBeEditedPillInfoDetail.id

            et_edit_pill_pillName.setText(ToBeEditedPillInfoDetail.name)
            et_edit_pill_quantity.setText(ToBeEditedPillInfoDetail.quantity.toString())
            etFrequencyEdit.setText(ToBeEditedPillInfoDetail.frequency.toString())
            etPillAmountEdit.setText(ToBeEditedPillInfoDetail.amount.toString())
            tvPillDatePickerEdit.text = ToBeEditedPillInfoDetail.remindStartDate
            tvPillTimePickerEdit.text = ToBeEditedPillInfoDetail.RemindTime
            Log.i("Line tvPillTimePickerE", tvPillTimePickerEdit.text.toString())
            etRxNumberEdit.setText(ToBeEditedPillInfoDetail.rxNumber)
            etDoctorNoteEdit.setText(ToBeEditedPillInfoDetail.doctorNote)

        }


//        UPDATE AND SAVE  Button Part ------------------------------------------------------------------------
        updatePillBtn.setOnClickListener {
            val name = et_edit_pill_pillName.text.toString().trim()
            val quantity = et_edit_pill_quantity.text.toString().trim().toIntOrNull()
            val isRepetitive = isPillReminderRepetitive()
            val frequency = etFrequencyEdit.text.toString().trim().toIntOrNull()
            val amount = etPillAmountEdit.text.toString().trim().toIntOrNull()
            val amountType = spinnerAmountTypeEdit.selectedItem.toString().trim()
            val remindStartDate = tvPillDatePickerEdit.text.toString().trim()
            val remindTime = tvPillTimePickerEdit.text.toString().trim()
            val rxNumber = tvRxNumberEdit.text.toString().trim()
            val doctorNote = etDoctorNoteEdit.text.toString().trim()

            var isFormFilled = false


            if (name.isEmpty()) {
                Toast.makeText(this, "Pill Name is Empty!", Toast.LENGTH_SHORT).show()
            }

            if (quantity == null) {
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

            if (remindStartDate.isEmpty()) {
                Toast.makeText(this, "Remind Start Date is Empty!", Toast.LENGTH_SHORT).show()
            }

            if (remindTime.isEmpty()) {
                Toast.makeText(this, "Remind Time is Empty!", Toast.LENGTH_SHORT).show()
            }

            if (rxNumber.isEmpty()) {
                Toast.makeText(this, "rxNumber is Empty!", Toast.LENGTH_SHORT).show()
            }

            if (name.isNotEmpty()
                && quantity != null
                && frequency != null
                && amount != null
                && amountType.isNotEmpty()
                && remindStartDate.isNotEmpty()
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
                    quantity,
                    isRepetitive,
                    frequency,
                    amount,
                    amountType,
                    remindStartDate,
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

    fun showDatePickerDialogEdit(v: View) {
        val sdf = SimpleDateFormat("yyyy-mm-dd")

        var dateOnClock: Date = sdf.parse(tvPillDatePickerEdit.text.toString())
        EditPillDatePickerFragment(dateOnClock).show(supportFragmentManager, "datePickerEdit")
    }


    fun showTimePickerDialogEdit(v: View) {
        val sdf = SimpleDateFormat("hh:mm a")

        var timeOnClock: Date = sdf.parse(tvPillTimePickerEdit.text.toString())
            EditPillTimePickerFragment(timeOnClock).show(supportFragmentManager, "timePickerEdit")
        }


    fun UpdatePillRecord(isFormFilled: Boolean, newPill: PillInfo) {
            val databaseHelper = PillInfoDBHelper()
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

    private fun isPillReminderRepetitive(): Boolean {
        var radioaGroupSelectedId: Int = rg_add_pill.checkedRadioButtonId
        val radio:RadioButton = findViewById(radioaGroupSelectedId)
        val radioText = radio.text
        val mystring = resources.getString(R.string.str_add_pill_rb_pill_is_repetitve)
        var isRepetitive = radioText.toString() == mystring
        return isRepetitive
    }


}



