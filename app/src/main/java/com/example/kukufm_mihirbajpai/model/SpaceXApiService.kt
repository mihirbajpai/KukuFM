package com.example.kukufm_mihirbajpai.model

import retrofit2.http.GET

interface SpaceXApiService {
    @GET("launches")
    suspend fun getLaunches(): List<Launch>
}
