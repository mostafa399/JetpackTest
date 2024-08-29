package com.mostafahelal.myapplication.gym.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
data class Gym(

    val id: Int,
    val name: String,
    val des: String,
    val is_open: Boolean,
    val isFavoutite: Boolean = false)
