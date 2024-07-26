package com.example.kukufm_mihirbajpai.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kukufm_mihirbajpai.R
import com.example.kukufm_mihirbajpai.ui.theme.KukuFMSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController) {
    val currentRoute = currentRoute(navController)
    TopAppBar(
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.kuku_fm_logo), // Replace with your drawable resource ID
                contentDescription = "Menu",
                modifier = Modifier
                    .size(60.dp)
                    .padding(start = 8.dp)
            )
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(end = 60.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when (currentRoute) {
                        BottomNavItem.Home.route -> "Home"
                        BottomNavItem.Search.route -> "Search"
                        BottomNavItem.Store.route -> "Store"
                        "detail_screen/{flightNumber}" -> "Details"
                        else -> "App"
                    },
                    color = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = KukuFMSecondary
        )
    )
}