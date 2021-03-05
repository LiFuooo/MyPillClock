package com.example.mypillclock.Database

import android.util.Log
import com.example.mypillclock.DataClass.DiaryItemDataClass
import com.example.mypillclock.DefaultDataObjects.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

class DiaryItemDBHelper {
    object DiaryItemsTable : IntIdTable() {
        val category = reference("category", DiaryCategoryDbHelper.DiaryCategoryTable)
        val itemName: Column<String> = varchar("name", 50)
        val itemIcon:Column<Int> = integer("itemIcon")
    }

    //    An entity instance or a row in the table is defined as a class instance:
    class DiaryItemEntity(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<DiaryItemEntity>(DiaryItemsTable)
        var category by DiaryCategoryDbHelper.DiaryCategoryEntity referencedOn DiaryItemsTable.category
        var itemName by DiaryItemsTable.itemName
        var itemIcon by DiaryItemsTable.itemIcon
//        val clockIns by DiaryClockInDBHelper.DiaryItemClockInTimeEntity referrersOn DiaryClockInDBHelper.diaryItemclockInTimeTable.clockInTime
    }

    fun addDiaryItemToDB(diaryItem: DiaryItemDataClass) {
        val diaryItemInCategoryString = Json.encodeToString(DiaryItemDataClass.serializer(), diaryItem)
//        Log.i("diaryItemString", "diaryItemString = $diaryItemInCategoryString")
        val diaryItemInCategorysJson = Json.decodeFromString(DiaryItemDataClass.serializer(), diaryItemInCategoryString)
//        Log.i("diaryItemJson", "diaryItemJson = $diaryItemInCategorysJson")


        transaction {
            val categoryEntity = DiaryCategoryDbHelper.DiaryCategoryEntity[diaryItem.category.id]
            DiaryItemEntity.new {
                category = categoryEntity
                itemName = diaryItemInCategorysJson.itemName
                itemIcon = diaryItemInCategorysJson.itemIcon
            }

        }

    }


//    TODO: set one Objects into DB

    private fun setOneDefaultObjectIntoDB(defaultList:MutableList<DiaryItemDataClass>){
    for (i in 0 until defaultList.size){
        transaction {
            val listItem =  defaultList[i]
//            Log.i("listItem", listItem.itemName)
            addDiaryItemToDB(listItem)
        }
    }
    }

    fun setAllDefaultObjectsIntoDB(){
        setOneDefaultObjectIntoDB(Food.list)
        setOneDefaultObjectIntoDB(Drink.list)
        setOneDefaultObjectIntoDB(Exercise.list)
        setOneDefaultObjectIntoDB(Smoke.list)
        setOneDefaultObjectIntoDB(Feeling.list)
    }

    fun isDBTableEmpty(): Boolean {
      return DiaryItemsTable.id.count().toString().toInt() == 0
    }

    fun deleteAllItemsFromDB(){
        transaction {
            DiaryItemsTable.deleteAll()
        }
    }


}

