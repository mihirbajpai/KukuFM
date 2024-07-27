package com.example.kukufm_mihirbajpai.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kukufm_mihirbajpai.R
import com.example.kukufm_mihirbajpai.model.Launch
import com.example.kukufm_mihirbajpai.ui.theme.BackgroundColor
import com.example.kukufm_mihirbajpai.ui.theme.KukuFMPrimary
import com.example.kukufm_mihirbajpai.ui.theme.Purple40
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(
    launchesList: List<Launch>,
    navController: NavController,
    onRefresh: () -> Unit
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { onRefresh() }
    ) {
        LaunchesList(launchesList, navController)
    }
}

@Composable
fun LaunchesList(
    launchesList: List<Launch>,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        items(launchesList) { launch ->
            LaunchItem(launch) {
                navController.navigate("detail_screen/${launch.flight_number}")
            }
        }
    }
}

@Composable
fun LaunchItem(launch: Launch, onClick: ()->Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClick()
            },
        elevation = 4.dp,
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
                    heading = "Launch Year: ",
                    text = launch.launch_year,
                )
                Spacer(modifier = Modifier.height(4.dp))
                ShowText(
                    heading = "Rocket: ",
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
}

@Composable
fun ShowText(
    heading: String = "",
    text: String
){
    Row {
        Text(text = heading, color =  Purple40, fontWeight = FontWeight.Bold)
        Text(text = text, color =  Gray)
    }
}