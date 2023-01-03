package com.example.challengechapter6.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.challengechapter6.dao.player.PlayerDao
import com.example.challengechapter6.dao.player.PlayerEntity
import com.example.challengechapter6.dao.suit.SuitDao
import com.example.challengechapter6.dao.suit.SuitEntity

@Database(entities = [PlayerEntity::class, SuitEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun playerDao(): PlayerDao
    abstract fun suitDao(): SuitDao

    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if(INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "Player.db").build()
                }
            }

            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}