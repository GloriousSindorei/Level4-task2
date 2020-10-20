package com.example.madlevel4task2

import android.content.Context

class RPSRepository (context: Context) {

    private val rpsDao: RPSDao

    init {
        val database = RPSDatabase.getDatabase(context)
        rpsDao = database!!.rpsDao()
    }

    suspend fun getAllProducts(): List<RPS> = rpsDao.getAllProducts()

    suspend fun insertProduct(product: RPS) = rpsDao.insertProduct(product)

    suspend fun deleteProduct(product: RPS) = rpsDao.deleteProduct(product)

    suspend fun deleteAllProducts() = rpsDao.deleteAllProducts()
}