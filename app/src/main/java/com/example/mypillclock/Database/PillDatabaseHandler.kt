package com.example.mypillclock.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import android.util.Log.INFO
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.PillDatabaseHandler.PillDB.KEY_AMOUNT
import com.example.mypillclock.Database.PillDatabaseHandler.PillDB.KEY_AMOUNTTYPE
import com.example.mypillclock.Database.PillDatabaseHandler.PillDB.KEY_DOCTORNOTE
import com.example.mypillclock.Database.PillDatabaseHandler.PillDB.KEY_DURATION
import com.example.mypillclock.Database.PillDatabaseHandler.PillDB.KEY_FREQUENCY
import com.example.mypillclock.Database.PillDatabaseHandler.PillDB.KEY_PILL_NAME
import com.example.mypillclock.Database.PillDatabaseHandler.PillDB.KEY_REMINDTIME
import com.example.mypillclock.Database.PillDatabaseHandler.PillDB.TABLE_NAME
import kotlinx.serialization.json.Json
import java.util.logging.Level.INFO

class PillDatabaseHandler(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null,DATABASE_VERSION){



        object PillDB:BaseColumns{
            const val TABLE_NAME = "SavedPill"
            const val KEY_PILL_NAME = "name"
            const val KEY_DURATION = "duration"
            const val KEY_FREQUENCY = "frequency"
            const val KEY_AMOUNT = "amount"
            const val KEY_AMOUNTTYPE = "amountType"
            const val KEY_REMINDTIME = "remindTime"
            const val KEY_DOCTORNOTE= "doctorNote"
        }


    companion object FeedEntry:BaseColumns {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "PillInfo.db"}

    private val SQL_CREATE_ENTRIES = "CREATE TABLE $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$KEY_PILL_NAME TEXT," +
            "$KEY_DURATION TEXT," +
            "$KEY_FREQUENCY TEXT," +
            "$KEY_AMOUNT TEXT,"+
            "$KEY_AMOUNTTYPE TEXT,"+
            "$KEY_REMINDTIME TEXT,"+
            "$KEY_DOCTORNOTE TEXT)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

    override fun onCreate(db: SQLiteDatabase) {
//        creating table with fields
    db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun addPill(pill:PillInfo):Long{
        val db = this.writableDatabase

        val pillInfoString = Json.encodeToString(PillInfo.serializer(), pill)
        Log.e("insideAddPillFun", "pillInfoString = $pillInfoString")
        val pillInfoJson = Json.decodeFromString(PillInfo.serializer(), pillInfoString)
        Log.e("insideAddPillFun", "pillInfoJson = $pillInfoJson")


        val contentValues = ContentValues().apply{
            put(KEY_PILL_NAME, pillInfoJson.name)
            put(KEY_DURATION, pillInfoJson.duration)
            put(KEY_FREQUENCY, pillInfoJson.frequency)
            put(KEY_AMOUNT, pillInfoJson.amount)
            put(KEY_AMOUNTTYPE, pillInfoJson.amountType)
            put(KEY_REMINDTIME, pillInfoJson.RemindTime)
            put(KEY_DOCTORNOTE, pillInfoJson.doctorNote)
        }


// Insert the new row, returning the primary key value of the new row
//        or it will return -1 if there was an error inserting the data
        val success =db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return success
    }

    //    function to read records from database in the form of ArrayList
    fun viewPill(): MutableList<PillInfo> {
//    create empty list for output
        val pillList = mutableListOf<PillInfo>()

//    select all records from the table
        val selectQuery = "SELECT * FROM $TABLE_NAME"

        val db = this.readableDatabase

//    cursor is used to read the record one by one, add them to data class
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLException) {
            db.execSQL(selectQuery)
            return pillList
        }


        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(KEY_PILL_NAME))
                val duration = cursor.getInt(cursor.getColumnIndex(KEY_DURATION))
                val frequency = cursor.getInt(cursor.getColumnIndex(KEY_FREQUENCY))
                val amount = cursor.getInt(cursor.getColumnIndex(KEY_AMOUNT))
                val amountType = cursor.getString(cursor.getColumnIndex(KEY_AMOUNTTYPE))
                val remindTime = cursor.getString(cursor.getColumnIndex(KEY_REMINDTIME))
                val doctorNote = cursor.getString(cursor.getColumnIndex(KEY_DOCTORNOTE))

                val pill = PillInfo(
                    name,
                    duration,
                    frequency,
                    amount,
                    amountType,
                    remindTime,
                    doctorNote
                )
                pillList.add(pill)

            } while (cursor.moveToNext())
        }
        Log.e("viewPillFun", "pillList = $pillList")
        return pillList
    }


}








