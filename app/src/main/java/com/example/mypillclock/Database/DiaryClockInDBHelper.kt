package com.example.mypillclock.Database

import android.util.Log
import com.example.mypillclock.DataClass.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import java.text.SimpleDateFormat

class DiaryClockInDBHelper {

    object diaryItemclockInTimeTable : IntIdTable() {
        val item = reference("item", DiaryItemDBHelper.DiaryItemsTable)
        var clockInTime: Column<Long> = long("clockInTime")
    }

    //    An entity instance or a row in the table is defined as a class instance:
    class DiaryItemClockInTimeEntity(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<DiaryItemClockInTimeEntity>(diaryItemclockInTimeTable)
        var item by DiaryItemDBHelper.DiaryItemEntity referencedOn DiaryClockInDBHelper.diaryItemclockInTimeTable.item
        var clockInTime by diaryItemclockInTimeTable.clockInTime


    }

    fun addDiaryItemClockInRecordToDB(itemClockInData: DiaryClockInDataClass) {

        transaction {
            Log.i("addDiaryItemClockInRec", itemClockInData.toString())
            Log.i("addDiaryItemClockIn id", itemClockInData.item.id.toString())
            val itemEntity = DiaryItemDBHelper.DiaryItemEntity[itemClockInData.item.id]
            DiaryItemClockInTimeEntity.new {
                item = itemEntity
                clockInTime = itemClockInData.clockInTime
            }
        }

    }

    fun addDiaryItemClockInRecordToDB1(itemClockInData: DiaryClockInDataClass) {

        val itemClockInString = Json.encodeToString(DiaryClockInDataClass.serializer(), itemClockInData)
        Log.e("itemClockInString", "itemClockInString = $itemClockInString")
        val itemClockInJson = Json.decodeFromString(DiaryClockInDataClass.serializer(), itemClockInString)
        Log.e("insideAddPillFun", "pillInfoJson = $itemClockInJson")

        transaction {
            Log.i("addDiaryItemClockInRec", itemClockInData.toString())
            Log.i("addDiaryItemClockIn id", itemClockInData.item.id.toString())

            val itemEntity = DiaryItemDBHelper.DiaryItemEntity[itemClockInData.item.id]
            DiaryItemClockInTimeEntity.new {
                item = itemEntity
                clockInTime = itemClockInJson.clockInTime
            }
        }

    }



    // output data type is MutableList<PillInfo>
    fun getAllDiaryItemClockInListFromDB():MutableList<DiaryClockInDataClass> {
        return  transaction {
            val query = DiaryItemClockInTimeEntity.all()
            query.map { itemClockInDataInstance: DiaryItemClockInTimeEntity ->
                DiaryClockInDataClass(
                    itemClockInDataInstance.id.value,
                    diaryItemEntityToDataClass(itemClockInDataInstance.item),
                    itemClockInDataInstance.clockInTime,
                )
            }.toMutableList()
        }
    }

    private fun diaryItemEntityToDataClass(diaryItemEntity: DiaryItemDBHelper.DiaryItemEntity): DiaryItemDataClass {
        return DiaryItemDataClass(
            diaryItemEntity.id.value,
            diaryCategoryEntityToDataClass(diaryItemEntity.category),
            diaryItemEntity.itemName,
            diaryItemEntity.itemIcon)
    }


    private fun diaryCategoryEntityToDataClass(diaryCategoryEntity: DiaryCategoryDbHelper.DiaryCategoryEntity): DiaryMainDataClass {
        return DiaryMainDataClass(
            diaryCategoryEntity.id.value,
            diaryCategoryEntity.icons,
            diaryCategoryEntity.categoryName)
    }



    fun deleteDiaryItemClockInRecord(diaryItemClockInData: DiaryClockInDataClass) {

        transaction {
            diaryItemclockInTimeTable.deleteWhere {
                diaryItemclockInTimeTable.id eq diaryItemClockInData.id
            }
        }

    }


// this function is not correct
//    fun updateDiaryItemClockInRecord(updatedDiaryItemClockInData: DiaryClockInDataClass) {
//
//        val updatedDiaryItemClockInTimeString = Json.encodeToString(DiaryClockInDataClass.serializer(), updatedDiaryItemClockInData)
//        val updatedDiaryItemClockInTimeJson = Json.decodeFromString(DiaryClockInDataClass.serializer(), updatedDiaryItemClockInTimeString)
//        val DiaryItemclockInRecordTobeUpdated = queryOneDiaryItem(updatedDiaryItemClockInData.id) ?: error("The id does not exists")
//
//        transaction {
//            val itemEntity = DiaryItemDBHelper.DiaryItemEntity[updatedDiaryItemClockInData.item.id]
//            DiaryItemClockInTimeEntity.new {
//                item = itemEntity
//                clockInTime = updatedDiaryItemClockInData.clockInTime
//            }
//        }
//    }

    private fun queryOneDiaryClockInRecord(id: Int): DiaryItemClockInTimeEntity? {
        return transaction {
            DiaryItemClockInTimeEntity.findById(id)
        }
    }

    fun findDiaryClockInRecordByDate(dateToMatch:String): MutableList<DiaryClockInDataClass> {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val allRecords = getAllDiaryItemClockInListFromDB()

        return allRecords.filter {
            sdf.format(it.clockInTime) == dateToMatch
        }.toMutableList()
    }


    fun recordsCount(): Int {
        return getAllDiaryItemClockInListFromDB().size
    }

}