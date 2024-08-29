package com.mostafahelal.myapplication.gym.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(
    entities = [LocalGym::class],
    version = 2,
    exportSchema = false

)
abstract class GymsDatabase : RoomDatabase() {
    abstract val dao: GymsDao


//    companion object {
//        @Volatile
//        private var daoInstance: GymsDao?=null
//
//        private fun buildDatabase(context: Context): GymsDatabase
//        {
//            return Room.databaseBuilder(
//                context = context.applicationContext,
//                GymsDatabase::class.java,
//                "gyms_database"
//            ).fallbackToDestructiveMigration()
//                .build()
//        }
//        @OptIn(InternalCoroutinesApi::class)
//        fun getDatabaseInstance(context: Context): GymsDao {
//            synchronized(this){
//                if (daoInstance ==null){
//                    daoInstance = buildDatabase(context ).dao
//                }
//                return daoInstance as GymsDao
//            }
//
//
//        }
//    }
}