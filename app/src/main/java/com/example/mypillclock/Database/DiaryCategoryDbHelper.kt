package com.example.mypillclock.Database

import android.util.Log
import com.example.mypillclock.DataClass.DiaryMainDataClass
import com.example.mypillclock.DefaultDataObjects.*
import com.example.mypillclock.R
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction

class DiaryCategoryDbHelper {

    object DiaryCategoryTable : IntIdTable() {
        val icons: Column<Int> = integer("name")
        val categoryName: Column<String> = varchar("category", 50)
    }

    //    An entity instance or a row in the table is defined as a class instance:
    class DiaryCategoryEntity(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<DiaryCategoryEntity>(DiaryCategoryTable)
        var icons by DiaryCategoryTable.icons
        var categoryName by DiaryCategoryTable.categoryName
        val items by DiaryItemDBHelper.DiaryItemEntity referrersOn DiaryItemDBHelper.DiaryItemsTable.category
    }

    fun addDiaryCategory(diaryCategoryData: DiaryMainDataClass) {
        val diaryMainDataClassString = Json.encodeToString(DiaryMainDataClass.serializer(), diaryCategoryData)
        Log.e("addDiaryCategory", "diaryMainDataClassString = $diaryMainDataClassString")
        val diaryMainDataClassJson = Json.decodeFromString(DiaryMainDataClass.serializer(), diaryMainDataClassString)
        Log.e("addDiaryCategory", "diaryMainDataClassJson = $diaryMainDataClassJson")


        transaction {
            DiaryCategoryEntity.new {
                icons = diaryMainDataClassJson.icons
                categoryName = diaryMainDataClassJson.categoryName
            }
        }

    }



    private fun defaultCategories(): List<DiaryMainDataClass> {

        var arrayList = mutableListOf<DiaryMainDataClass>()

        arrayList.add(Food.CATEGORY_FOOD)
        arrayList.add(Drink.CATEGORY_DRINK)
        arrayList.add(Exercise.CATEGORY_EXCERCISE)
        arrayList.add(Smoke.CATEGORY_SMOKE)
        arrayList.add(Feeling.CATEGORY_FEELING)
        arrayList.add(DiaryMainDataClass(6,R.drawable.ic_baseline_baby_changing_station_24, "Sleep/Get Up Time"))


        return arrayList
    }

    fun addAllDefaultCategoriesToDB() {
        val defaultList = defaultCategories()
        println("defaultList = $defaultList")
        transaction {
            for (i in defaultList.indices){
                val new = DiaryCategoryEntity.new(defaultList[i].id) {
                    icons = defaultList[i].icons
                    categoryName = defaultList[i].categoryName
                }
                println(new.id.value)

            }
        }
    }

    fun deleteAllCategoryRecords(){
        transaction {
            DiaryCategoryTable.deleteAll()
        }
    }


    fun getCategoryListFromDB():MutableList<DiaryMainDataClass> {
        return  transaction {
            val query = DiaryCategoryEntity.all()
            query.map { diaryCategoryInstance: DiaryCategoryEntity ->
                DiaryMainDataClass(
                    diaryCategoryInstance.id.value,
                    diaryCategoryInstance.icons,
                    diaryCategoryInstance.categoryName,
                )
            }.toMutableList()
        }
    }




}