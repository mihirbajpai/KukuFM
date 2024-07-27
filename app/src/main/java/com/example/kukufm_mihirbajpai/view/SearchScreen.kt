package com.example.kukufm_mihirbajpai.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kukufm_mihirbajpai.model.Launch
import com.example.kukufm_mihirbajpai.ui.theme.BackgroundColor
import com.example.kukufm_mihirbajpai.ui.theme.KukuFMPrimary

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    launchesList: List<Launch>,
    navController: NavController
) {
    val searchText = remember { mutableStateOf("") }
    val filteredLaunches = remember { mutableStateOf(launchesList) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
            OutlinedTextField(
                value = searchText.value,
                onValueChange = { newText ->
                    searchText.value = newText
                    filteredLaunches.value = launchesList.filter { launch ->
                        launch.mission_name.contains(newText, ignoreCase = true) ||
                        launch.launch_year.contains(newText, ignoreCase = true) ||
                        launch.rocket.rocket_name.contains(newText, ignoreCase = true)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Search...",
                        color = Color.LightGray
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                ),
                textStyle = TextStyle(color = Color.DarkGray),
                maxLines = 1
            )
        }

        if (searchText.value.isNotEmpty()) {
            LaunchesList(filteredLaunches.value, navController)
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Type to search...",
                    color = KukuFMPrimary,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.alpha(0.5f)
                )
            }
        }
    }
}