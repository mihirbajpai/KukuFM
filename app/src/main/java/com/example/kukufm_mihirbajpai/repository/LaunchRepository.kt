package com.example.kukufm_mihirbajpai.repository

import androidx.lifecycle.LiveData
import com.example.kukufm_mihirbajpai.model.data.FavoriteLaunch
import com.example.kukufm_mihirbajpai.model.data.LocalLaunch
import com.example.kukufm_mihirbajpai.model.local.FavoriteLaunchDao
import com.example.kukufm_mihirbajpai.model.local.LocalLaunchDao
import com.example.kukufm_mihirbajpai.model.remote.SpaceXApiService
import javax.inject.Inject

class LaunchRepository @Inject constructor(
    private val apiService: SpaceXApiService,
    private val favoriteLaunchDao: FavoriteLaunchDao,
    private val localLaunchDao: LocalLaunchDao
) {
    suspend fun getLaunches() = apiService.getLaunches()

    suspend fun getFavorites() = favoriteLaunchDao.getAllFavorites()

    suspend fun addFavorite(flightNumber: Int) =
        favoriteLaunchDao.addFavorite(FavoriteLaunch(flightNumber = flightNumber))

    suspend fun removeFavorite(flightNumber: Int) {
        val favorite = favoriteLaunchDao.getFavoriteByFlightNumber(flightNumber = flightNumber)
        if (favorite != null) {
            favoriteLaunchDao.removeFavorite(favoriteLaunch = favorite)
        }
    }

    fun isFavorite(flightNumber: Int): LiveData<Boolean> {
        return favoriteLaunchDao.isFavorite(flightNumber = flightNumber)
    }

    suspend fun getAllLocalData() = localLaunchDao.getAllData()

    suspend fun insertData(data: LocalLaunch) = localLaunchDao.insertData(data = data)
}
