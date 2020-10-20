package com.example.madlevel4task2

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "rps_table")
data class RPS (
    @ColumnInfo(name = "Player choice")
    @DrawableRes val playerChoice: Int,
    @ColumnInfo(name = "Computer choice")
    @DrawableRes val computerChoice: Int,
    @ColumnInfo(name = "Winner")
    val result: Result?,
    @ColumnInfo(name = "Date")
    val date: Date,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
) {
    enum class Result(val value: String) {
        WIN("win"),
        DRAW("draw"),
        LOSS("loss")
    }
    enum class Choice {
        ROCK,
        PAPER,
        SCISSORS
    }
}