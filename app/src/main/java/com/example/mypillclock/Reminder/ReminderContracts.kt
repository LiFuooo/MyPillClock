package com.example.mypillclock.Reminder

import com.example.mypillclock.DataClass.RemindDateTimeDataClass

interface ReminderContracts {
    interface View{
        fun displayExistingReminder(reminderData:RemindDateTimeDataClass)
        fun displayTimeDialog(hour:Int, minute:Int)
        fun displayError(errorCode:Int)
        fun displayAdministerError()
        fun close(opCode:Int, reminderData:RemindDateTimeDataClass)
    }

    interface Presenter{
        fun start()
        fun timeTapped()
        fun timeSelected(hourOfDay: Int, minute:Int)
        fun saveTapped(name:String, pillName:String)
        fun deleteTapped()
        fun administeredTapped()
    }

}