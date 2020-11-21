package com.example.mypillclock.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mypillclock.Database.PillDatabaseHandler
import com.example.mypillclock.Fragments.AddPillTimePickerFragment
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.R
import kotlinx.android.synthetic.main.activity_add_pill.*
import kotlinx.serialization.json.Json


class AddPillActivity : AppCompatActivity() {

    private var myPill: PillInfo? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pill)


//        spinner part  ------------------------------------------------------------------
        val spinner: Spinner = findViewById(R.id.spinnerAmountType)
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
// Spinner Part END ------------------------------------------------------------------------------


//        Save Button Part ------------------------------------------------------------------------

        saveAddPillBtn.setOnClickListener {
            val name = etPillName.text.toString().trim()
            val duration = etDuration.text.toString().trim().toIntOrNull()
            val frequency = etFrequency.text.toString().trim().toIntOrNull()
            val amount = etPillAmount.text.toString().trim().toIntOrNull()
            val amountType = spinnerAmountType.selectedItem.toString().trim()
            val remindTime = tvPillTimePicker.text.toString().trim()
            val doctorNote = etDoctorNote.text.toString().trim()

            var isFormFilled = false


            if(name.isEmpty()) {
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

            if(remindTime.isEmpty()){
                Toast.makeText(this, "Remind Time is Empty!", Toast.LENGTH_SHORT).show()
            }

            if(name.isNotEmpty()
                && duration != null
                && frequency != null
                && amount != null
                && amountType.isNotEmpty()
                && remindTime.isNotEmpty()
            ) { isFormFilled = true

//                after data saved in database correctly, make the toast
                Toast.makeText(this, "Pill Info is Saved!", Toast.LENGTH_SHORT).show()

                val pill = PillInfo(
                        if (myPill == null) 0 else myPill!!.id,
                        name,
                        duration,
                        frequency,
                        amount,
                        amountType,
                        remindTime,
                        doctorNote)





//                Data class instance to Json Text
                val pillInfoJson = Json.encodeToString(PillInfo.serializer(), pill)
                Toast.makeText(this, pillInfoJson, Toast.LENGTH_SHORT).show()

//                Json text to Data class instance
                val pillInfoText = Json.decodeFromString(PillInfo.serializer(), pillInfoJson)

//              get configuration
                val json = Json { allowStructuredMapKeys = true }



//                Add Pill to Database
                 addPillRecord(isFormFilled, pill)


                Intent(this, MainActivity::class.java).also{
//                    it.putExtra("this pill's info", pill)
//                    val pillInfo = intent.getSerializableExtra("this pill's info") as PillInfo
//                    tvMain.text = pillInfo.toString()
                    startActivity(it)
                }
            }
//
        }
        }

    //    function to save pill info to database
    private fun addPillRecord(isFormFilled:Boolean, pill:PillInfo){
        val databaseHandler: PillDatabaseHandler = PillDatabaseHandler(this)
        if(isFormFilled){
            Log.e("AddPillActivity","isFormFilled = $isFormFilled")
//            val pillInfoJson = Json.encodeToString(PillInfo.serializer(), pill)
            val status = databaseHandler.addPill(pill)
            Log.e("AddPillActivity","status = $status")
            if(status > -1){
                setResult(Activity.RESULT_OK)
                Toast.makeText(this, "Pill Saved to DataBase", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Pill Save to DataBase FAILED!", Toast.LENGTH_SHORT).show()
            }
        }
    }


        fun showTimePickerDialog(v: View) {
            AddPillTimePickerFragment().show(supportFragmentManager, "timePicker")
        }



    }











