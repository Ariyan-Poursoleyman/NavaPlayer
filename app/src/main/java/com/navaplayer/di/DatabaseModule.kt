package com.navaplayer.di

import android.content.Context
import androidx.room.Room
import com.navaplayer.model.AppDatabase
import com.navaplayer.model.FavoriteDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "nava_database"
        ).build()
    }

    @Provides
    fun provideFavoriteDao(db: AppDatabase): FavoriteDAO {
        return db.favoriteDao()
    }
}