package com.haris.home.di

import com.haris.home.repositories.Api
import com.haris.home.repositories.Repository
import com.haris.home.repositories.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@InstallIn(ViewModelComponent::class)
@Module
internal object Module {

    @Provides
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    fun provideRepository(api: Api): Repository {
        return RepositoryImpl(api)
    }
}