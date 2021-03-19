package com.example.mypillclock.Utilities

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.Activities.ActivityAddMissingPillClockinRecord
import com.example.mypillclock.Activities.ActivityViewOnePillClockInHistory
import com.example.mypillclock.DataClass.PillClockInDataClass
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.PillClockInDBHelper
import com.example.mypillclock.Database.PillInfoDBHelper
import com.example.mypillclock.Database.PillTimeCompareDBHelper
import com.example.mypillclock.R
import kotlinx.android.synthetic.main.item_clockin.view.*


// https://github.com/android/views-widgets-samples/tree/main/RecyclerViewKotlin/
// https://stackoverflow.com/questions/45474333/how-can-i-set-onclicklistener-to-two-buttons-in-recyclerview
// https://stackoverflow.com/questions/28767413/how-to-open-a-different-activity-on-recyclerview-item-onclick#28767516

open class ClockInPillItemAdapter(

    val context: Context,
    var itemsList: MutableList<PillInfo>,
    var myListenerObject: MultipleListenerClass
):
    RecyclerView.Adapter<ClockInPillItemAdapter.pillClockInViewHolder>() {
    private val TAG = "ClockInPillItemAdapter"
//    private var onClickListener: MultipleListenerInterface? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): pillClockInViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_clockin,
            parent,
            false
        )

        return pillClockInViewHolder(itemView, myListenerObject)
    }


    override fun onBindViewHolder(holder: pillClockInViewHolder, position: Int) {
        val currentItem = itemsList[position]
        val displayString = currentItem.name + ",  " + currentItem.RemindTime +
                " ,id = ${currentItem.id}"
        val currentTime = System.currentTimeMillis()


        holder.itemView.apply {
            tv_clockInItemPillName.text = currentItem.name


            if (position % 2 == 0) {
                ll_pillClockIn_item.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorLightGray
                    )
                )
            } else {
                clockInItem.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
            }

        }
        }



    override fun getItemCount() = itemsList.size



    class MultipleListenerClass(context: Context) {

        val context = context
        val getSavedPillList = PillInfoDBHelper().getPillListFromDB()

        fun onImageClick(position: Int){
            Log.i("TAG", "onImageClick")
            val now = System.currentTimeMillis()
            val pillClicked = getSavedPillList[position]


//            ------------------------------------------
            //            test part
            val pillInfo = pillClicked
            val firstScheduleTimeString = pillInfo.remindStartDate + " " + pillInfo.RemindTime
            Log.i("firstScheduleTimeString", firstScheduleTimeString)
            val firstScheduleTimeLong =
                DateTimeFormatConverter().dateTimeStringToLong(firstScheduleTimeString)
            Log.i("firstScheduleTimeLong", firstScheduleTimeLong.toString())
            val timeBetweenTwoReminder = (24 / pillInfo.frequency) * 60 * 60 * 1000
            Log.i("timeBetweenTwoReminder", timeBetweenTwoReminder.toString())
            val qtyInThisBottle = pillInfo.quantity
            Log.i("qtyInThisBottle", qtyInThisBottle.toString())
            val qtyPerDose = pillInfo.amount
            val doseQty = qtyInThisBottle / qtyPerDose
            val isRepetitive = pillInfo.isRepetitive
            Log.i("isRepetitive", isRepetitive.toString())

            val scheduleIndex = ((now - firstScheduleTimeLong) / timeBetweenTwoReminder).toInt()
            Log.i("scheduleIndex", scheduleIndex.toString())
            val lastScheduleIndex = scheduleIndex + 1
            Log.i("lastScheduleIndex", lastScheduleIndex.toString())
            val latestScheduleTimeLong = PillTimeCompareDBHelper().latestScheduleTimeLong(pillInfo)
            Log.i("latestScheduleTimeLong", latestScheduleTimeLong.toString())
            val nextScheduleTimeLong = PillTimeCompareDBHelper().nextScheduleTimeLong(pillInfo)
            Log.i("nextScheduleTimeLong", nextScheduleTimeLong.toString())
            Log.i("now", now.toString())
            val allClockInTime = PillTimeCompareDBHelper().allClockInTime(pillInfo)
            Log.i("allClockInTime", allClockInTime.toString())
//            val clockInList = PillTimeCompareDBHelper().isClockInList(pillInfo)
//            clockInList.forEach {
//                Log.i("clockIn", it.toString())
//            }

            val isTheLatestScheduleClockedIn = PillTimeCompareDBHelper().isTheLatestScheduleClockedIn(pillClicked)
            Log.i("isLatestSchedClockedIn",isTheLatestScheduleClockedIn.toString())

            if(!isTheLatestScheduleClockedIn){
                val pillClockInData = PillClockInDataClass(0,pillClicked,"Medicine", now)
                PillClockInDBHelper().addClockInRecord(pillClockInData)
                Log.i("pill clock in", "${getSavedPillList[position]}")
                Log.i("pill clockin time Long","${pillClockInData.timeClockIn}")
                Toast.makeText(context, "${pillClicked.name} Clock-in Success!", Toast.LENGTH_SHORT).show()
            } else{
//                cancelClockIn
            }

//-----------------------------------------------------------------


//            val pillClockInData = PillClockInDataClass(0,pillClicked,"Medicine", now)
//            PillClockInDBHelper().addClockInRecord(pillClockInData)
//            Log.i("pill clock in", "${getSavedPillList[position]}")
//            Log.i("pill clockin time Long","${pillClockInData.timeClockIn}")
//            Toast.makeText(context, "${pillClicked.name} Clock-in Success!", Toast.LENGTH_SHORT).show()


        }
        fun onBtnAddClick(position: Int) {
            // Implement your functionality for onDelete here
            Log.i("TAG", "on Button 1 Click")
            val pillClicked = getSavedPillList[position]
            Log.i("pill name","${pillClicked.name}")
            val intent = Intent(context, ActivityAddMissingPillClockinRecord::class.java)
            intent.putExtra("pill position", position)
            intent.putExtra("pill name", pillClicked.name)
            intent.putExtra("pill id", pillClicked.id)
            context.startActivity(intent)
        }
        fun onBtnHistoryClick(position: Int){
            Log.i("this", "on Button 2 Click")
            val pillClicked = getSavedPillList[position]
            val intent = Intent(context, ActivityViewOnePillClockInHistory::class.java)
//            intent.putExtra("pill position", position)
            intent.putExtra("pill id", pillClicked.id)
            context.startActivity(intent)
        }
    }

//
//    interface MultipleListenerInterface1 {
//        fun onImageClick(position: Int, model: PillInfo)
//        fun onBtnAddClick(position: Int, model: PillInfo)
//        fun onBtnHistoryClick(position: Int, model: PillInfo)
//    }


    inner class pillClockInViewHolder(
        itemView: View,
        listener: MultipleListenerClass
    ) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var clockInLinearLayout = itemView.findViewById<LinearLayout>(R.id.ll_pillClockIn_image)
        var pillImage = itemView.findViewById<ImageView>(R.id.iv_pillclockInItem)
        var pillName = itemView.findViewById<TextView>(R.id.tv_clockInItemPillName)
        var btnPillClockinAdd = itemView.findViewById<Button>(R.id.btn_pill_clockin_add)
        var btnPillClockinHistory = itemView.findViewById<Button>(R.id.btn_pill_clockin_history)
        val pillInfo = itemsList[adapterPosition + 1]

        val now = System.currentTimeMillis()

        var multipleListener = listener

        init {
            clockInLinearLayout.setOnClickListener(this)
            btnPillClockinAdd.setOnClickListener(this)
            btnPillClockinHistory.setOnClickListener(this)
        }




        override fun onClick(view: View) {
            Log.i("adapter Position", "$adapterPosition")
            Log.i("itemsList count", "${itemsList.count()}")
                when (view.id) {
                    R.id.ll_pillClockIn_image -> {
                        multipleListener.onImageClick(this.layoutPosition)
                        this.pillImage.setImageResource(R.drawable.ic_baseline_wb_sunny_24)

                    }

                    R.id.btn_pill_clockin_add -> multipleListener.onBtnAddClick(
                        this.layoutPosition

                    )
                    R.id.btn_pill_clockin_history -> multipleListener.onBtnHistoryClick(
                        this.layoutPosition

                    )
                    else -> {
                    }
                }
        }


    }
}


