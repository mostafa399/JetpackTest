package com.mostafahelal.myapplication.gym.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher
@Module
@InstallIn(SingletonComponent::class)
object DispacherModule {
    @MainDispatcher
    @Provides
    fun provideMainDispacher():CoroutineDispatcher=Dispatchers.Main
    @IoDispatcher
    @Provides
    fun provideIoDispacher():CoroutineDispatcher=Dispatchers.IO
}