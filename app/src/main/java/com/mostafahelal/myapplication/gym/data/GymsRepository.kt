package com.mostafahelal.myapplication.gym.data

import com.mostafahelal.myapplication.gym.data.di.IoDispatcher
import com.mostafahelal.myapplication.gym.data.local.GymsDao
import com.mostafahelal.myapplication.gym.data.local.GymsFavouriteState
import com.mostafahelal.myapplication.gym.data.local.LocalGym
import com.mostafahelal.myapplication.gym.data.remote.GymsApiServices
import com.mostafahelal.myapplication.gym.domain.Gym
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymsRepository @Inject constructor(
    private val apiServices: GymsApiServices,
    private val gymsDao: GymsDao,
    @IoDispatcher private val dispacher: CoroutineDispatcher

) {

    suspend fun loadGyms() =
        withContext(dispacher) {
            try {
                updateLocalDb()
            } catch (e: Exception) {
                if (gymsDao.getGyms().isEmpty()) {
                    throw Exception("something Went wrong , no data was found , try connecting to Internet")
                }

            }
        }

    suspend fun getGyms(): List<Gym> {
        return withContext(dispacher) {
            return@withContext gymsDao.getGyms().map {
                Gym(
                    it.id,
                    it.name,
                    it.des,
                    it.is_open,
                    it.isFavoutite
                )
            }
        }
    }

    suspend fun toggleFavouriteGym(gymId: Int, state: Boolean) =
        withContext(dispacher) {
            gymsDao.update(
                GymsFavouriteState(id = gymId, isFavoutite = state)
            )
            return@withContext gymsDao.getGyms()
        }

    private suspend fun updateLocalDb() {
        val gyms = apiServices.getGyms()
        val favouriteGymsList = gymsDao.getFavouriteGyms()
        gymsDao.addGyms(gyms.map {
            LocalGym(
                it.id,
                it.name,
                it.des,
                it.is_open,
            )
        })
        gymsDao.updateAll(favouriteGymsList.map {
            GymsFavouriteState(
                id = it.id,
                isFavoutite = true
            )
        })
    }
}