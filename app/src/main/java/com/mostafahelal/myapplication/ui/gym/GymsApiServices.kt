package com.mostafahelal.myapplication.ui.gym

import retrofit2.http.GET
import retrofit2.http.Query

interface GymsApiServices {
    @GET("gyms.json")
    suspend fun getGyms():List<Gym>
    @GET("gyms.json?orderBy=\"id\"")
    suspend fun getGym(@Query("equalTo")id:Int):Map<String, Gym>
}