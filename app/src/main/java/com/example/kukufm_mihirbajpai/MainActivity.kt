package com.example.kukufm_mihirbajpai

import android.annotation.SuppressLint
import android.net.http.SslCertificate.restoreState
import android.os.Bundle
import android.webkit.WebBackForwardList
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kukufm_mihirbajpai.ui.theme.KukuFMPrimary
import com.example.kukufm_mihirbajpai.ui.theme.KukuFMSecondary
import com.example.kukufm_mihirbajpai.ui.theme.KukuFmMihirBajpaiTheme
import com.example.kukufm_mihirbajpai.ui.theme.PurpleGrey40

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KukuFmMihirBajpaiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavigationGraph(navController, Modifier.padding(innerPadding))
    }
}

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

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Store
    )

    BottomNavigation {
        val currentRoute = currentRoute(navController)
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            BottomNavigationItem(
                modifier = Modifier.background(KukuFMPrimary),
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.title,
                        tint = if (isSelected) Color.White else PurpleGrey40
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (isSelected) Color.White else PurpleGrey40
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

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = BottomNavItem.Home.route, modifier = modifier) {
        composable(BottomNavItem.Home.route) { HomeScreen() }
        composable(BottomNavItem.Search.route) { SearchScreen() }
        composable(BottomNavItem.Store.route) { StoreScreen() }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    Scaffold {
        Text("Welcome to Home Screen", modifier = Modifier.padding(16.dp))
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen() {
    Scaffold {
        Text("Welcome to Search Screen", modifier = Modifier.padding(16.dp))
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StoreScreen() {
    val webViewState = rememberSaveable { mutableStateOf<Bundle?>(null) }
    val context = LocalContext.current
    val webView = remember { WebView(context) }

    // Load URL if not already loaded
    LaunchedEffect(Unit) {
        if (webViewState.value == null) {
            webView.loadUrl("https://www.spacex.com/vehicles/falcon-9/")
        }
    }

    Scaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = {
                    webView.apply {
                        webViewClient = WebViewClient()
                        webViewState.value?.let { restoreState(it) }
                    }
                },
                update = { webView ->
                    webViewState.value?.let {
                        webView.restoreState(it)
                    } ?: run {
                        webView.loadUrl("https://www.spacex.com/vehicles/falcon-9/")
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    // Handle back press to navigate within the WebView
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    DisposableEffect(Unit) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher?.onBackPressed()
                }
            }
        }
        onBackPressedDispatcher?.addCallback(callback)
        onDispose {
            callback.remove()
        }
    }

    // Save WebView state when the composable leaves the composition
    DisposableEffect(Unit) {
        onDispose {
            val state = Bundle()
            webView.saveState(state)
            webViewState.value = state
        }
    }
}
