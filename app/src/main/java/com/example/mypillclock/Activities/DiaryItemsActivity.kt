package com.example.mypillclock.Activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypillclock.DataClass.DiaryItemDataClass
import com.example.mypillclock.DataClass.DiaryMainDataClass
import com.example.mypillclock.Database.DiaryCategoryDbHelper
import com.example.mypillclock.Database.DiaryItemDBHelper
import com.example.mypillclock.DefaultDataObjects.Drink
import com.example.mypillclock.R
import com.example.mypillclock.Utilities.DiaryItemRvAdapter
import kotlinx.android.synthetic.main.activity_diary_item_show.*
import kotlinx.android.synthetic.main.activity_diary_main.*
import org.jetbrains.exposed.sql.SizedIterable

class DiaryItemsActivity: AppCompatActivity() {

    private val TAG = "DiaryItemsActivity"
    private var diaryItemFromCategoryAdapter: DiaryItemRvAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_item_show)


        DiaryItemDBHelper().setAllDefaultObjectsIntoDB()
        Log.i(TAG, intent.extras.toString())
        if (intent.extras != null) {
            val position = intent.getIntExtra("holder position", 0)
           Log.i(TAG, "position = $position")
            val itemList = getItemListFromDb(position)
//            setListDataIntoDiaryItemRV(itemList)
        }

//        if (DiaryItemInCategoryDBHelper().isDBTableEmpty()) {
//            DiaryItemInCategoryDBHelper().setAllDefaultObjectsIntoDB()
//        }


    }


    private fun initRecyclerView() {
        val onClick = this::onItemClick
        diaryItemFromCategoryAdapter = DiaryItemRvAdapter(
            this,
            Drink.list,
            onClick
        )
        rv_diary_item_in_category.adapter = diaryItemFromCategoryAdapter
        rv_diary_item_in_category.layoutManager = GridLayoutManager(
            this,
            3,
            LinearLayoutManager.VERTICAL,
            false
        )
    }
//
//    private fun getIncomingIntent(intent: Intent): String? {
//        var comingPosition: String? = null
//        if (intent.hasExtra("image_url") && intent.hasExtra("image_name")) {
//            Log.d("Food_getIncomingIntent", "getIncomingIntent: found intent extras.")
//            comingPosition = intent.getStringExtra("holder position")
////            val comingCategoryName = intent.getStringExtra("category name")
//        }
//        return comingPosition
//    }

    fun getCategoryName(CategoryPosition: Int): String {
        val categoryList = DiaryCategoryDbHelper().getCategoryListFromDB()
        return categoryList[CategoryPosition].categoryName
    }

//    fun getItemList(position: Int){
//        return transaction {
//            DiaryCategoryDbHelper.DiaryCategoryEntity[position].items.map{
//                DiaryItemInCategoryDataClass(
//                    it.id.value,
//                    it.category.categoryName,
//                    it.itemName,
//                    it.itemIcon)
//            }.toList()
//        }
//    }


    private fun onItemClick(position: Int) {
//        TODO: add clock-in data into DB
    }




   private fun getItemListFromDb(categoryId: Int): MutableList<DiaryItemDataClass> {
     return DiaryCategoryDbHelper.DiaryCategoryEntity[categoryId].items.toDataClassList().toMutableList()
    }

    private fun SizedIterable<DiaryItemDBHelper.DiaryItemEntity>.toDataClassList() =
        map { categoryTableyEntity ->
            categoryTableyEntity.toDataClass()
        }

    fun DiaryItemDBHelper.DiaryItemEntity.toDataClass() =
        DiaryItemDataClass(
            id.value,
            category.toDataClass(),
            itemName,
            itemIcon
        )

    fun DiaryCategoryDbHelper.DiaryCategoryEntity.toDataClass(): DiaryMainDataClass {
        return let { DiaryMainDataClass(it.id.value, it.icons, it.categoryName) }
    }



    private fun setListDataIntoDiaryItemRV(list: MutableList<DiaryItemDataClass>) {
        val onClick = this::onItemClick
        diaryItemFromCategoryAdapter = DiaryItemRvAdapter(this, list, onClick)
        rv_diary_main.adapter = diaryItemFromCategoryAdapter
        rv_diary_main.layoutManager = GridLayoutManager(
            this,
            3,
            LinearLayoutManager.VERTICAL,
            false
        )
    }


//    fun setListDataIntoDiaryItemRV(DiaryItemList: MutableList<DiaryItemInCategoryDataClass>) {
//
//
//        rv_diary_item_in_category.layoutManager = LinearLayoutManager(this)
//
//        val itemAdapter = DiaryItemFromCategoryRvAdapter(this, DiaryItemList)
//        rv_diary_item_in_category.adapter = itemAdapter
//
//
//        var diaryItemAdapter = DiaryItemInEachCategoryRvAdapter(this, onCategoryClickListener)
//        rv_diary_item_in_category.adapter = diaryItemAdapter
//        rv_diary_item_in_category.layoutManager = GridLayoutManager(
//            this,
//            3,
//            LinearLayoutManager.VERTICAL,
//            false
//        )
//    }


}




    //    function to save pill info to database
//    private fun addDiaryItemClockInRecord(itemClicked: DiaryItemClockInDataClass) {
//        val databaseHelper = DiaryItemClockInDBHelper()
//            try {
//                val currentTime = System.currentTimeMillis()
//                databaseHelper.addDiaryItemClockInRecord(itemClicked,currentTime)
//                setResult(Activity.RESULT_OK)
//                Toast.makeText(this, "Pill Saved to DataBase", Toast.LENGTH_SHORT).show()
//            } catch (e: Exception) {
//
//                Toast.makeText(this, "Pill Save to DataBase FAILED!", Toast.LENGTH_SHORT).show()
//            }
//
//    }



