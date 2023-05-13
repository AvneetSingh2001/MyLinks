package com.avicodes.mylinks.presentation.di

import com.avicodes.mylinks.domain.repository.LinkRepository
import com.avicodes.mylinks.presentation.ui.MainActivityViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideMainActivityViewModelFactory(
        linkRepository: LinkRepository
    ): MainActivityViewModelFactory {
        return MainActivityViewModelFactory(
            linkRepository = linkRepository
        )
    }

}