package com.mostafahelal.myapplication

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mostafahelal.myapplication.DummyGymsList.getDummyGyms
import com.mostafahelal.myapplication.gym.presentation.SementicsDescription
import com.mostafahelal.myapplication.gym.presentation.gymsList.GymsScreen
import com.mostafahelal.myapplication.gym.presentation.gymsList.GymsScreenState
import com.mostafahelal.myapplication.ui.theme.MyApplicationTheme
import org.junit.Rule
import org.junit.Test


class GymsScreenTest {
    @get:Rule
    val testRule:ComposeContentTestRule= createComposeRule()
    @Test
    fun loadingState_isActive(){
        testRule.setContent {
            MyApplicationTheme {
                GymsScreen(state = GymsScreenState(
                    isLoading = true,
                    gym = emptyList(),
                ) , onItemClick = {},{ _:Int, _:Boolean-> })
            }
        }
        testRule.onNodeWithContentDescription(SementicsDescription.GYMS_LIST_LOADING).assertIsDisplayed()
    }
    @Test
    fun LoadedContentState_isActive(){
        val gymsList=getDummyGyms()
        testRule.setContent {
            MyApplicationTheme {
                GymsScreen(state = GymsScreenState(
                    isLoading = false,
                    gym = gymsList,
                ) , onItemClick = {},{ _:Int, _:Boolean-> })
            }
        }
        testRule.onNodeWithText(gymsList[0].name).assertIsDisplayed()
        testRule.onNodeWithContentDescription(SementicsDescription.GYMS_LIST_LOADING).assertDoesNotExist()
    }
    @Test
    fun errorState_isActive(){

        val errorText="failed to load data"
        testRule.setContent {
            MyApplicationTheme {
                GymsScreen(state = GymsScreenState(
                    isLoading = false,
                    gym = emptyList(),
                    error = errorText
                ) , onItemClick = {},{ _:Int, _:Boolean-> })
            }
        }
        testRule.onNodeWithText(errorText).assertIsDisplayed()
        testRule.onNodeWithContentDescription(SementicsDescription.GYMS_LIST_LOADING).assertDoesNotExist()
    }
    @Test
    fun onItemClicked_idIsPassedCorrectly(){
        val gymsList=getDummyGyms()
        val gymItem=gymsList[0]
        testRule.setContent {
            MyApplicationTheme {
                GymsScreen(state = GymsScreenState(
                    isLoading = false,
                    gym = gymsList,
                ) , onItemClick = {id->
                                  assert(id==gymItem.id)

                },{ _:Int, _:Boolean-> })
            }
        }
        testRule.onNodeWithText(gymItem.name).performClick()
    }

}