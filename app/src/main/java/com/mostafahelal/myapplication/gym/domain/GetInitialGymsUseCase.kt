package com.mostafahelal.myapplication.gym.domain

import com.mostafahelal.myapplication.gym.data.GymsRepository
import javax.inject.Inject

class GetInitialGymsUseCase @Inject constructor(
    private val repo: GymsRepository ,
    private val getSortedGymsUsecase: GetSortedGymsUsecase) {
    suspend operator fun invoke():List<Gym>{
        repo.loadGyms()
        return getSortedGymsUsecase()
    }
}