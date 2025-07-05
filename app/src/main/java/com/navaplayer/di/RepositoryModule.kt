package com.navaplayer.di

import com.navaplayer.model.FavoriteDAO
import com.navaplayer.repository.FavoriteRepository
import com.navaplayer.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFavoriteRepository(dao: FavoriteDAO): FavoriteRepository =
        FavoriteRepository(dao)

    @Provides
    @Singleton
    fun provideRepository(): Repository =
        Repository()
}
