package com.example.kukufm_mihirbajpai.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kukufm_mihirbajpai.R
import com.example.kukufm_mihirbajpai.model.data.LocalLaunch
import com.example.kukufm_mihirbajpai.ui.theme.BackgroundColor
import com.example.kukufm_mihirbajpai.ui.theme.KukuFMPrimary
import com.example.kukufm_mihirbajpai.viewmodel.LaunchViewModel

@Composable
fun OfflineScreen(
    localLaunches: List<LocalLaunch>,
    isOnline: State<Boolean>,
    viewModel: LaunchViewModel
) {
    LaunchedEffect(key1 = isOnline.value) {
        if (isOnline.value) viewModel.loadData()
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = if (isOnline.value) Green else Red)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (isOnline.value) stringResource(R.string.you_are_back_online) else stringResource(
                        R.string.you_are_offline
                    ),
                    color = White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        items(localLaunches) { launch ->
            LocalLaunchItem(localLaunch = launch)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun LocalLaunchItem(localLaunch: LocalLaunch) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    Toast
                        .makeText(context, "Please connect to the internet!", Toast.LENGTH_SHORT)
                        .show()
                },
            elevation = 4.dp,
            shape = MaterialTheme.shapes.medium,
            backgroundColor = White
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = localLaunch.mission_name,
                        color = KukuFMPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ShowText(
                        heading = stringResource(R.string.launch_year),
                        text = localLaunch.launch_year
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    ShowText(
                        heading = stringResource(R.string.rocket),
                        text = localLaunch.rocket_name
                    )
                }
                Image(
                    painter = painterResource(R.drawable.ic_rocket),
                    contentDescription = "Offline Image",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}