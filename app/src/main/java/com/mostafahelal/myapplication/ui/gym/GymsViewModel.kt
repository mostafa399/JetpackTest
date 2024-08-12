package com.mostafahelal.myapplication.ui.gym

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafahelal.myapplication.GymsApplication
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class GymsViewModel : ViewModel() {
    var gymList by mutableStateOf(emptyList<Gym>())
    private val gymsApiServices: GymsApiServices
    private val gymsDao =
        GymsDatabase.getDatabaseInstance(context = GymsApplication.getGymsApplication())
    val errorHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(
                "https://cairo-gyms-15eb1-default-rtdb.firebaseio.com/"
            ).build()
        gymsApiServices = retrofit.create(GymsApiServices::class.java)
        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch(errorHandler) {
            val gymsList = getAllGyms()
//            gymList = gymsList.restoreSelectedGyms()
            gymList = getAllGyms()

        }

    }

    private suspend fun getAllGyms() =
        withContext(Dispatchers.IO) {
            try {
                updateLocalDb()
            } catch (e: Exception) {
                if (gymsDao.getGyms().isEmpty()) {
                    throw Exception("something Went wrong , no data was found , try connecting to Internet")
                }

            }
            gymsDao.getGyms()
        }

    private suspend fun updateLocalDb() {
        val gyms = gymsApiServices.getGyms()
        val favouriteGymsList=gymsDao.getFavouriteGyms()
        gymsDao.addGyms(gyms)
        gymsDao.updateAll(favouriteGymsList.map{GymsFavouriteState(id = it.id,isFavoutite = true)})
    }

    fun toggleFavouriteIcon(gymId: Int) {
        val gyms = gymList.toMutableList()
        val selectedIndex = gyms.indexOfFirst { it.id == gymId }
//        gyms[selectedIndex] =
//            gyms[selectedIndex].copy(isFavoutite = !gyms[selectedIndex].isFavoutite)
//        storeSelectedGym(gyms[selectedIndex])
////        gymList = gyms
        viewModelScope.launch {
            val updatedGymsList = toggleFavouriteGym(
                gymId = gymId,
                newFavouriteState = !gyms[selectedIndex].isFavoutite
            )
            gymList = updatedGymsList
        }
    }

  private suspend fun toggleFavouriteGym(gymId: Int, newFavouriteState: Boolean) =withContext(Dispatchers.IO) {
            gymsDao.update(
                GymsFavouriteState(id = gymId, isFavoutite = newFavouriteState)
            )
            return@withContext gymsDao.getGyms()
        }



//    fun storeSelectedGym(gym: Gym) {
//        val savedHandleList = stateHandle.get<List<Int>?>(FAV_IDS).orEmpty().toMutableList()
//        if (gym.isFavoutite) savedHandleList.add(gym.id) else savedHandleList.remove(gym.id)
//        stateHandle[FAV_IDS] = savedHandleList
//
//    }
//
//    fun List<Gym>.restoreSelectedGyms(): List<Gym> {
//
//        stateHandle.get<List<Int>>(FAV_IDS).let { savedIds ->
//            val gymsMap = this.associateBy { gym -> gym.id }.toMutableMap()
//            savedIds?.forEach { gymId ->
////                 this.find { gym -> gym.id == gymId }?.isFavoutite = true
//                val gym = gymsMap[gymId] ?: return@forEach
//                gymsMap[gymId] = gym.copy(isFavoutite = true)
//
//            }
//            return gymsMap.values.toList()
//        }
//        return this
//    }
//
//    companion object {
//        const val FAV_IDS = "favouriteGymIds"
//    }
}
