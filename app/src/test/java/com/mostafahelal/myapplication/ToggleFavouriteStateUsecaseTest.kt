package com.mostafahelal.myapplication

import com.mostafahelal.myapplication.DummyGymsList.getDummyGeneralGyms
import com.mostafahelal.myapplication.gym.data.GymsRepository
import com.mostafahelal.myapplication.gym.domain.GetSortedGymsUsecase
import com.mostafahelal.myapplication.gym.domain.ToggleFavouriteStateUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class ToggleFavouriteStateUsecaseTest {
    private val dispacher= StandardTestDispatcher()
    private val scope= TestScope(dispacher)
    @Test
    fun toggleFavouriteState_updateFavouriteState()=scope.runTest{
        val gymsRepository= GymsRepository(
            GymsViewmodelTest.TestGymsApiService(),
            GymsViewmodelTest.TestGymsDao(),dispacher)
        val getStoredGymsUseCase= GetSortedGymsUsecase(gymsRepository)
        val useCaseUnderTest=ToggleFavouriteStateUseCase(gymsRepository,getStoredGymsUseCase)

        gymsRepository.loadGyms()
        advanceUntilIdle()

        val gyms=getDummyGeneralGyms()

        //function invoke
        val updateGymsList=useCaseUnderTest(gyms[0].id,gyms[0].isFavoutite)
        advanceUntilIdle()
        assert(updateGymsList[0].isFavoutite==!gyms[0].isFavoutite)
    }

}