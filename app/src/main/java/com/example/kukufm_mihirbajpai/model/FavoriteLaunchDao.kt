package com.example.kukufm_mihirbajpai.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteLaunchDao {
    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<FavoriteLaunch>

    @Insert
    suspend fun addFavorite(favoriteLaunch: FavoriteLaunch)

    @Delete
    suspend fun removeFavorite(favoriteLaunch: FavoriteLaunch)

    @Query("SELECT * FROM favorites WHERE flightNumber = :flightNumber")
    suspend fun getFavoriteByFlightNumber(flightNumber: Int): FavoriteLaunch?
}
