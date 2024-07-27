package com.example.kukufm_mihirbajpai

import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kukufm_mihirbajpai.model.Launch
import com.example.kukufm_mihirbajpai.model.LocalLaunch
import com.example.kukufm_mihirbajpai.ui.theme.KukuFMPrimary
import com.example.kukufm_mihirbajpai.ui.theme.KukuFmMihirBajpaiTheme
import com.example.kukufm_mihirbajpai.util.NetworkConnectivityObserver
import com.example.kukufm_mihirbajpai.util.NetworkUtils
import com.example.kukufm_mihirbajpai.view.BottomNavItem
import com.example.kukufm_mihirbajpai.view.BottomNavigationBar
import com.example.kukufm_mihirbajpai.view.FavoriteScreen
import com.example.kukufm_mihirbajpai.view.HomeScreen
import com.example.kukufm_mihirbajpai.view.LaunchDetailsScreen
import com.example.kukufm_mihirbajpai.view.OfflineScreen
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
fun AppContainer(
    viewModel: LaunchViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val isLoading by viewModel.isLoading.observeAsState(true)
    val networkObserver = remember { NetworkConnectivityObserver(context) }
    val isOnline = networkObserver.observeAsState(NetworkUtils.isOnline(context))
    Log.d("MainActivity1", "${isOnline.value}")

    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                isFavoriteEnable = isOnline.value && !isLoading
            )
        },
        bottomBar = {
            if (!isLoading && isOnline.value) BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavigationGraph(
            navController = navController,
            viewModel = viewModel,
            isLoading = isLoading,
            isOnline = isOnline,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: LaunchViewModel,
    isLoading: Boolean,
    isOnline: State<Boolean>,
    modifier: Modifier = Modifier,
) {
    var localLaunchesList by remember { mutableStateOf(emptyList<LocalLaunch>()) }
    var launchesList by remember { mutableStateOf(emptyList<Launch>()) }

    // Update the list of launches when online
    if (isOnline.value) {
        launchesList = viewModel.launches.observeAsState(emptyList()).value
    }

    // Load local data if online
    LaunchedEffect(key1 = launchesList.size) {
        viewModel.getAllLocalData {
            localLaunchesList = it
            if (isOnline.value && it.isEmpty()) {
                launchesList.forEach { launch ->
                    viewModel.insertLocalData(
                        LocalLaunch(
                            flight_number = launch.flight_number,
                            mission_name = launch.mission_name,
                            launch_year = launch.launch_year,
                            rocket_name = launch.rocket.rocket_name
                        )
                    )
                }
            }
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                trackColor = KukuFMPrimary,
                color = White
            )
        }
    } else {
        NavHost(
            navController,
            startDestination = if (isOnline.value) BottomNavItem.Home.route else "offline_screen",
            modifier = modifier
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    launches = launchesList,
                    navController = navController
                ) {
                    viewModel.loadData()
                }
            }
            composable(BottomNavItem.Search.route) {
                SearchScreen(
                    navController = navController,
                    viewModel = viewModel,
                    launchesList = launchesList
                )
            }
            composable(BottomNavItem.Store.route) { StoreScreen() }
            composable(
                "detail_screen/{flightNumber}",
                arguments = listOf(navArgument("flightNumber") { type = NavType.IntType })
            ) { backStackEntry ->
                val flightNumber = backStackEntry.arguments?.getInt("flightNumber")
                val launch = launchesList.find { it.flight_number == flightNumber }
                launch?.let {
                    LaunchDetailsScreen(launch = launch)
                }
            }
            composable("favorite_screen") {
                viewModel.getFavorites()
                FavoriteScreen(
                    launchesList = launchesList,
                    navController = navController,
                    viewModel = viewModel
                )
            }
            composable("offline_screen") {
                OfflineScreen(
                    localLaunches = localLaunchesList,
                    isOnline = isOnline,
                    viewModel = viewModel
                )
            }
        }
    }
}
