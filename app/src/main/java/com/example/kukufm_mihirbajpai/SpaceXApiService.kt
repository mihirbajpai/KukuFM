package com.example.kukufm_mihirbajpai

import retrofit2.http.GET

interface SpaceXApiService {
    @GET("launches")
    suspend fun getLaunches(): List<Launch>
}
