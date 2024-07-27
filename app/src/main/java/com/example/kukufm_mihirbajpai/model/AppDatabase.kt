package com.example.kukufm_mihirbajpai.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteLaunch::class, LocalLaunch::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteLaunchDao(): FavoriteLaunchDao
    abstract fun localLaunchDao(): LocalLaunchDao
}
