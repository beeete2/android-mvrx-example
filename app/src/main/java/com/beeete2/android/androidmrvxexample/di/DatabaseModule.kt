package com.beeete2.android.androidmrvxexample.di

import com.beeete2.android.androidmrvxexample.model.repository.UserRepository
import com.beeete2.android.androidmrvxexample.model.repository.UserLocalRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository = UserLocalRepository()

}