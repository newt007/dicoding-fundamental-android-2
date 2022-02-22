package com.elapp.githubuser.presentation.di

import com.elapp.githubuser.data.remote.user.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun provideUserService(retrofit: Retrofit) = retrofit.create(UserService::class.java)

}