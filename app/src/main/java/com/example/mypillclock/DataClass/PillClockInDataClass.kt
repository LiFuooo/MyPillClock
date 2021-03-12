package com.example.mypillclock.DataClass
import com.example.mypillclock.Database.PillClockInDBHelper
import org.jetbrains.exposed.sql.Column
import java.io.Serializable


@kotlinx.serialization.Serializable
data class PillClockInDataClass(
    val id:Int,
    val pillName:PillInfo,
    val category:String = "Medicine",
    var timeClockIn:Long,
):Serializable

