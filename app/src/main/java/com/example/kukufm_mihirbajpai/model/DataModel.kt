package com.example.kukufm_mihirbajpai.model


data class Rocket(
    val rocket_name: String,
    val rocket_type: String
)

data class Launch(
    val flight_number: Int,
    val mission_name: String,
    val launch_year: String,
    val rocket: Rocket,
    val launch_site: LaunchSite,
    val links: Links
)

data class LaunchSite(
    val site_name: String
)

data class Links(
    val mission_patch: String?,
    val article_link: String?,
    val video_link: String?
)