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
import com.example.mypillclock.databinding.ActivityAddPillBinding
import com.example.mypillclock.databinding.ActivityEditPillBinding
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.*


class EditPillActivity : AppCompatActivity() {
    private lateinit var binding:ActivityEditPillBinding
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPillBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            binding.spinnerAmountTypeEdit.setAdapter(adapter)
            if (compareValue != null) {
                val spinnerPosition = adapter.getPosition(compareValue)
                binding.spinnerAmountTypeEdit.setSelection(spinnerPosition)}





//                ToDo(Set pillInfo )
            val idOfPillBeenEdited = ToBeEditedPillInfoDetail.id

            binding.etEditPillPillName.setText(ToBeEditedPillInfoDetail.name)
            binding.etEditPillQuantity.setText(ToBeEditedPillInfoDetail.quantity.toString())
            binding.etFrequencyEdit.setText(ToBeEditedPillInfoDetail.frequency.toString())
            binding.etPillAmountEdit.setText(ToBeEditedPillInfoDetail.amount.toString())
            binding.tvPillDatePickerEdit.text = ToBeEditedPillInfoDetail.remindStartDate
            binding.tvPillTimePickerEdit.text = ToBeEditedPillInfoDetail.RemindTime
            Log.i("Line tvPillTimePickerE", binding.tvPillTimePickerEdit.text.toString())
            binding.etRxNumberEdit.setText(ToBeEditedPillInfoDetail.rxNumber)
            binding.etDoctorNoteEdit.setText(ToBeEditedPillInfoDetail.doctorNote)

        }


//        UPDATE AND SAVE  Button Part ------------------------------------------------------------------------
        binding.updatePillBtn.setOnClickListener {
            val name = binding.etEditPillPillName.text.toString().trim()
            val quantity = binding.etEditPillQuantity.text.toString().trim().toIntOrNull()
            val isRepetitive = isPillReminderRepetitive()
            val frequency = binding.etFrequencyEdit.text.toString().trim().toIntOrNull()
            val amount = binding.etPillAmountEdit.text.toString().trim().toIntOrNull()
            val amountType = binding.spinnerAmountTypeEdit.selectedItem.toString().trim()
            val remindStartDate = binding.tvPillDatePickerEdit.text.toString().trim()
            val remindTime = binding.tvPillTimePickerEdit.text.toString().trim()
            val rxNumber = binding.tvRxNumberEdit.text.toString().trim()
            val doctorNote = binding.etDoctorNoteEdit.text.toString().trim()

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

        var dateOnClock: Date = sdf.parse(binding.tvPillDatePickerEdit.text.toString())
        EditPillDatePickerFragment(dateOnClock).show(supportFragmentManager, "datePickerEdit")
    }


    fun showTimePickerDialogEdit(v: View) {
        val sdf = SimpleDateFormat("hh:mm a")

        var timeOnClock: Date = sdf.parse(binding.tvPillTimePickerEdit.text.toString())
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

//     rewrite this function
//    get is this pill repetitive radio button selected ID
    private fun isPillReminderRepetitive(): Boolean {
        lateinit var addPillActivityBinding:ActivityAddPillBinding
        var radioaGroupSelectedId: Int = addPillActivityBinding.rgAddPill.checkedRadioButtonId
        val radio:RadioButton = findViewById(radioaGroupSelectedId)
        val radioText = radio.text
        val mystring = resources.getString(R.string.str_add_pill_rb_pill_is_repetitve)
        var isRepetitive = radioText.toString() == mystring
        return isRepetitive
    }


}



