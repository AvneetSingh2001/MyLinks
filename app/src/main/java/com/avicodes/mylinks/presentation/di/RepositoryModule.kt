package com.avicodes.mylinks.presentation.di

import android.content.Context
import com.avicodes.mylinks.data.api.ApiService
import com.avicodes.mylinks.data.prefs.TokenPrefs
import com.avicodes.mylinks.data.repository.LinkRepositoryImpl
import com.avicodes.mylinks.domain.repository.LinkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun providesLinkRepository(apiService: ApiService): LinkRepository {
        return LinkRepositoryImpl(apiService = apiService)
    }
}