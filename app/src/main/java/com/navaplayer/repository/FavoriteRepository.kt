package com.navaplayer.repository

import android.content.Context
import com.navaplayer.model.AppDatabase
import com.navaplayer.model.FavoriteDAO
import com.navaplayer.model.ONLINEMP3

class FavoriteRepository(private val favoriteDao: FavoriteDAO) {

    suspend fun getAllFavorites(): List<ONLINEMP3> = favoriteDao.getAll()

    suspend fun addToFavorite(song: ONLINEMP3) = favoriteDao.insert(song)

    suspend fun removeFromFavorite(song: ONLINEMP3) = favoriteDao.delete(song)

    suspend fun isFavorite(songId: String): Boolean = favoriteDao.isFavorite(songId)
}
