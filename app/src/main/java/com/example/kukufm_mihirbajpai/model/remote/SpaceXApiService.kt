package com.example.kukufm_mihirbajpai.model.remote

import com.example.kukufm_mihirbajpai.model.data.Launch
import retrofit2.http.GET

interface SpaceXApiService {
    @GET("launches")
    suspend fun getLaunches(): List<Launch>
}
