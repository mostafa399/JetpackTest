package com.mostafahelal.myapplication.ui.gym

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "gyms")
data class Gym(
    @PrimaryKey
    @ColumnInfo(name = "gym_id")
    val id: Int,
    @SerializedName("gym_name")
    val name: String,
    @SerializedName("gym_location")
    val des: String,
    @SerializedName("is_open")
    val is_open: Boolean,
    @ColumnInfo(name = "is_favourite")
    val isFavoutite: Boolean = false)
