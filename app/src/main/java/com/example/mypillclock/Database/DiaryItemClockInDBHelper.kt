package com.example.mypillclock.Database

import com.example.mypillclock.DataClass.DiaryItemClockInDataClass
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.DataClass.pillClockInDataClass
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import java.text.SimpleDateFormat

class DiaryItemClockInDBHelper {

    object diaryItemclockInTimeTable : IntIdTable() {
        val category: Column<String> = varchar("category", 50)
        val itemName: Column<String> = varchar("itemName", 50)
        var clockInTime: Column<Long> = long("clockInTime")


        override val primaryKey = PrimaryKey(id, name = "item clock In Time")
    }

    //    An entity instance or a row in the table is defined as a class instance:
    class DiaryItemClockInTimeEntity(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<DiaryItemClockInTimeEntity>(diaryItemclockInTimeTable)
        var category by diaryItemclockInTimeTable.category
        var itemName by diaryItemclockInTimeTable.itemName
        var clockInTime by diaryItemclockInTimeTable.clockInTime


    }

    fun addDiaryItemClockInRecord(itemClockInData: DiaryItemClockInDataClass) {

        transaction {
            DiaryItemClockInTimeEntity.new {
                category = itemClockInData.category
                itemName = itemClockInData.itemName
                clockInTime = itemClockInData.clockInTime
            }
        }

    }



    // output data type is MutableList<PillInfo>
    fun getAllDiaryItemClockInListFromDB():MutableList<DiaryItemClockInDataClass> {
        return  transaction {
            val query = DiaryItemClockInTimeEntity.all()
            query.map { itemClockInDataInstance: DiaryItemClockInTimeEntity ->
                DiaryItemClockInDataClass(
                    itemClockInDataInstance.id.value,
                    itemClockInDataInstance.category,
                    itemClockInDataInstance.itemName,
                    itemClockInDataInstance.clockInTime,
                )
            }.toMutableList()
        }
    }



    fun deleteDiaryItemClockInRecord(diaryItemClockInData: DiaryItemClockInDataClass) {

        transaction {
            diaryItemclockInTimeTable.deleteWhere {
                diaryItemclockInTimeTable.id eq diaryItemClockInData.id
            }
        }

    }


    fun updateDiaryItemClockInRecord(updatedDiaryItemClockInData: DiaryItemClockInDataClass) {

        val updatedDiaryItemClockInTimeString = Json.encodeToString(DiaryItemClockInDataClass.serializer(), updatedDiaryItemClockInData)
        val updatedDiaryItemClockInTimeJson = Json.decodeFromString(DiaryItemClockInDataClass.serializer(), updatedDiaryItemClockInTimeString)
        val DiaryItemclockInRecordTobeUpdated = queryOneDiaryItem(updatedDiaryItemClockInData.id) ?: error("The id does not exists")

        transaction {
            DiaryItemclockInRecordTobeUpdated.category = updatedDiaryItemClockInTimeJson.category
            DiaryItemclockInRecordTobeUpdated.itemName = updatedDiaryItemClockInTimeJson.itemName
            DiaryItemclockInRecordTobeUpdated.clockInTime = updatedDiaryItemClockInTimeJson.clockInTime

        }
    }

    private fun queryOneDiaryItem(id: Int): DiaryItemClockInTimeEntity? {
        return transaction {
            DiaryItemClockInTimeEntity.findById(id)
        }
    }

    fun findRecordsByDate(dateToMatch:String): MutableList<DiaryItemClockInDataClass> {
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