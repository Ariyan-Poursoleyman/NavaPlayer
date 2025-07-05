package com.navaplayer.network

import com.navaplayer.model.Artist
import com.navaplayer.model.Music
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService  {

    @GET("api.php?latest")
    suspend fun getAllSongs(): Music

    @GET("api.php?artist_list")
    suspend fun artistList(): Artist

    @GET("api.php")
    suspend fun searchSongs(@Query("search_text") query: String): Music

}