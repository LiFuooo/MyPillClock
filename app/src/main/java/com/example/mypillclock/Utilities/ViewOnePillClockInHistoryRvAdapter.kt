package com.example.mypillclock.Utilities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.DataClass.PillScheduleTimeDataClass
import com.example.mypillclock.DataClass.PillTimeCompareDataClass
import com.example.mypillclock.R
import com.example.mypillclock.databinding.ItemOnePillClockinHistoryBinding
import com.example.mypillclock.databinding.ItemPillBinding


class ViewOnePillClockInHistoryRvAdapter(
    var context: Context,
    var arrayList: MutableList<PillTimeCompareDataClass>,
) :
    RecyclerView.Adapter<ViewOnePillClockInHistoryRvAdapter.ViewHolder>() {

    private val TAG = "ViewOnePillClockInHistoryRvAdapter"
    private lateinit var binding: ItemOnePillClockinHistoryBinding
//    private var mOnCategoryListener: OnCategoryListener? = null



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewOnePillClockInHistoryRvAdapter.ViewHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_one_pill_clockin_history, parent, false)

        return ViewHolder(viewHolder)
    }

    override fun onBindViewHolder(
        holder: ViewOnePillClockInHistoryRvAdapter.ViewHolder,
        position: Int
    ) {
        val pillClockInCompareData: PillTimeCompareDataClass = arrayList[position]
        holder.itemView.apply {
            binding.tvLlOnePillClockinHistoryNameItem.text = pillClockInCompareData.pillName
            binding.tvLlOnePillClockinHistoryPlanTimeItem.text = DateTimeFormatConverter().timeLongToDateTimeString(
                pillClockInCompareData.scheduleTime
            )
            binding.tvLlOnePillClockinHistoryActualTimeItem.text = if(pillClockInCompareData.clockInTime != null){
                DateTimeFormatConverter().timeLongToDateTimeString(pillClockInCompareData.clockInTime)
            } else{
                "None"
            }
            if(pillClockInCompareData.isClockIn){
                binding.ivLlOnePillClockinHistoryIstakenItem.setImageResource(R.drawable.ic_baseline_check_24)
            } else{
                binding.ivLlOnePillClockinHistoryIstakenItem.setImageResource(R.drawable.ic_baseline_clear_24)
            }
        }
    }


    override fun getItemCount(): Int {
        return arrayList.size
    }



    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)



}

