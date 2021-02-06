//package com.example.mypillclock.Reminder
//
//import com.example.mypillclock.DataClass.RemindDateTimeDataClass
//import org.jetbrains.exposed.sql.transactions.transaction
//
//class Model(
//    private val reminderDbHelper: ReminderDBHelper,
//    var reminderData: RemindDateTimeDataClass){
//
//    var editing = reminderData != null
//
//    fun getReminderData():RemindDateTimeDataClass?{
//        return reminderData
//    }
//
//    fun getIsEditing():Boolean{
//        return editing
//    }
//
//    fun setHourAndMinute(hourOfDay:Int, minute:Int){
//        if(reminderData == null){
//            reminderData = RemindDateTimeDataClass()
//        }
//        reminderData?.hour = hourOfDay
//        reminderData?.minute = minute
//    }
//
//    fun createReminder(name:String, days:Array<String>):Long{
//        reminderData?.name = name
//
//        transaction {
////            insert reminder into table
//        }
//        return rowId
//    }
//
//
//    fun updateReminder(name:String, days:Array<String>):Int{
//        reminderData?.name = name
//        transaction {
////            update this info
//        }
//
//        return success
//    }
//
//    fun deleteReminder(id:Int):Int{
//        transaction {
////            detele by ID
//        }
//
//        return success
//    }
//
//
//
//
//
//}