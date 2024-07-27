package com.example.kukufm_mihirbajpai.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kukufm_mihirbajpai.R
import com.example.kukufm_mihirbajpai.Routes
import com.example.kukufm_mihirbajpai.ui.theme.KukuFMSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController, isFavoriteEnable: Boolean) {
    val currentRoute = currentRoute(navController)
    val context = LocalContext.current
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.kuku_fm_logo),
                    contentDescription = "Menu",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(start = 8.dp)
                )
                Text(
                    text = when (currentRoute) {
                        BottomNavItem.Home.route -> "Home"
                        BottomNavItem.Search.route -> "Search"
                        BottomNavItem.Store.route -> "Store"
                        Routes.Details.route -> "Details"
                        Routes.Favorite.route -> "Your Favorite's"
                        Routes.Offline.route -> "Offline Home"
                        else -> "App"
                    },
                    color = Color.White,
                    modifier = Modifier.padding(end = 30.dp)
                )
                IconButton(
                    onClick = {
                        if (currentRoute == Routes.Favorite.route) {
                            Unit    //Do nothing
                        } else if (isFavoriteEnable) {
                            navController.navigate(Routes.Favorite.route)
                        } else {
                            Toast.makeText(
                                context,
                                "Please connect to the internet!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier.alpha(if (currentRoute == Routes.Favorite.route) 0f else 1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "favorites",
                        tint = Color.Red,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = KukuFMSecondary
        )
    )
}