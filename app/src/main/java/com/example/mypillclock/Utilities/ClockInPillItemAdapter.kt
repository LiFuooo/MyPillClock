package com.example.mypillclock.Utilities

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.PillClockInDBHelper
import com.example.mypillclock.Database.pillInfoDBHelper
import com.example.mypillclock.R
import kotlinx.android.synthetic.main.item_clockin.view.*


// https://github.com/android/views-widgets-samples/tree/main/RecyclerViewKotlin/
// https://github.com/carotkut94/Learn-Recycler-View
// https://www.codeproject.com/Tips/1229751/Handle-Click-Events-of-Multiple-Buttons-Inside-a
// https://www.youtube.com/watch?v=fiPrO7jpt0Y

open class ClockInPillItemAdapter(

    val context: Context,
    var itemsList:MutableList<PillInfo>,
    val onClick: (Int) -> Unit):
    RecyclerView.Adapter<ClockInPillItemAdapter.pillClockInViewHolder>() {
    private val TAG = "ClockInPillItemAdapter"

    private var onClickListener: OnItemClickListener? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): pillClockInViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_clockin,
            parent,
            false
        )
        return pillClockInViewHolder(itemView,onClick,Btn1OnClick)
    }


    override fun onBindViewHolder(holder: pillClockInViewHolder, position: Int) {
        val currentItem = itemsList[position]
        val displayString = currentItem.name + ",  " + currentItem.RemindTime +
                " ,id = ${currentItem.id}"
        val currentTime = System.currentTimeMillis()


        holder.itemView.apply {
            tv_clockInItemPillName.text = currentItem.name
        }
    }


//
//
//            holder.pillClockInItemImageView.setOnClickListener {
//                holder.pillClockInItemImageView.setImageResource(R.drawable.ic_baseline_snowboarding_24)
//                PillClockInDBHelper().addClockInRecord(currentItem, currentTime)
//            }
//
//
//
//
//            holder.itemView.setOnClickListener {
//                if (onClickListener != null) {
//                    onClickListener!!.onClick(position, currentItem)
//                }
//
//            }
//
//            if (position % 2 == 0) {
//                clockInItem.setBackgroundColor(
//                    ContextCompat.getColor(
//                        context,
//                        R.color.colorLightGray
//                    )
//                )
//            } else {
//                clockInItem.setBackgroundColor(
//                    ContextCompat.getColor(
//                        context,
//                        R.color.white
//                    )
//                )
//            }
//        }
//    }


    override fun getItemCount() = itemsList.size


    fun setOnClickListener(onClickListener: OnItemClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnItemClickListener {
        fun onClick(position: Int, model: PillInfo)
    }



    inner class pillClockInViewHolder(itemView: View, onClick: (Int) -> Unit, Btn1OnClick:(Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var clockInLinearLayout = itemView.findViewById<LinearLayout>(R.id.ll_pillClockIn_image)
        var pillImage = itemView.findViewById<ImageView>(R.id.iv_pillclockInItem)
        var pillName = itemView.findViewById<TextView>(R.id.tv_clockInItemPillName)
        var btnPillClockinAdd = itemView.findViewById<Button>(R.id.btn_pill_clockin_add)
        var btnPillClockinHistory = itemView.findViewById<Button>(R.id.btn_pill_clockin_history)

        var pillClockInImageListener: PillClockInImageListener = onClick
        var pillClockInBtn1Listener: PillClockInBt1Listener = Btn1OnClick
        val now = System.currentTimeMillis()

        init {
            clockInLinearLayout.setOnClickListener(this)
            btnPillClockinAdd.setOnClickListener(this)
        }


        fun clockInLinearLayoutClick(position: Int){
            val pillInfo = pillInfoDBHelper().getPillListFromDB()[position]
            clockInLinearLayout.setOnClickListener {
                Toast.makeText(context, "Clock In Image Clicked", Toast.LENGTH_SHORT).show()
//            add clock in data to DB
                PillClockInDBHelper().addClockInRecord(pillInfo, now)
                Toast.makeText(context, "pillInfo Clock In: $pillInfo", Toast.LENGTH_SHORT).show()

        }
        }

         fun pillClockInAddBtnClick(position: Int) {
             val pillInfo = pillInfoDBHelper().getPillListFromDB()[adapterPosition]
             btnPillClockinAdd.setOnClickListener {
                 Toast.makeText(context, "btnPillClockinAdd Clicked", Toast.LENGTH_SHORT).show()
//            add clock in data to DB
//                PillClockInDBHelper().addClockInRecord(pillInfo, now)
//                Toast.makeText(context, "pillInfo Clock In: $pillInfo", Toast.LENGTH_SHORT).show()

             }
         }

        override fun onClick(view: View) {
            Toast.makeText(context, "ClockInPillItemAdpter onClick", Toast.LENGTH_SHORT).show()
            Log.i(TAG, "pillClockInImageListener override onClick: $adapterPosition")
            pillClockInImageListener(adapterPosition)
            pillClockInBtn1Listener(adapterPosition)

        }

        fun imageOnClick(view: View) {
            Toast.makeText(context, "fun image onClick", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "pillClockInImageListener onClick: $adapterPosition")
            pillClockInImageListener(adapterPosition)
            pillClockInBtn1Listener(adapterPosition)

        }

    }


//    inner class clockInItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
//        View.OnClickListener {
//        val pillClockInItemImageView = itemView.ll_pillClockIn
//        val clockInItemPillName = itemView.clockInItemTvPillName
//        val ClockInItemBtn = itemView.tv_clockInItemPillName
//
////        init {
////            itemView.setOnClickListener(this)
////        }
//
//
//        fun onClick(p0: View?) {
//            val position = adapterPosition
//            if (position != RecyclerView.NO_POSITION) {
////                listener.onItemClick(AdapterView,p0,position,0)
//            }
//        }
//    }
//        R.id.btn_pill_clockin_add

//            public void onClick(View v) {
//                onClickListener.classOnClick(v, getAdapterPosition());
//            }
//        })
//        mDaysBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickListener.daysOnClick(v, getAdapterPosition());
//            }
//        })



}

typealias PillClockInImageListener  = (Int) -> Unit
typealias PillClockInBt1Listener  = (Int) -> Unit





