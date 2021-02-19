//package com.example.mypillclock.Database
//
//import android.util.Log
//import kotlinx.serialization.json.Json
//import org.jetbrains.exposed.dao.IntEntity
//import org.jetbrains.exposed.dao.IntEntityClass
//import org.jetbrains.exposed.dao.id.EntityID
//import org.jetbrains.exposed.dao.id.IntIdTable
//import org.jetbrains.exposed.sql.Column
//import org.jetbrains.exposed.sql.transactions.transaction
//
//class reminderDBHelper {
//
//    object reminderDataTable : IntIdTable() {
//        val name: Column<String> = varchar("name", 50)
//        var days: Column<String> = varchar("days",30)
//        val hour: Column<Int> = integer("hour")
//        val minute: Column<Int> = integer("minute")
//
//
//        override val primaryKey = PrimaryKey(id, name = "PillSaved")
//    }
//
//    //    An entity instance or a row in the table is defined as a class instance:
//    class reminderDataEntity(id: EntityID<Int>) : IntEntity(id) {
//        companion object : IntEntityClass<reminderDataEntity>(reminderDataTable)
//        var name by reminderDataTable.name
//        var days by reminderDataTable.days
//        var hour by reminderDataTable.hour
//        var minute by reminderDataTable.minute
//    }
//
//
//    fun addReminder(reminderInfo: clockInInfo) {
//
//        val reminderInfoString = Json.encodeToString(clockInInfo.serializer(), reminderInfo)
//        Log.e("insideReminderInfoStr", "reminderInfoString = $reminderInfoString")
//        val reminderInfoJson = Json.decodeFromString(clockInInfo.serializer(), reminderInfoString)
//        Log.e("insideReminderInfoJson", "reminderInfoJson = $reminderInfoJson")
//
//
//        transaction {
//            reminderDataEntity.new {
//                name = reminderInfoJson.pillName
//                days = reminderInfoJson.days
//                hour = reminderInfoJson.hour
//                minute = reminderInfoJson.minute
//            }
//        }
//
//
//
//    }
//
//
//
//
//
//
//
//}