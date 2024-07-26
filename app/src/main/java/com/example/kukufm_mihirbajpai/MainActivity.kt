package com.example.kukufm_mihirbajpai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kukufm_mihirbajpai.ui.theme.KukuFMPrimary
import com.example.kukufm_mihirbajpai.ui.theme.KukuFmMihirBajpaiTheme
import com.example.kukufm_mihirbajpai.view.BottomNavItem
import com.example.kukufm_mihirbajpai.view.BottomNavigationBar
import com.example.kukufm_mihirbajpai.view.HomeScreen
import com.example.kukufm_mihirbajpai.view.LaunchDetailsScreen
import com.example.kukufm_mihirbajpai.view.SearchScreen
import com.example.kukufm_mihirbajpai.view.StoreScreen
import com.example.kukufm_mihirbajpai.view.TopBar
import com.example.kukufm_mihirbajpai.viewmodel.LaunchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KukuFmMihirBajpaiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContainer()
                }
            }
        }
    }
}

@Composable
fun AppContainer() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavigationGraph(navController, Modifier.padding(innerPadding))
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: LaunchViewModel = hiltViewModel()
) {
    val launches by viewModel.launches.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(true)

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                trackColor = KukuFMPrimary,
                color = White
            )
        }
    } else {
        NavHost(navController, startDestination = BottomNavItem.Home.route, modifier = modifier) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(launches = launches, navController = navController) {
                    viewModel.loadData()
                }
            }
            composable(BottomNavItem.Search.route) { SearchScreen() }
            composable(BottomNavItem.Store.route) { StoreScreen() }

            composable(
                "detail_screen/{flightNumber}",
                arguments = listOf(navArgument("flightNumber") { type = NavType.IntType })
            ) { backStackEntry ->
                val flightNumber = backStackEntry.arguments?.getInt("flightNumber")
                val launch = launches.find { it.flight_number == flightNumber }
                launch?.let {
                    LaunchDetailsScreen(launch = launch)
                }
            }
        }
    }
}
