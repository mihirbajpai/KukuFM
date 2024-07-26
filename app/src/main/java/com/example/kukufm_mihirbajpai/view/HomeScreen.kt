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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    launches: List<Launch>,
    navController: NavController,
    onRefresh: () -> Unit
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { onRefresh() }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
        ) {
            items(launches) { launch ->
                LaunchItem(launch) {
                    navController.navigate("detail_screen/${launch.flight_number}")
                }
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
        backgroundColor = Color.White
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
                    .data(launch.links.mission_patch) // URL to image
                    .placeholder(R.drawable.ic_rocket) // Placeholder image
                    .error(R.drawable.ic_rocket) // Error image
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
        Text(text = heading, color =  MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        Text(text = text, color =  Color.Gray)
    }
}