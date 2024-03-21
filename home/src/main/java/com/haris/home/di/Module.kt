package com.haris.home.di

import com.haris.home.Api
import com.haris.home.Repository
import com.haris.home.RepositoryImpl
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