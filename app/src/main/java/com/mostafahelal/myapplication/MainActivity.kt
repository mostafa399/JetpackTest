package com.mostafahelal.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.mostafahelal.myapplication.ui.gym.GymDetailsScreen
import com.mostafahelal.myapplication.ui.gym.GymsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymsAroundApp()
        }
    }
}

@Composable
fun GymsAroundApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "gyms") {
        composable(route = "gyms") {
            GymsScreen { id ->
                navController.navigate("gyms/$id")
            }
        }
        composable(
            route = "gyms/{gym_id}",
            arguments = listOf(navArgument("gym_id") { type = NavType.IntType }),
            deepLinks = listOf(navDeepLink {
                uriPattern="https://www.gymsaround.com/details/{gym_id}"
            })
        ) {
            GymDetailsScreen() }
    }

}

@Composable
fun MyText() {
    Text(
        text = "Elephants Can Sense Storms .",
        style = TextStyle(
            color = Color.Red,
            fontSize = 24.sp,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.SansSerif,
            background = Color.Black,
            textAlign = TextAlign.Center
        ),
        maxLines = 2
    )
}

@Composable
fun MyButton() {
    var buttonIsEnabled by remember { mutableStateOf(true) }
    Button(
        onClick = { buttonIsEnabled = false },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White,
            disabledContainerColor = Color.LightGray
        ),
        enabled = buttonIsEnabled
    ) {
        Text(if (buttonIsEnabled) "Click Me" else "I,m Disabled")
    }
}

@Composable
fun MyTextField() {
    var text by remember { mutableStateOf("") }
    TextField(value = text, onValueChange = {
        text = it
    }, label = {
        Text(text = "text")
    })
}

@Composable
fun MyImage() {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = "jetpack image"
    )
}

@Composable
fun MyLayout() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        MyText()
        MyButton()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Row:")
            MyImage()
        }
    }
}

@Composable
fun MyBox() {
    Box(
        Modifier
            .size(120.dp)
            .background(Color.Black)
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.LightGray)
    ) {
        Text(text = "Hello", Modifier.align(Alignment.TopStart), color = Color.White)
        Text(text = "Android", Modifier.align(Alignment.Center), color = Color.White)
        Text(text = "Cokkies", Modifier.align(Alignment.BottomEnd), color = Color.White)
    }

}




