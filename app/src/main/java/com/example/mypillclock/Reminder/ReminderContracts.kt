//package com.example.mypillclock.Reminder
//
//import com.example.mypillclock.DataClass.clockInInfo
//
//interface ReminderContracts {
//    interface View{
//        fun displayExistingReminder(reminderData:clockInInfo)
//        fun displayTimeDialog(hour:Int, minute:Int)
//        fun displayError(errorCode:Int)
//        fun displayAdministerError()
//        fun close(opCode:Int, reminderData:clockInInfo)
//    }
//
//    interface Presenter{
//        fun start()
//        fun timeTapped()
//        fun timeSelected(hourOfDay: Int, minute:Int)
//        fun saveTapped(name:String, pillName:String)
//        fun deleteTapped()
//        fun administeredTapped()
//    }
//
//}