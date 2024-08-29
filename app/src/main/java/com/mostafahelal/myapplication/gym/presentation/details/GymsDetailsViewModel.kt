package com.mostafahelal.myapplication.gym.presentation.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafahelal.myapplication.gym.data.remote.GymsApiServices
import com.mostafahelal.myapplication.gym.domain.Gym
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val gymState = mutableStateOf<Gym?>(null)
    private val gymsApiServices: GymsApiServices
    init {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(
                "https://cairo-gyms-15eb1-default-rtdb.firebaseio.com/"
            ).build()
        gymsApiServices = retrofit.create(GymsApiServices::class.java)

        val id=savedStateHandle.get<Int>("gym_id")?:0
        getGym(id)
    }
    private fun getGym(id: Int) {
        viewModelScope.launch() {
            val gyms = getGymFromRemoteDb(id)
            gymState.value=gyms

        }

    }
    private suspend fun getGymFromRemoteDb(id:Int) =
        withContext(Dispatchers.IO) {
            gymsApiServices.getGym(id).values.first().let {
                Gym(
                    it.id,
                    it.name,
                    it.des,
                    it.is_open,

                )
            }
        }



}
