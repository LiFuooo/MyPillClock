package com.example.mypillclock.Database

import android.util.Log
import com.example.mypillclock.DataClass.DiaryItemInCategoryDataClass
import com.example.mypillclock.DataClass.diaryMainDataClass
import com.example.mypillclock.DefaultDataObjects.Food
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.transactions.transaction

class DiaryItemInCategoryDBHelper {
    object DiaryItemInCategoryTable : IntIdTable() {
        val categoryName: Column<String> = varchar("category", 50)
        val itemName: Column<String> = varchar("name", 50)
        val itemIcon:Column<Int> = integer("itemIcon")


        override val primaryKey = PrimaryKey(id, name = "Diary Item in Category")
    }

    //    An entity instance or a row in the table is defined as a class instance:
    class DiaryItemInCategoryTableyEntity(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<DiaryItemInCategoryTableyEntity>(DiaryItemInCategoryTable)
        var categoryName by DiaryItemInCategoryTable.categoryName
        var itemName by DiaryItemInCategoryTable.itemName
        var itemIcon by DiaryItemInCategoryTable.itemIcon
    }

    fun addDiaryItemInCategory(diaryCategoryData: diaryMainDataClass) {
        val diaryItemInCategoryString = Json.encodeToString(diaryMainDataClass.serializer(), diaryCategoryData)
        Log.e("diaryItemInCategoryStr", "diaryItemInCategoryString = $diaryItemInCategoryString")
        val diaryItemInCategorysJson = Json.decodeFromString(DiaryItemInCategoryDataClass.serializer(), diaryItemInCategoryString)
        Log.e("diaryItemInCatesJson", "diaryMainDataClassJson = $diaryItemInCategorysJson")


        transaction {
            DiaryItemInCategoryTableyEntity.new {
                categoryName = diaryItemInCategorysJson.categoryName
                itemName = diaryItemInCategorysJson.itemName
                itemIcon = diaryItemInCategorysJson.itemIcon
            }
        }

    }

    fun defautListOfCategory(categoryName:String){

        Food.some
    }


}

//object Food{
//    DiaryItemInCategoryDataClass()
//}