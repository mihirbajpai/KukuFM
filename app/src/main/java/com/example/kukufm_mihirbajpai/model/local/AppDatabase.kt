package com.example.kukufm_mihirbajpai.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kukufm_mihirbajpai.model.data.FavoriteLaunch
import com.example.kukufm_mihirbajpai.model.data.LocalLaunch

@Database(entities = [FavoriteLaunch::class, LocalLaunch::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteLaunchDao(): FavoriteLaunchDao
    abstract fun localLaunchDao(): LocalLaunchDao
}
