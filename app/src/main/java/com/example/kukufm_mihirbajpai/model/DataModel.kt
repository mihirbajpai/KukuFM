package com.example.kukufm_mihirbajpai.model

data class Launch(
    val flight_number: Int,
    val mission_name: String,
    val launch_year: String,
    val launch_date_utc: String,
    val rocket: Rocket,
    val launch_site: LaunchSite,
    val launch_success: Boolean?,
    val links: Links,
    val details: String?,
)

data class Rocket(
    val rocket_id: String,
    val rocket_name: String,
    val rocket_type: String,
    val second_stage: SecondStage,
)

data class SecondStage(
    val payloads: List<Payload>
)

data class Payload(
    val payload_id: String,
    val reused: Boolean,
    val nationality: String,
    val manufacturer: String,
    val payload_type: String,
    val payload_mass_kg: Double?,
    val orbit: String,
)

data class LaunchSite(
    val site_name_long: String
)

data class Links(
    val mission_patch: String,
    val article_link: String?,
    val wikipedia: String?,
    val video_link: String?,
)