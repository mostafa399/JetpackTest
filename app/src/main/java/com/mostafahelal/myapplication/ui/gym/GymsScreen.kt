package com.mostafahelal.myapplication.ui.gym

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mostafahelal.myapplication.ui.theme.Purple40


@Composable
fun GymsScreen( onItemClick: (Int)->Unit) {
    val vm: GymsViewModel = viewModel()
    LazyColumn() {
        items(vm.gymList) {
            GymItem(gym = it, onFavouriteIconClick = { gymId ->
                vm.toggleFavouriteIcon(gymId)
            }, onItemClick = {id-> onItemClick(id)

            })
        }
    }

}

@Composable
fun GymItem(gym: Gym, onFavouriteIconClick: (Int) -> Unit, onItemClick: (Int)->Unit) {
    var icon = if (gym.isFavoutite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(gym.id) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
        ) {
            GymIcon(Icons.Filled.Place, Modifier.weight(.15f), contentDescription = "Location")
            GymDetails(gym, Modifier.weight(.7f))
            GymIcon(icon, Modifier.weight(.15f), contentDescription = "Favourite") {
                onFavouriteIconClick(gym.id)
            }


        }
    }

}

@Composable
fun GymDetails(gym: Gym, modifier: Modifier, horizontelAlignment:Alignment.Horizontal=Alignment.Start) {
    Column(
        modifier=modifier,
        horizontalAlignment = horizontelAlignment
    ) {
        Text(
            text=gym.name,
            style = MaterialTheme.typography.headlineLarge,
            color = Purple40,
            maxLines = 1
        )
        CompositionLocalProvider(LocalContentColor.provides(Color.DarkGray)) {
            Text(
                text=gym.des,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                maxLines = 2
            )
        }
    }

}

@Composable
fun GymIcon(
    place: ImageVector,
    modifier: Modifier,
    contentDescription: String,
    onClick: () -> Unit = {}
) {

    Image(
        imageVector = place,
        contentDescription = contentDescription,
        modifier = modifier
            .padding(8.dp)
            .clickable {
                onClick()
            },
        colorFilter = ColorFilter.tint(Color.DarkGray)

    )
}
//property delegation ->by
//state hoisting