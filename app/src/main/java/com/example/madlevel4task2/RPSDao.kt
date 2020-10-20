package com.example.madlevel4task2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RPSDao {
    @Query("SELECT * FROM RPS_TABLE")
    suspend fun getAllProducts(): List<RPS>

    @Insert
    suspend fun insertProduct(rps: RPS)

    @Delete
    suspend fun deleteProduct(rps: RPS)

    @Query("DELETE FROM rps_table")
    suspend fun deleteAllProducts()
}