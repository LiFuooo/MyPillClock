package com.example.mypillclock.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import com.example.mypillclock.DataClass.PillInfo
import com.example.mypillclock.Database.PillDatabaseHandler.PillDB.KEY_AMOUNT
import com.example.mypillclock.Database.PillDatabaseHandler.PillDB.KEY_AMOUNTTYPE
import com.example.mypillclock.Database.PillDatabaseHandler.PillDB.KEY_DOCTORNOTE
import com.example.mypillclock.Database.PillDatabaseHandler.PillDB.KEY_DURATION
import com.example.mypillclock.Database.PillDatabaseHandler.PillDB.KEY_FREQUENCY
import com.example.mypillclock.Database.PillDatabaseHandler.PillDB.KEY_ID
import com.example.mypillclock.Database.PillDatabaseHandler.PillDB.KEY_PILL_NAME
import com.example.mypillclock.Database.PillDatabaseHandler.PillDB.KEY_REMINDTIME
import com.example.mypillclock.Database.PillDatabaseHandler.PillDB.TABLE_NAME
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PillDatabaseHandler(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null,DATABASE_VERSION){

//https://www.baeldung.com/kotlin-exposed-persistence


        object PillDB:BaseColumns{
            const val KEY_ID = "_id"
            const val TABLE_NAME = "SavedPill"
            const val KEY_PILL_NAME = "name"
            const val KEY_DURATION = "duration"
            const val KEY_FREQUENCY = "frequency"
            const val KEY_AMOUNT = "amount"
            const val KEY_AMOUNTTYPE = "amountType"
            const val KEY_REMINDTIME = "remindTime"
            const val KEY_DOCTORNOTE= "doctorNote"
        }

//    declare a table that contain an Int id with the name "id"
        object DBExposedPillsTable : IntIdTable() {
            val sequelId: Column<Int> = integer("sequel_id").uniqueIndex()
            val name: Column<String> = varchar("name", 50)
            var duration: Column<Int> = integer("duration")
            val frequency: Column<Int> = integer("frequency")
            val amount: Column<Int> = integer("amount")
            val amountType: Column<String> = varchar("amountType", 50)
            val remindTime: Column<String> = varchar("remindTime", 50)
            val doctorNote: Column<String> = varchar("doctorNote", 200)

            override val primaryKey = PrimaryKey(id, name = "PillSaved")
        }

//    An entity instance or a row in the table is defined as a class instance:
    class DBExposedPillInstance(id: EntityID<Int>):IntEntity(id){
        companion object: IntEntityClass<DBExposedPillInstance>(DBExposedPillsTable)
        var name by DBExposedPillsTable.name
        var duration by DBExposedPillsTable.duration
        var frequency by DBExposedPillsTable.frequency
        var amount by DBExposedPillsTable.amount
        var amountType by DBExposedPillsTable.amountType
        var remindTime by DBExposedPillsTable.remindTime
        var doctorNote by DBExposedPillsTable.doctorNote

    }


    companion object FeedEntry:BaseColumns {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "PillInfo.db"}

    private val SQL_CREATE_ENTRIES = "CREATE TABLE $TABLE_NAME (" +
            "$KEY_ID INTEGER PRIMARY KEY," +
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

        val ddlStatements: List<String> = SchemaUtils.createStatements(DBExposedPillsTable)
//        SchemaUtils.

        Database.connect ("jdbc:sqlite:/data/data.db", "org.sqlite.JDBC")
        transaction{
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(DBExposedPillsTable)
//            commit()
        }
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



//        val pillContent = ContentValues().apply{
//            put(KEY_PILL_NAME, pillInfoJson.name)
//            put(KEY_DURATION, pillInfoJson.duration)
//            put(KEY_FREQUENCY, pillInfoJson.frequency)
//            put(KEY_AMOUNT, pillInfoJson.amount)
//            put(KEY_AMOUNTTYPE, pillInfoJson.amountType)
//            put(KEY_REMINDTIME, pillInfoJson.RemindTime)
//            put(KEY_DOCTORNOTE, pillInfoJson.doctorNote)
//        }



//        Database.connect("jdbc:sqlite:/data/data.db", "org.sqlite.JDBC")
        transaction {
            addLogger(StdOutSqlLogger)
            val  aNewPill = DBExposedPillInstance.new{
                name = pillInfoJson.name
                duration = pillInfoJson.duration
                frequency = pillInfoJson.frequency
                amount = pillInfoJson.amount
                amountType = pillInfoJson.amountType
                remindTime = pillInfoJson.RemindTime
                doctorNote = pillInfoJson.doctorNote
            }

//            val success =db.insert(TABLE_NAME, null, pillContentExposed)
//            db.close()
//            return success
        }


// Insert the new row, returning the primary key value of the new row
//        or it will return -1 if there was an error inserting the data
//        val success =db.insert(TABLE_NAME, null, pillContent)

//        db.close()
//        return success
//    }

    //    function to read records from database in the form of List
    fun getPillListFromDB(): MutableList<PillInfo> {
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
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val name = cursor.getString(cursor.getColumnIndex(KEY_PILL_NAME))
                val duration = cursor.getInt(cursor.getColumnIndex(KEY_DURATION))
                val frequency = cursor.getInt(cursor.getColumnIndex(KEY_FREQUENCY))
                val amount = cursor.getInt(cursor.getColumnIndex(KEY_AMOUNT))
                val amountType = cursor.getString(cursor.getColumnIndex(KEY_AMOUNTTYPE))
                val remindTime = cursor.getString(cursor.getColumnIndex(KEY_REMINDTIME))
                val doctorNote = cursor.getString(cursor.getColumnIndex(KEY_DOCTORNOTE))

                val pill = PillInfo(
                        id,
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

    fun deletePill(pillInfo: PillInfo){
        val db = this.writableDatabase
        transaction {
            addLogger(StdOutSqlLogger)
            DBExposedPillsTable.deleteWhere {
                DBExposedPillsTable.id eq pillInfo.id
            }
            }

        }

//        val success = db.delete(TABLE_NAME, KEY_ID + "=" + pillInfo.id, null)
//        db.close()
//        return success
//    }

    fun updatePill(pill: PillInfo):Int{
        val db = this.writableDatabase

        val pillInfoString = Json.encodeToString(PillInfo.serializer(), pill)
        val pillInfoJson = Json.decodeFromString(PillInfo.serializer(), pillInfoString)
        val PillToBeUpdated =  getPillListFromDB(pill)

        transaction {

            PillToBeUpdated.name = pillInfoJson.name
                duration = pillInfoJson.duration
                frequency = pillInfoJson.frequency
                amount = pillInfoJson.amount
                amountType = pillInfoJson.amountType
                remindTime = pillInfoJson.RemindTime
                doctorNote = pillInfoJson.doctorNote



        val contentValues = ContentValues().apply{
            put(KEY_PILL_NAME, pillInfoJson.name)
            put(KEY_DURATION, pillInfoJson.duration)
            put(KEY_FREQUENCY, pillInfoJson.frequency)
            put(KEY_AMOUNT, pillInfoJson.amount)
            put(KEY_AMOUNTTYPE, pillInfoJson.amountType)
            put(KEY_REMINDTIME, pillInfoJson.RemindTime)
            put(KEY_DOCTORNOTE, pillInfoJson.doctorNote)
        }


        val success =db.update(TABLE_NAME,
                contentValues,
                KEY_ID + "=" + pill.id,
                null)

        db.close()
        return success
    }

        fun queryOnePill(id: Int):DBExposedPillInstance?{
//            problem: check id is valid
            return  transaction {
                addLogger(StdOutSqlLogger)
                DBExposedPillInstance.findById(id)
            }
        }

}








