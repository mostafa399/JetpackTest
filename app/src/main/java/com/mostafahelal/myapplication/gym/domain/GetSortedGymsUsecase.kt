package com.mostafahelal.myapplication.gym.domain

import com.mostafahelal.myapplication.gym.data.GymsRepository
import javax.inject.Inject

class GetSortedGymsUsecase @Inject constructor(
    private val repo:GymsRepository
){

    suspend operator fun invoke():List<Gym>{
        return repo.getGyms().sortedBy { it.name }
    }
}