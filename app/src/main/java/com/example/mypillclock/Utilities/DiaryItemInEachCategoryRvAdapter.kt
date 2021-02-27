package com.example.mypillclock.Utilities

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mypillclock.DataClass.DiaryItemClockInDataClass
import com.example.mypillclock.DataClass.DiaryItemInCategoryDataClass
import com.example.mypillclock.Database.DiaryItemClockInDBHelper
import com.example.mypillclock.DefaultDataObjects.Drink
import com.example.mypillclock.DefaultDataObjects.Exercise
import com.example.mypillclock.DefaultDataObjects.Food
import com.example.mypillclock.DefaultDataObjects.Smoke
import com.example.mypillclock.R

class DiaryItemInEachCategoryRvAdapter (var context: Context) :
    RecyclerView.Adapter<DiaryItemInEachCategoryRvAdapter.ViewHolder>(){

    lateinit var arrayList: MutableList<DiaryItemInCategoryDataClass>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_item_diary_item_detail, parent, false)
        return ViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val arrayList = findArrayList(position)
        val diaryCategory: DiaryItemInCategoryDataClass = arrayList[position]


        holder.itemName.text = diaryCategory.itemName
        holder.icons.setImageResource(diaryCategory.itemIcon)
        val diaryItemClicked = DiaryItemClockInDataClass(
            0,
            diaryCategory.categoryName,
            "item Name clicked",
            System.currentTimeMillis())

        holder.itemView.setOnClickListener {
            Log.i("viewHolder postion", position.toString())
            Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
            DiaryItemClockInDBHelper().addDiaryItemClockInRecord(diaryItemClicked)

//
//            val intent = Intent(context, DiaryCategoryItemsActivity::class.java)
//            intent.putExtra("holder position", position)
//            intent.putExtra("category name", diaryCategory.categoryName)
//            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    fun findArrayList(position: Int): MutableList<DiaryItemInCategoryDataClass> {
        return when (position){
            0 -> Food.list
            1-> Drink.list
            2 -> Exercise.list
            3 -> Smoke.list
            else  -> Food.list
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val context = itemView.context
        var icons = itemView.findViewById<ImageView>(R.id.iv_diary_food_cardView)
        var itemName = itemView.findViewById<TextView>(R.id.tv_diary_food_cardView)
    }

}