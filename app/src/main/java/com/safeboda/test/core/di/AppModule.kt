package com.safeboda.test.core.di

import android.content.Context
import androidx.room.Room
import com.safeboda.test.core.SafeBoadDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SafeBoadDatabase =
        Room.databaseBuilder(context, SafeBoadDatabase::class.java, "SafeBoadDb")
            .build()

}