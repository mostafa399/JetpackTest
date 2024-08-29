package com.mostafahelal.myapplication.gym.data.di

import android.content.Context
import androidx.room.Room
import com.mostafahelal.myapplication.gym.data.local.GymsDao
import com.mostafahelal.myapplication.gym.data.local.GymsDatabase
import com.mostafahelal.myapplication.gym.data.remote.GymsApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GymsDataModule {
    @Provides
    fun provideApiServiceInstance(retrofit: Retrofit):GymsApiServices{
        return retrofit.create(GymsApiServices::class.java)
    }
    @Provides
    @Singleton
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(
                "https://cairo-gyms-15eb1-default-rtdb.firebaseio.com/"
            ).build()
    }
    @Provides
    fun provideRoomDao(db: GymsDatabase): GymsDao {
        return db.dao

    }

    @Provides
    @Singleton
    fun provideRoomDataBase(
        @ApplicationContext context: Context
    ): GymsDatabase {
        return Room.databaseBuilder(
            context = context,
            GymsDatabase::class.java,
            "gyms_database"
        ).fallbackToDestructiveMigration()
            .build()
    }
}