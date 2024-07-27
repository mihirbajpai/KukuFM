@file:Suppress("DEPRECATION")

package com.example.kukufm_mihirbajpai.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kukufm_mihirbajpai.R
import com.example.kukufm_mihirbajpai.model.data.Launch
import com.example.kukufm_mihirbajpai.ui.theme.BackgroundColor
import com.example.kukufm_mihirbajpai.ui.theme.KukuFMPrimary
import com.example.kukufm_mihirbajpai.ui.theme.Purple40
import com.example.kukufm_mihirbajpai.viewmodel.LaunchViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(
    viewModel: LaunchViewModel,
    launches: List<Launch>,
    navController: NavController,
    onRefresh: () -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getFavorites()
    }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { onRefresh() }
    ) {
        LaunchesList(launchesList = launches, navController = navController, viewModel = viewModel)
    }
}

@Composable
fun LaunchesList(
    launchesList: List<Launch>,
    navController: NavController,
    viewModel: LaunchViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        items(launchesList) { launch ->
            LaunchItem(launch = launch, viewModel = viewModel) {
                navController.navigate("detail_screen/${launch.flight_number}")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun LaunchItem(launch: Launch, viewModel: LaunchViewModel, onClick: () -> Unit) {
    val isFavorite by viewModel.isFavoriteLiveData(launch.flight_number).observeAsState(false)

    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            elevation = 8.dp,
            shape = MaterialTheme.shapes.medium,
            backgroundColor = White
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = launch.mission_name,
                        color = KukuFMPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ShowText(
                        heading = stringResource(R.string.launch_year),
                        text = launch.launch_year
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    ShowText(
                        heading = stringResource(R.string.rocket),
                        text = launch.rocket.rocket_name
                    )
                }
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(launch.links.mission_patch)
                        .placeholder(R.drawable.ic_rocket)
                        .error(R.drawable.ic_rocket)
                        .build(),
                    contentDescription = "Mission Patch",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(end = 8.dp)
                )
            }
        }
        IconButton(
            onClick = {
                if (isFavorite) {
                    viewModel.removeFavorite(launch.flight_number)
                } else {
                    viewModel.addFavorite(launch.flight_number)
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-20).dp, y = 16.dp)
                .background(White, CircleShape)
                .size(28.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = if (isFavorite) stringResource(R.string.remove_from_favorites) else stringResource(
                    R.string.add_to_favorites
                ),
                tint = if (isFavorite) Red else Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}


@Composable
fun ShowText(
    heading: String = "",
    text: String
) {
    Row {
        Text(text = heading, color = Purple40, fontWeight = FontWeight.Bold)
        Text(text = text, color = Gray)
    }
}
