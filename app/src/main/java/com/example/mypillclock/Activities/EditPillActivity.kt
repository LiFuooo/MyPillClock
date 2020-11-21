package com.example.mypillclock.Activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.R
import kotlinx.android.synthetic.main.activity_edit_pill.*

class EditPillActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pill)

        var PillInfoDetail : PillInfo? = null
        if(intent.hasExtra(MainActivity.EXTRA_PILL_DETAIL)){
            PillInfoDetail = intent.getSerializableExtra(MainActivity.EXTRA_PILL_DETAIL) as PillInfo
        }

        if(PillInfoDetail != null){

            etPillNameEdit.setText(PillInfoDetail.name)
            etDurationEdit.setText(PillInfoDetail.duration.toString())
            etFrequencyEdit.setText(PillInfoDetail.frequency.toString())
            etPillAmountEdit.setText(PillInfoDetail.amount.toString())
//            holder.spinnerAmountType.setText(currentItem.amountType)
            tvPillTimePickerEdit.text = PillInfoDetail.RemindTime.toString()
            etDoctorNoteEdit.setText(PillInfoDetail.name)

        }
    }





}
