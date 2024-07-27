package com.example.kukufm_mihirbajpai.model

import android.content.Context
import androidx.room.Room
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

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.spacexdata.com/v3/")
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
            "launches_database"
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
    fun provideLaunchRepository(apiService: SpaceXApiService, favoriteLaunchDao: FavoriteLaunchDao, localLaunchDao: LocalLaunchDao): LaunchRepository {
        return LaunchRepository(apiService, favoriteLaunchDao, localLaunchDao)
    }
}

