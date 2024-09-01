package com.mostafahelal.myapplication.gym.presentation.gymsList

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafahelal.myapplication.gym.data.di.MainDispatcher
import com.mostafahelal.myapplication.gym.domain.GetInitialGymsUseCase
import com.mostafahelal.myapplication.gym.domain.ToggleFavouriteStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class GymsViewModel @Inject constructor(
    private val getAllGymsUseCase : GetInitialGymsUseCase,
    private val toggleFavouriteStateUseCase : ToggleFavouriteStateUseCase,
    @MainDispatcher private val dispacher:CoroutineDispatcher,
) : ViewModel() {

    private var _state by mutableStateOf(GymsScreenState(gym = emptyList(), isLoading = true))
    val state: State<GymsScreenState> get() = derivedStateOf { _state }
    val errorHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _state = _state.copy(isLoading = false, error = throwable.message)
    }

    init {
        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch(errorHandler+dispacher) {
            val recievedGyms = getAllGymsUseCase()
            _state = _state.copy(
                gym = recievedGyms,
                isLoading = false
            )

        }

    }


    fun toggleFavouriteIcon(gymId: Int,oldValue:Boolean) {
        viewModelScope.launch(errorHandler+dispacher) {
            val updatedGymsList = toggleFavouriteStateUseCase(gymId,oldValue)
            _state = _state.copy(gym = updatedGymsList)
        }
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
