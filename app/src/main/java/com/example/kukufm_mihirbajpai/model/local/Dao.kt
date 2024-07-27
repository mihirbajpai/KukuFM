package com.example.kukufm_mihirbajpai.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kukufm_mihirbajpai.model.data.FavoriteLaunch
import com.example.kukufm_mihirbajpai.model.data.LocalLaunch

@Dao
interface FavoriteLaunchDao {
    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<FavoriteLaunch>

    @Insert
    suspend fun addFavorite(favoriteLaunch: FavoriteLaunch)

    @Delete
    suspend fun removeFavorite(favoriteLaunch: FavoriteLaunch)

    @Query("SELECT COUNT(*) > 0 FROM favorites WHERE flightNumber = :flightNumber")
    fun isFavorite(flightNumber: Int): LiveData<Boolean>

    @Query("SELECT * FROM favorites WHERE flightNumber = :flightNumber")
    suspend fun getFavoriteByFlightNumber(flightNumber: Int): FavoriteLaunch?
}

@Dao
interface LocalLaunchDao {
    @Query("SELECT * FROM local_data")
    suspend fun getAllData(): List<LocalLaunch>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: LocalLaunch)
}