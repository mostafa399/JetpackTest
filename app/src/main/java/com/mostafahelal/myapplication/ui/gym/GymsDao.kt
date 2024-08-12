package com.mostafahelal.myapplication.ui.gym

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GymsDao {
    @Query("select * from gyms ")
    suspend fun getGyms():List<Gym>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGyms(gyms:List<Gym>)
    @Update(entity = Gym::class)
    suspend fun update(gymsFavouriteState:GymsFavouriteState)
    @Update(entity = Gym::class)
    suspend fun updateAll(gymsFavouriteState:List<GymsFavouriteState>)
    @Query("select * from gyms where is_favourite = 1")
    suspend fun getFavouriteGyms():List<Gym>
}