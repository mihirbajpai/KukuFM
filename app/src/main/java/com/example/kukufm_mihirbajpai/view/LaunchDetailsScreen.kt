package com.example.kukufm_mihirbajpai.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kukufm_mihirbajpai.R
import com.example.kukufm_mihirbajpai.model.data.Launch
import com.example.kukufm_mihirbajpai.ui.theme.BackgroundColor
import com.example.kukufm_mihirbajpai.ui.theme.KukuFMPrimary
import com.example.kukufm_mihirbajpai.ui.theme.Purple100
import com.example.kukufm_mihirbajpai.ui.theme.Purple40
import com.example.kukufm_mihirbajpai.viewmodel.LaunchViewModel.Companion.TIME_FORMAT
import com.example.kukufm_mihirbajpai.viewmodel.LaunchViewModel.Companion.TXT_PAYLOAD
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LaunchDetailsScreen(launch: Launch) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(launch.links.mission_patch)
                .placeholder(R.drawable.ic_rocket)
                .error(R.drawable.ic_rocket)
                .build(),
            contentDescription = "Mission Patch",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.mission_name, launch.mission_name),
            color = KukuFMPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        ShowText(
            heading = stringResource(R.string.launch_date),
            text = convertDate(launch.launch_date_utc)
        )

        Spacer(modifier = Modifier.height(8.dp))

        ShowText(
            heading = stringResource(R.string.launch_site),
            text = launch.launch_site.site_name_long
        )

        Spacer(modifier = Modifier.height(8.dp))

        ShowText(
            heading = stringResource(R.string.launch_success),
            text = if (launch.launch_success != false) stringResource(R.string.yes) else stringResource(
                R.string.no
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        val rocketDetails = listOf(
            Pair(first = stringResource(R.string.rocket_id), second = launch.rocket.rocket_id),
            Pair(first = stringResource(R.string.rocket_name), second = launch.rocket.rocket_name),
            Pair(first = stringResource(R.string.rocket_type), second = launch.rocket.rocket_type)
        )
        ExpandableCard(
            heading = stringResource(R.string.rocket_details),
            detailsItems = rocketDetails
        )

        Spacer(modifier = Modifier.height(8.dp))

        val detailsText = listOf(
            Pair(first = "", second = launch.details ?: "")
        )
        ExpandableCard(
            heading = stringResource(R.string.launch_details),
            detailsItems = detailsText
        )

        Spacer(modifier = Modifier.height(8.dp))

        val payloadList = launch.rocket.second_stage.payloads.flatMap { payload ->
            listOf(
                Pair("Payload", payload.payload_id),
                Pair("Reused: ", if (payload.reused) "Yes" else "No"),
                Pair("Nationality: ", payload.nationality),
                Pair("Manufacturer: ", payload.manufacturer),
                Pair("Payload Type: ", payload.payload_type),
                Pair("Mass (kg): ", payload.payload_mass_kg?.toString() ?: "N/A"),
                Pair("Orbit: ", payload.orbit)
            )
        }
        ExpandableCard(heading = stringResource(R.string.payloads), detailsItems = payloadList)

        Spacer(modifier = Modifier.height(16.dp))

        launch.links.article_link?.let { link ->
            Text(
                text = stringResource(R.string.important_links),
                color = KukuFMPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            ClickableUrlText(heading = stringResource(R.string.article_link), url = link)
            Spacer(modifier = Modifier.height(4.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        launch.links.video_link?.let { link ->
            ClickableUrlText(heading = stringResource(R.string.video_link), url = link)
            Spacer(modifier = Modifier.height(4.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        launch.links.wikipedia?.let { link ->
            ClickableUrlText(heading = stringResource(R.string.wikipedia_link), url = link)
        }
    }
}

@Composable
fun ExpandableCard(
    heading: String,
    detailsItems: List<Pair<String, String>>
) {
    var isExpanded by remember { mutableStateOf(false) }
    val rotation = remember { Animatable(-180f) }

    LaunchedEffect(key1 = isExpanded) {
        if (isExpanded) {
            rotation.animateTo(-180f)
        } else {
            rotation.animateTo(0f)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(
                color = Purple100
            )
            .clickable {
                isExpanded = !isExpanded
            }
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = heading,
                color = KukuFMPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            androidx.compose.material.Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .rotate(rotation.value),
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = null
            )
        }
        AnimatedVisibility(isExpanded) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                detailsItems.forEach { item ->
                    if (item.first != null && item.second != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        if (item.first == TXT_PAYLOAD) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "${item.first} - ${item.second}:",
                                color = Purple40,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                textDecoration = TextDecoration.Underline
                            )
                        } else {
                            ShowText(
                                heading = item.first,
                                text = item.second
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ClickableUrlText(heading: String, url: String) {
    val context = LocalContext.current
    Row {
        Text(
            text = "$heading: ",
            color = Purple40,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = url,
            color = Blue,
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            },
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

fun convertDate(originalDate: String): String {
    val originalFormatter = DateTimeFormatter.ISO_DATE_TIME
    val targetFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT)
    val zonedDateTime = ZonedDateTime.parse(originalDate, originalFormatter)
    return zonedDateTime.format(targetFormatter)
}