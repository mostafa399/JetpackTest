package com.mostafahelal.myapplication.gym.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "gyms")
data class LocalGym(
    @PrimaryKey
    @ColumnInfo(name = "gym_id")
    val id: Int,
    val name: String,
    val des: String,
    val is_open: Boolean,
    @ColumnInfo(name = "is_favourite")
    val isFavoutite: Boolean = false)
