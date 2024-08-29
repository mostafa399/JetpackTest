package com.mostafahelal.myapplication.gym.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GymsDao {
    @Query("select * from gyms ")
    suspend fun getGyms():List<LocalGym>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGyms(gyms:List<LocalGym>)
    @Update(entity = LocalGym::class)
    suspend fun update(gymsFavouriteState: GymsFavouriteState)
    @Update(entity = LocalGym::class)
    suspend fun updateAll(gymsFavouriteState:List<GymsFavouriteState>)
    @Query("select * from gyms where is_favourite = 1")
    suspend fun getFavouriteGyms():List<LocalGym>
}