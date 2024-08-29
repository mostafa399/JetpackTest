package com.mostafahelal.myapplication.gym.data.remote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class RemoteGym(
    @SerializedName("id")
    val id: Int,
    @SerializedName("gym_name")
    val name: String,
    @SerializedName("gym_location")
    val des: String,
    @SerializedName("is_open")
    val is_open: Boolean,
    )
