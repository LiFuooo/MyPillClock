package com.example.mypillclock.Reminder

import java.util.*

class Presenter (
    private val view: ReminderContracts.View,
    private val model: Model
    ): ReminderContracts.Presenter{

    override fun start(){
        if(model.getReminderData() != null){
            view.displayExistingReminder(model.getReminderData()!!)
        }
    }

    override fun timeTapped() {
        val reminderData = model.getReminderData()
        if (reminderData != null){
            view.displayTimeDialog(reminderData.hour, reminderData.minute)
        } else{
            val date = Calendar.getInstance()
            val hour = date.get(Calendar.HOUR_OF_DAY)
            val minute = date.get(Calendar.MINUTE)
            view.displayTimeDialog(hour, minute)
        }
    }

    override fun timeSelected(hourOfDay:Int, minute:Int){
        model.setHourAndMinute(hourOfDay, minute)
    }

    override fun saveTapped(name: String, hour: Int, days:Array<String?>){

        if(name.trim().isEmpty()){
            view.displayError(ReminderDialog.ERROR_NO_NAME)
            return
        }

        if(model.getIsEditing()){
            val affectedRows = model.updateReminder(name, days, hour, minute)
            if (affectedRows > 0){
                view.close(ReminderDialog.REMINDER_UPDATED, model.getReminderData()!!)
            }
        }

    }

    override fun deleteTapped(){
        val reminderData = model.getReminderData()?:return
        val rowsAffected = model.deleteReminder(reminderData.id)
        if(rowsAffedted > 0){
            view.close(ReminderDialog.REMINDER_DELETED, reminderData)
        } else {
            view.displayError(ReminderDialog.ERROR_DELETE_FAILED)
        }
    }

    override fun administeredTapped() {
        if(!model.getIsEditing()){
            view.displayAdministerError()
        }
    }

    fun validDays(days:Array<String?>):Boolean{
        for(day in days){
            if (day != null){
                return true
            }
        }
        return false
    }


    }

