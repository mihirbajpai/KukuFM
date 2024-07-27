package com.example.kukufm_mihirbajpai.repository

import androidx.lifecycle.LiveData
import com.example.kukufm_mihirbajpai.model.FavoriteLaunch
import com.example.kukufm_mihirbajpai.model.FavoriteLaunchDao
import com.example.kukufm_mihirbajpai.model.LocalLaunch
import com.example.kukufm_mihirbajpai.model.LocalLaunchDao
import com.example.kukufm_mihirbajpai.model.SpaceXApiService
import javax.inject.Inject

class LaunchRepository @Inject constructor(
    private val apiService: SpaceXApiService,
    private val favoriteLaunchDao: FavoriteLaunchDao,
    private val localLaunchDao: LocalLaunchDao
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
    fun isFavorite(flightNumber: Int): LiveData<Boolean> {
        return favoriteLaunchDao.isFavorite(flightNumber)
    }
    suspend fun getAllLocalData() = localLaunchDao.getAllData()

    suspend fun insertData(data: LocalLaunch) = localLaunchDao.insertData(data)
}
