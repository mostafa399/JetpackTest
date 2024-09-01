package com.mostafahelal.myapplication

import com.mostafahelal.myapplication.DummyGymsList.getDummyGeneralGyms
import com.mostafahelal.myapplication.DummyGymsList.getDummyGyms
import com.mostafahelal.myapplication.gym.data.GymsRepository
import com.mostafahelal.myapplication.gym.data.local.GymsDao
import com.mostafahelal.myapplication.gym.data.local.GymsFavouriteState
import com.mostafahelal.myapplication.gym.data.local.LocalGym
import com.mostafahelal.myapplication.gym.data.remote.GymsApiServices
import com.mostafahelal.myapplication.gym.data.remote.RemoteGym
import com.mostafahelal.myapplication.gym.domain.GetInitialGymsUseCase
import com.mostafahelal.myapplication.gym.domain.GetSortedGymsUsecase
import com.mostafahelal.myapplication.gym.domain.ToggleFavouriteStateUseCase
import com.mostafahelal.myapplication.gym.presentation.gymsList.GymsScreenState
import com.mostafahelal.myapplication.gym.presentation.gymsList.GymsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
@ExperimentalCoroutinesApi
class GymsViewmodelTest {
    private val dispacher= StandardTestDispatcher()
    private val scope= TestScope(dispacher)
    @Test
    fun loadingState_isSetCorrectly()=scope.runTest {
        val viewmodel = getGymsViewModel()
        val state=viewmodel.state.value
        assert(
            state==GymsScreenState(
                gym = emptyList(),
                isLoading = true,
                error = null
            )
        )
    }
    @Test
    fun loadedContentState_isSetCorrectly()=scope.runTest {
        val viewmodel = getGymsViewModel()
        advanceUntilIdle()
        val state=viewmodel.state.value
        assert(
            state==GymsScreenState(
                gym = getDummyGeneralGyms(),
                isLoading = false,
                error = null
            )
        )
    }

    private fun getGymsViewModel(): GymsViewModel {
        val gymsRepository=GymsRepository(TestGymsApiService(),TestGymsDao(),dispacher)
        val getStoredGymsUseCase=GetSortedGymsUsecase(gymsRepository)
        val getInitialGymsUseCase=GetInitialGymsUseCase(gymsRepository,getStoredGymsUseCase)
        val toggleFavouriteStateUseCase=ToggleFavouriteStateUseCase(gymsRepository,getStoredGymsUseCase)
        return GymsViewModel(getInitialGymsUseCase,toggleFavouriteStateUseCase,dispacher)
    }
    class TestGymsApiService:GymsApiServices {
        override suspend fun getGyms(): List<RemoteGym> {
            return getDummyGyms()
        }

        override suspend fun getGym(id: Int): Map<String, RemoteGym> {
            TODO("Not yet implemented")
        }
    }
    class TestGymsDao:GymsDao {
        private var gyms= HashMap<Int,LocalGym>()
        override suspend fun getGyms(): List<LocalGym> {
          return gyms.values.toList()
        }

        override suspend fun addGyms(gyms: List<LocalGym>) {
            gyms.forEach{
                this.gyms[it.id]=it
            }
        }

        override suspend fun update(gymsFavouriteState: GymsFavouriteState) {
           updateGym(gymsFavouriteState)
        }

        override suspend fun updateAll(gymsFavouriteState: List<GymsFavouriteState>) {
           gymsFavouriteState.forEach {
               updateGym(it)
           }
        }

        override suspend fun getFavouriteGyms(): List<LocalGym> {
            return gyms.values.toList().filter { it.isFavoutite }
        }
        private fun updateGym(gymState:GymsFavouriteState){
            val gym=this.gyms[gymState.id]
            gym?.let {
                this.gyms[gymState.id]=gym.copy(
                    isFavoutite = gymState.isFavoutite
                )
            }

        }
    }

}