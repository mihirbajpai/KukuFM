package com.example.kukufm_mihirbajpai

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.lang.reflect.Modifier

@Composable
fun LaunchListScreen(viewModel: LaunchViewModel = hiltViewModel()) {
    val launches by viewModel.launches.observeAsState(emptyList())

    LazyColumn {
        items(launches) { launch ->
            LaunchItem(launch)
        }
    }
}

@Composable
fun LaunchItem(launch: Launch) {
    Column() {
        Text(text = "Mission Name: ${launch.mission_name}")
        Text(text = "Launch Year: ${launch.launch_year}")
        Text(text = "Rocket Name: ${launch.rocket.rocket_name}")
    }
}