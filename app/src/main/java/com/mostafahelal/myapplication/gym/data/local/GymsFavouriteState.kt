package com.mostafahelal.myapplication.gym.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class GymsFavouriteState(
    @ColumnInfo(name = "gym_id")
    val id: Int,
    @ColumnInfo(name = "is_favourite")
    val isFavoutite: Boolean = false
)
