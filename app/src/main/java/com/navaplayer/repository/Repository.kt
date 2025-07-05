package com.navaplayer.repository

import com.navaplayer.model.Artist
import com.navaplayer.model.Music
import com.navaplayer.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {

    suspend fun getLatestMusic() : Music {
        return withContext(Dispatchers.IO){
            RetrofitInstance.api.getAllSongs()
        }
    }

    suspend fun getHotArtists() : Artist{
        return withContext(Dispatchers.IO){
            RetrofitInstance.api.artistList()
        }
    }

    suspend fun searchSongs(query: String): Music {
        return withContext(Dispatchers.IO) {
            RetrofitInstance.api.searchSongs(query)
        }
    }

}