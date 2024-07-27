package com.example.kukufm_mihirbajpai.repository

import com.example.kukufm_mihirbajpai.model.FavoriteLaunch
import com.example.kukufm_mihirbajpai.model.FavoriteLaunchDao
import com.example.kukufm_mihirbajpai.model.SpaceXApiService
import javax.inject.Inject

class LaunchRepository @Inject constructor(
    private val apiService: SpaceXApiService,
    private val favoriteLaunchDao: FavoriteLaunchDao
) {
    suspend fun getLaunches() = apiService.getLaunches()

    suspend fun getFavorites() = favoriteLaunchDao.getAllFavorites()
    suspend fun addFavorite(flightNumber: Int) = favoriteLaunchDao.addFavorite(FavoriteLaunch(flightNumber))
    suspend fun removeFavorite(flightNumber: Int) {
        val favorite = favoriteLaunchDao.getFavoriteByFlightNumber(flightNumber)
        if (favorite != null) {
            favoriteLaunchDao.removeFavorite(favorite)
        }
    }
}
