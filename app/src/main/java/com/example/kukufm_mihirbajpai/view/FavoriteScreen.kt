package com.example.kukufm_mihirbajpai.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kukufm_mihirbajpai.R
import com.example.kukufm_mihirbajpai.model.data.Launch
import com.example.kukufm_mihirbajpai.ui.theme.KukuFMPrimary
import com.example.kukufm_mihirbajpai.viewmodel.LaunchViewModel

@Composable
fun FavoriteScreen(
    launchesList: List<Launch>,
    navController: NavController,
    viewModel: LaunchViewModel,
) {
    val favoriteFlightNumbers = viewModel.favorites.observeAsState(emptyList())
    val favoriteLaunches = launchesList.filter {
        favoriteFlightNumbers.value.contains(it.flight_number)
    }
    if (favoriteLaunches.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.favorites_is_empty),
                color = KukuFMPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.alpha(0.5f)
            )
        }
    } else {
        // Reusing HomeScreen LaunchesList
        LaunchesList(
            launchesList = favoriteLaunches,
            navController = navController,
            viewModel = viewModel
        )
    }
}