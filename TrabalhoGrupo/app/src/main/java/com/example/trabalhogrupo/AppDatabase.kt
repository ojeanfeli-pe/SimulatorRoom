package com.example.trabalhogrupo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ItemCompra::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemCompraDao(): ItemCompraDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val tempInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "item_compra_database"
                ).build()
                INSTANCE = tempInstance
                tempInstance
            }
        }
    }
}