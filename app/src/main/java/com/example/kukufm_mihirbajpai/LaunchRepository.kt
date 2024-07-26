package com.example.kukufm_mihirbajpai

import javax.inject.Inject

class LaunchRepository @Inject constructor(private val apiService: SpaceXApiService) {
    suspend fun getLaunches() = apiService.getLaunches()
}
