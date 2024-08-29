package com.mostafahelal.myapplication.gym.presentation.gymsList

import com.mostafahelal.myapplication.gym.domain.Gym

data class GymsScreenState(
    val gym: List<Gym>,
    val isLoading: Boolean,
    val error:String?=null
)
