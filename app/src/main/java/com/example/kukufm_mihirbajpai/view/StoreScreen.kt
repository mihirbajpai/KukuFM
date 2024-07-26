package com.example.kukufm_mihirbajpai.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView


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
