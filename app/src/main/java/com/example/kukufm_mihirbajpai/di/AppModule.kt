package com.example.kukufm_mihirbajpai.di

import android.content.Context
import androidx.room.Room
import com.example.kukufm_mihirbajpai.model.local.AppDatabase
import com.example.kukufm_mihirbajpai.model.local.FavoriteLaunchDao
import com.example.kukufm_mihirbajpai.model.local.LocalLaunchDao
import com.example.kukufm_mihirbajpai.model.remote.SpaceXApiService
import com.example.kukufm_mihirbajpai.repository.LaunchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.spacexdata.com/v3/"
    private const val DATABASE_NAME = "launches_database"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideSpaceXApiService(retrofit: Retrofit): SpaceXApiService =
        retrofit.create(SpaceXApiService::class.java)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideFavoriteLaunchDao(db: AppDatabase): FavoriteLaunchDao {
        return db.favoriteLaunchDao()
    }

    @Provides
    fun provideLocalLaunchDao(db: AppDatabase): LocalLaunchDao {
        return db.localLaunchDao()
    }

    @Provides
    fun provideLaunchRepository(
        apiService: SpaceXApiService,
        favoriteLaunchDao: FavoriteLaunchDao,
        localLaunchDao: LocalLaunchDao
    ): LaunchRepository {
        return LaunchRepository(apiService, favoriteLaunchDao, localLaunchDao)
    }
}

