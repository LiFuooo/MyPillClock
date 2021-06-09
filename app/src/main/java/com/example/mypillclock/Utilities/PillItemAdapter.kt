package com.example.mypillclock.Utilities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.PillInfoDBHelper
import com.example.mypillclock.R
import com.example.mypillclock.databinding.ItemPillBinding


//https://github.com/android/views-widgets-samples/tree/main/RecyclerViewKotlin/

open class PillItemAdapter(
        val context:Context,
        var itemsList:MutableList<PillInfo>
) : RecyclerView.Adapter<PillItemAdapter.PillViewHolder>() {

    private var onClickListener: OnClickListener? = null
//    private lateinit var binding:ItemPillBinding

    inner class PillViewHolder(val binding: ItemPillBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PillViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_pill,
            parent,
            false)
        val binding = ItemPillBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PillViewHolder(binding)
    }



    override fun onBindViewHolder(holder: PillViewHolder, position: Int) {
        val currentItem = itemsList[position]
        val displayString = currentItem.name + ",  " + currentItem.RemindTime+
                " ,id = ${currentItem.id}"


        holder.apply {
            binding.tvPillItemQty.text = currentItem.amount.toString() +" " + currentItem.amountType
            binding.tvPillItemContent.text = displayString

            holder.itemView.setOnClickListener{
                if(onClickListener != null){
                    onClickListener!!.onClick(position, currentItem)
                }
            }

            if (position % 2 == 0) {
                binding.llPillItem.setBackgroundColor(
                        ContextCompat.getColor(
                                context,
                                R.color.colorLightGray))
            } else {
                binding.llPillItem.setBackgroundColor(
                        ContextCompat.getColor(
                                context,
                                R.color.white))
            }
        }
    }



    override fun getItemCount()= itemsList.size


    fun removeAt(position: Int){
        val dbHandler = PillInfoDBHelper()
        try {
            dbHandler.deletePill(itemsList[position])
        } catch (e:Exception){

        }

//        if(isDeleted > 0){
//            itemsList.removeAt(position)
////            notifyItemMoved(position-1,position)
        }


    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    interface OnClickListener{
        fun onClick(position:Int, model: PillInfo)
    }




//    View.OnClickListener {
//        var etPillName = itemView.etPillName
//        var etDuration = itemView.etDuration
//        var etFrequency = itemView.etFrequency
//        var etPillAmount = itemView.etPillAmount
////        var spinnerAmountType = itemView.spinnerAmountType.selectedItem
//        var tvPillTimePicker = itemView.tvPillTimePicker
//        var etDoctorNote = itemView.etDoctorNote
////        var AmountTypeIndex = itemView.spinnerAmountType.selectedItemPosition
//
//
//
//
//    init{
//        itemView.setOnClickListener(this)
//    }
//
//        override fun onClick(p0: View?) {
//            TODO("Not yet implemented")
//        }
//    }




}

