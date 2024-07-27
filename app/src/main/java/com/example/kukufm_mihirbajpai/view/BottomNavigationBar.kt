package com.example.kukufm_mihirbajpai.view

import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.kukufm_mihirbajpai.ui.theme.KukuFMPrimary

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Store
    )
    val currentRoute = currentRoute(navController)
    if (
        currentRoute?.contains("detail_screen") != true &&
        currentRoute?.contains("favorite_screen") != true
    ) BottomNavigation {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            BottomNavigationItem(
                modifier = Modifier.background(KukuFMPrimary),
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.title,
                        tint = White,
                        modifier = Modifier.graphicsLayer(
                            alpha = if (isSelected) 1f else 0.3f
                        )
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = White,
                        modifier = Modifier.graphicsLayer(
                            alpha = if (isSelected) 1f else 0.3f
                        ),
                        textDecoration = if (isSelected) TextDecoration.Underline else TextDecoration.None
                    )
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

sealed class BottomNavItem(val title: String, val icon: ImageVector, val route: String) {
    object Home : BottomNavItem("Home", Icons.Default.Home, "home")
    object Search : BottomNavItem("Search", Icons.Default.Search, "search")
    object Store : BottomNavItem("Store", Icons.Default.Store, "store")
}