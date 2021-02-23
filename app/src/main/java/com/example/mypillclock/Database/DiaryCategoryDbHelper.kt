package com.example.mypillclock.Database

import android.util.Log
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.DataClass.diaryMainDataClass
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


        override val primaryKey = PrimaryKey(id, name = "Diary Category")
    }

    //    An entity instance or a row in the table is defined as a class instance:
    class DiaryCategoryEntity(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<DiaryCategoryEntity>(DiaryCategoryTable)
        var icons by DiaryCategoryTable.icons
        var categoryName by DiaryCategoryTable.categoryName
    }

    fun addDiaryCategory(diaryCategoryData: diaryMainDataClass) {
        val diaryMainDataClassString = Json.encodeToString(diaryMainDataClass.serializer(), diaryCategoryData)
        Log.e("addDiaryCategory", "diaryMainDataClassString = $diaryMainDataClassString")
        val diaryMainDataClassJson = Json.decodeFromString(diaryMainDataClass.serializer(), diaryMainDataClassString)
        Log.e("addDiaryCategory", "diaryMainDataClassJson = $diaryMainDataClassJson")


        transaction {
            DiaryCategoryEntity.new {
                icons = diaryMainDataClassJson.icons
                categoryName = diaryMainDataClassJson.categoryName
            }
        }

    }



    fun defaultCategories(): ArrayList<diaryMainDataClass> {

        var arrayList: ArrayList<diaryMainDataClass> = ArrayList()

        arrayList.add(diaryMainDataClass(R.drawable.ic_baseline_fastfood_24, "Food"))
        arrayList.add(diaryMainDataClass(R.drawable.ic_baseline_wine_bar_24, "Drink"))
        arrayList.add(diaryMainDataClass(R.drawable.ic_baseline_snowboarding_24, "Exercise"))
        arrayList.add(diaryMainDataClass(R.drawable.ic_baseline_emoji_emotions_24, "Feeling"))
        arrayList.add(diaryMainDataClass(R.drawable.ic_baseline_baby_changing_station_24, "Sleep/Get Up Time"))
        arrayList.add(diaryMainDataClass(R.drawable.ic_baseline_smoking_rooms_24, "Smoking"))

        return arrayList
    }

    fun addDefaultCategoriesToDb() {
        val defaultList = defaultCategories()
        for (i in 0 until defaultList.size){
            transaction {
                DiaryCategoryEntity.new {
                    icons = defaultList[i].icons
                    categoryName = defaultList[i].categoryName
                }
            }
        }
    }

    fun deleteAllCategoryRecords(){
        transaction {
            DiaryCategoryTable.deleteAll()
        }
    }


    fun getCategoryListFromDB():MutableList<diaryMainDataClass> {
        return  transaction {
            val query = DiaryCategoryEntity.all()
            query.map { diaryCategoryInstance: DiaryCategoryEntity ->
                diaryMainDataClass(
                    diaryCategoryInstance.icons,
                    diaryCategoryInstance.categoryName,
                )
            }.toMutableList()
        }
    }




}