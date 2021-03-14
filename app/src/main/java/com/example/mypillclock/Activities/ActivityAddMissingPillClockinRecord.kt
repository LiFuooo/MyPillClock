package com.example.mypillclock.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mypillclock.DataClass.PillClockInDataClass
import com.example.mypillclock.Database.PillClockInDBHelper
import com.example.mypillclock.Database.PillInfoDBHelper
import com.example.mypillclock.Fragments.AddPillDatePickerFragment
import com.example.mypillclock.Fragments.AddPillTimePickerFragment
import com.example.mypillclock.R
import com.example.mypillclock.Utilities.DataClassEntityConverter
import com.example.mypillclock.Utilities.DateTimeFormatConverter
import kotlinx.android.synthetic.main.activity_add_missing_pill_clockin_record.*
import org.jetbrains.exposed.sql.transactions.transaction


// https://stackoverflow.com/questions/26574277/how-to-override-method-via-keyboard-shortcut-in-android-studio
// https://www.tutorialspoint.com/how-to-use-date-time-picker-dialog-in-kotlin-android

class ActivityAddMissingPillClockinRecord:AppCompatActivity() {
    private val TAG = "ActivityAddMissingPillClockinRecord"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_missing_pill_clockin_record)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        get intent of pillName
        if (intent.extras != null) {
            val pillPositionFromIntent = intent.getIntExtra("pill position", 1)
            val pillNameFromIntent = intent.getStringExtra("pill name")
            Log.i(TAG, "pill position received= $pillPositionFromIntent")
            Log.i(TAG, "pill name received= $pillNameFromIntent")

            val tv_pillName = findViewById<TextView>(R.id.tv_add_missing_pill_clockin_pillName)
            tv_pillName.text = pillNameFromIntent

//
//            transaction {
//                val itemList = getItemListFromDb(positionFromIntent + 1)
//                diaryItemsList = itemList
//                setListDataIntoDiaryItemRV(itemList)
//                getCagetoryData()
//            }

            btn_add_missing_pill_clockin_save.setOnClickListener {
                val pillClockInDate = tv_add_missing_pill_clockin_date.text.toString().trim()
                val pillClockInTime = tv_add_missing_pill_clockin_time.text.toString().trim()
                val pillClockInDateTimeString = "$pillClockInDate $pillClockInTime"
                val pillClockInDateTimeLong =
                    DateTimeFormatConverter().dateTimeStringToLong(pillClockInDateTimeString)


                var isFormFilled = false


                if (pillClockInDate.isEmpty()) {
                    Toast.makeText(this, "Pill Clock-in Date is Empty!", Toast.LENGTH_SHORT).show()
                }

                if (pillClockInTime.isEmpty()) {
                    Toast.makeText(this, "Pill Clock-in Time is Empty!", Toast.LENGTH_SHORT).show()
                }


                if (pillClockInDate.isNotEmpty()
                    && pillClockInTime.isNotEmpty()
                ) {
                    isFormFilled = true
                    Toast.makeText(this, "Pill Clock-in Info is Saved!", Toast.LENGTH_SHORT).show()
                    transaction {
                        val clickedPillInfoEntity = PillInfoDBHelper.DBExposedPillEntity[pillPositionFromIntent+1]
                        Log.i("pillPositionFromIntent", pillPositionFromIntent.toString())
                        val clickedPillInfoDatClass = DataClassEntityConverter().pillInfoEntityToDataClass(clickedPillInfoEntity)
                        val pillClockInRecord =
                        PillClockInDataClass(
                            0,
                            clickedPillInfoDatClass,
                            "Medicine",
                            pillClockInDateTimeLong)

                        addPillClockInRecord(isFormFilled, pillClockInRecord)
                    }

                    Intent(this, PillClockInActivity::class.java).also {
                        startActivity(it)}
            }
            }




                }
//
            }




        //    function to save pill info to database
        fun addPillClockInRecord(isFormFilled: Boolean,
                                 pillClockInDataClass: PillClockInDataClass) {
            val databaseHelper = PillClockInDBHelper()
            if (isFormFilled) {
                Log.e("Add missing pillClockin", "isFormFilled = $isFormFilled")
                try {
                    databaseHelper.addClockInRecord(pillClockInDataClass)
//                    setResult(Activity.RESULT_OK)
                    Toast.makeText(this, "Pill Saved to DataBase", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.i("save pill clockin error", e.toString())
                    Toast.makeText(this, "Pill Save to DataBase FAILED!", Toast.LENGTH_SHORT).show()
                }
            }
        }






//    //setting menu in action bar
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.diary_item_toolbar, menu)
//        return true
//    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        android.R.id.home -> {
            // app icon in action bar clicked; goto parent activity.
            this.finish()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    fun pillColckInShowDatePickerDialog(view: View) {
        val textViewID = R.id.tv_add_missing_pill_clockin_date
        AddPillDatePickerFragment(textViewID).show(
            supportFragmentManager,
            "addPillClockInDatePicker"
        )
    }

    fun pillColckInShowTimePickerDialog(view: View) {
        val textViewID = R.id.tv_add_missing_pill_clockin_time
        AddPillTimePickerFragment(textViewID).show(
            supportFragmentManager,
            "addPillClockInTimePicker"
        )
    }
}