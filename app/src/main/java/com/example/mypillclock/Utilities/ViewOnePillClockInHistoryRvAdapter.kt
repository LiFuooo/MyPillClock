package com.example.mypillclock.Utilities

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.DataClass.DiaryMainDataClass
import com.example.mypillclock.DataClass.PillScheduleTimeDataClass
import com.example.mypillclock.R
import kotlinx.android.synthetic.main.item_one_pill_clockin_history.view.*
import kotlinx.android.synthetic.main.item_pill.view.*
import kotlinx.android.synthetic.main.item_pill.view.tvPillItem_Qty

class ViewOnePillClockInHistoryRvAdapter(
    var context: Context,
    var arrayList: MutableList<PillScheduleTimeDataClass>,
                         ) :
    RecyclerView.Adapter<ViewOnePillClockInHistoryRvAdapter.ViewHolder>() {

    private val TAG = "ViewOnePillClockInHistoryRvAdapter"
//    private var mOnCategoryListener: OnCategoryListener? = null



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewOnePillClockInHistoryRvAdapter.ViewHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_one_pill_clockin_history, parent, false)

        return ViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: ViewOnePillClockInHistoryRvAdapter.ViewHolder, position: Int) {
        val pillScheduleTimeData: PillScheduleTimeDataClass = arrayList[position]
        holder.itemView.apply {
            tv_ll_one_pill_clockin_history_name_item.text = pillScheduleTimeData.name.name
            tv_ll_one_pill_clockin_history_planTime_item.text = DateTimeFormatConverter().timeLongToDateTimeString(pillScheduleTimeData.scheduleTime)
        }
    }


    override fun getItemCount(): Int {
        return arrayList.size
    }



    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)



}

