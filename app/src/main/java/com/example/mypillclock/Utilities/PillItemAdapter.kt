package com.example.mypillclock.Utilities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.pillInfoDBHelper
import com.example.mypillclock.R
import kotlinx.android.synthetic.main.item_pill.view.*


//https://github.com/android/views-widgets-samples/tree/main/RecyclerViewKotlin/

open class PillItemAdapter(
        val context:Context,
        var itemsList:MutableList<PillInfo>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_pill,
            parent,
            false)
        return PillViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = itemsList[position]
        val displayString = currentItem.name + ",  " + currentItem.RemindTime+
                " ,id = ${currentItem.id}"


        holder.itemView.apply {
            tvPillItem_Qty.text = currentItem.amount.toString() +" " + currentItem.amountType
            tvPillItem_content.text = displayString

            holder.itemView.setOnClickListener{
                if(onClickListener != null){
                    onClickListener!!.onClick(position, currentItem)
                }
            }

            if (position % 2 == 0) {
                llPillItem.setBackgroundColor(
                        ContextCompat.getColor(
                                context,
                                R.color.colorLightGray))
            } else {
                llPillItem.setBackgroundColor(
                        ContextCompat.getColor(
                                context,
                                R.color.white))
            }
        }
    }



    override fun getItemCount()= itemsList.size


    fun removeAt(position: Int){
        val dbHandler = pillInfoDBHelper()
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

    class PillViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

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

