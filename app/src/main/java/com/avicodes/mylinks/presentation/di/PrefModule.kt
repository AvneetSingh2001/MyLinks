package com.avicodes.mylinks.presentation.di

import android.content.Context
import com.avicodes.mylinks.data.prefs.TokenPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PrefModule {
    @Singleton
    @Provides
    fun provideTokenPref(@ApplicationContext context: Context): TokenPrefs {
        return TokenPrefs(context)
    }
}