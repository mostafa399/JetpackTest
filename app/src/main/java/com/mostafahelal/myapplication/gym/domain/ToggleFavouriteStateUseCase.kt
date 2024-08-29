package com.mostafahelal.myapplication.gym.domain

import com.mostafahelal.myapplication.gym.data.GymsRepository
import javax.inject.Inject

class ToggleFavouriteStateUseCase @Inject constructor(
    private val repo: GymsRepository,
    private val getSortedGymsUsecase:GetSortedGymsUsecase
){

    suspend operator fun invoke(id:Int,oldValue:Boolean):List<Gym>{

        repo.toggleFavouriteGym(gymId = id, state = oldValue.not())
        return getSortedGymsUsecase()
    }
}