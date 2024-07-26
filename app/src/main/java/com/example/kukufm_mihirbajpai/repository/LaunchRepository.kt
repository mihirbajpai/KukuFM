package com.example.kukufm_mihirbajpai.repository

import com.example.kukufm_mihirbajpai.model.SpaceXApiService
import javax.inject.Inject

class LaunchRepository @Inject constructor(private val apiService: SpaceXApiService) {
    suspend fun getLaunches() = apiService.getLaunches()
}
