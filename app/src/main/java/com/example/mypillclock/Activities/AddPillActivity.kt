package com.example.mypillclock.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mypillclock.Alarm.AlarmSchedule
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.PillInfoDBHelper
import com.example.mypillclock.Fragments.AddPillDatePickerFragment
import com.example.mypillclock.Fragments.AddPillTimePickerFragment
import com.example.mypillclock.R
import kotlinx.android.synthetic.main.activity_add_pill.*
import kotlinx.serialization.json.Json


class AddPillActivity : AppCompatActivity() {

    private var myPill: PillInfo? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pill)

        var radioGroup: RadioGroup? = null
        lateinit var radioButton: RadioButton


//        TODO: is this bottle repetitive radio botton
//        rg_add_pill.setOnCheckedChangeListener(
//            RadioGroup.OnCheckedChangeListener { group, checkedId ->
//                val radio: RadioButton = findViewById(checkedId)
//                var isRepetitive = radio.text == R.string.str_add_pill_rb_only_this_bottle.toString()
//                Log.i("isRepetitive", isRepetitive.toString())
//            })
//        val rbSelectedItemId: Int = radioGroup!!.checkedRadioButtonId
//        var isRepetitive = rbSelectedItemId.text == R.string.str_add_pill_rb_only_this_bottle.toString()
//
//
//        var isRepetitive = textOfRbSelected()== R.string.str_add_pill_rb_only_this_bottle.toString()
//        // Assigning id of RadioGroup
//        radioGroup = findViewById(R.id.rg_add_pill)

        // Assigning id of Submit button
//        button = findViewById(R.id.submitButton)

        // Actions to be performed
        // when Submit button is clicked
//        button.setOnClickListener {
//
//            // Getting the checked radio button id
//            // from the radio group
//            val rbSelectedItemId: Int = radioGroup!!.checkedRadioButtonId
//
//            // Assigning id of the checked radio button
//            radioButton = findViewById(rbSelectedItemId)
//            radioButton.text
//
//            // Displaying text of the checked radio button in the form of toast
//            Toast.makeText(baseContext, radioButton.text, Toast.LENGTH_SHORT).show()
//        }





//        TODo(PillType spinner part) ------------------------------------------------------------------
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
// ToDO(Spinner Part END )------------------------------------------------------------------------------


//        set toolTipText for Radio Bottons
//        for some reason, this doesn't work
        toolTipText()

//        Save Button Part ------------------------------------------------------------------------

        saveAddPillBtn.setOnClickListener {
            val name = etPillName.text.toString().trim()
            val duration = et_add_pill_quantity.text.toString().trim().toIntOrNull()



            var isRepetitive = isPillReminderRepetitive()
            val frequency = etFrequency.text.toString().trim().toIntOrNull()
            val amount = etPillAmount.text.toString().trim().toIntOrNull()
            val amountType = spinnerAmountType.selectedItem.toString().trim()
            val remindStartDate = tvPillDatePicker.text.toString().trim()
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

            if (remindStartDate.isEmpty()) {
                Toast.makeText(this, "Remind start date is Empty!", Toast.LENGTH_SHORT).show()
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
                && remindStartDate.isNotEmpty()
                && remindTime.isNotEmpty()
                && rxNumber.isNotEmpty()
            ) {
                isFormFilled = true

//                after data saved in database correctly, make the toast
                Toast.makeText(this, "Pill Info is Saved!", Toast.LENGTH_SHORT).show()


                val pill = PillInfo(
                    if (myPill == null) 0 else myPill!!.id,
                    name,
                    duration,
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
                val pillInfoJson = Json.encodeToString(PillInfo.serializer(), pill)
                Toast.makeText(this, pillInfoJson, Toast.LENGTH_SHORT).show()

//                Json text to Data class instance
                val pillInfoText = Json.decodeFromString(PillInfo.serializer(), pillInfoJson)

//              get configuration
                val json = Json { allowStructuredMapKeys = true }


//                1. Add Pill to Database
                addPillRecord(isFormFilled, pill)
//                2. get pillEntity By DataClass
//                transaction {

                val pillEntity = PillInfoDBHelper().queryOnePillEntityByDataClass(pill)


//                3. schedule alarm
                if(pillEntity != null){

                AlarmSchedule(pillEntity).scheduleAlarms(this)
                }
//                }


                Intent(this, MainActivity::class.java).also {
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
    private fun addPillRecord(isFormFilled: Boolean, pill: PillInfo) {
        val databaseHelper = PillInfoDBHelper()
        if (isFormFilled) {
            Log.i("AddPillActivity", "isFormFilled = $isFormFilled")
//            val pillInfoJson = Json.encodeToString(PillInfo.serializer(), pill)
            try {
                databaseHelper.addPill(pill)
                setResult(Activity.RESULT_OK)
                Toast.makeText(this, "Pill Saved to DataBase", Toast.LENGTH_SHORT).show()
                Log.i("AddPillActivity pill", pill.toString())
            } catch (e: Exception) {
                Toast.makeText(this, "Pill Save to DataBase FAILED!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun showStartDatePickerDialog(v: View) {
        val textViewID = R.id.tvPillDatePicker
        AddPillDatePickerFragment(textViewID).show(supportFragmentManager, "datePickerAdd")
    }

    fun showTimePickerDialog(v: View) {
        val textViewId = R.id.tvPillTimePicker
        AddPillTimePickerFragment(textViewId).show(supportFragmentManager, "timePickerAdd")
    }

    fun textOfRbSelected(view: View): String {
////        val radio: RadioButton = findViewById(rg_add_pill.checkedRadioButtonId)
//        val radioGroup = rg_add_pill
//        val rbSelectedItemId: Int = radioGroup!!.checkedRadioButtonId
//        val radioButton = findViewById(rbSelectedItemId)
//        var isRepetitive = radioButton.text == R.string.str_add_pill_rb_only_this_bottle.toString()
//        return isRepetitive
//
//        // Get the clicked radio button instance
        val radio: RadioButton = findViewById(rg_add_pill.checkedRadioButtonId)
        return radio.text.toString()

    }

    private fun isPillReminderRepetitive(): Boolean {
        var radioaGroupSelectedId: Int = rg_add_pill.checkedRadioButtonId
            val radio:RadioButton = findViewById(radioaGroupSelectedId)
            val radioText = radio.text
            val mystring = resources.getString(R.string.str_add_pill_rb_pill_is_repetitve)
            var isRepetitive = radioText.toString() == mystring
        return isRepetitive
    }

    fun toolTipText(){
        var radioBtnRepetitive:RadioButton = findViewById(R.id.rb_add_pill_repetitive)
        var radioBtnOnlyOneBottle:RadioButton = findViewById(R.id.rb_add_pill_only_one_bottle)
        Log.i("andro VERSION.SDK_INT", android.os.Build.VERSION.SDK_INT.toString())
        if (android.os.Build.VERSION.SDK_INT >= 26){
            radioBtnRepetitive.tooltipText = "After finish this pill, send me notification to refill."
            radioBtnOnlyOneBottle.tooltipText = "This is the only bottle for this pill"
        } else{
        }
    }


    }














