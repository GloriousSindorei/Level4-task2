package com.example.madlevel4task2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RPS::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RPSDatabase : RoomDatabase() {

    abstract fun rpsDao(): RPSDao

    companion object{
        private const val DATABASE_NAME = "ROCK_PAPER_SCISSORS_DATABASE"

        @Volatile
        private var rpsDatabaseInstance: RPSDatabase? = null

        fun getDatabase(context: Context): RPSDatabase?{
            if(rpsDatabaseInstance == null){
                synchronized(RPSDatabase::class.java){
                    if(rpsDatabaseInstance == null) {
                        rpsDatabaseInstance =
                            Room.databaseBuilder(
                                context.applicationContext,
                                RPSDatabase::class.java,
                                DATABASE_NAME
                            ).build()
                    }
                }
            }
            return rpsDatabaseInstance
        }
    }
}