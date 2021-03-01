package com.example.mypillclock.Utilities

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.Activities.DiaryItemsActivity
import com.example.mypillclock.DataClass.DiaryItemDataClass
import com.example.mypillclock.DefaultDataObjects.Drink
import com.example.mypillclock.DefaultDataObjects.Exercise
import com.example.mypillclock.DefaultDataObjects.Food
import com.example.mypillclock.DefaultDataObjects.Smoke
import com.example.mypillclock.R


class DiaryItemRvAdapter(var context: Context,
                         var diaryItemList: MutableList<DiaryItemDataClass>,
                         val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<DiaryItemRvAdapter.ViewHolder>(){


    val TAG = "DiaryItemsRv"
    private val myOnItemInCategoryListener: OnItemInCategoryListener? = null



    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int):
            DiaryItemRvAdapter.ViewHolder {

        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_item_diary_item_detail, parent, false)
        return ViewHolder(viewHolder,onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val arrayList = findArrayList(position)
        val diaryCategory: DiaryItemDataClass = arrayList[position]


        holder.itemName.text = diaryCategory.itemName
        holder.icons.setImageResource(diaryCategory.itemIcon)
//        val diaryItemClicked = DiaryItemClockInDataClass(
//            0,
//            diaryCategory.category,
//            "item Name clicked",
//            System.currentTimeMillis()
//        )

        holder.itemView.setOnClickListener {
            Log.i("viewHolder postion", position.toString())
            Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
//            DiaryItemClockInDBHelper().addDiaryItemClockInRecord(diaryItemClicked)
//

            val intent = Intent(context, DiaryItemsActivity::class.java)
            intent.putExtra("holder position", position)
            intent.putExtra("category name", diaryCategory.category)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return diaryItemList.size
    }

    fun findArrayList(position: Int): MutableList<DiaryItemDataClass> {
        return when (position){
            0 -> Food.list
            1 -> Drink.list
            2 -> Exercise.list
            3 -> Smoke.list
            else  -> Food.list
        }
    }




     inner class ViewHolder(itemView: View, onClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {


         var icons = itemView.findViewById<ImageView>(R.id.iv_diary_item_cardView)
         var itemName = itemView.findViewById<TextView>(R.id.tv_diary_item_cardView)

        var onItemInCategoryListener: OnItemInCategoryListener = onClick

         init {
             itemView.setOnClickListener(this)
         }

        override fun onClick(view: View) {
            Log.d(TAG, "onClick: $adapterPosition")
            onItemInCategoryListener(adapterPosition)
        }

    }

}

typealias OnItemInCategoryListener  = (Int) -> Unit