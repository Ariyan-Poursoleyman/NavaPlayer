package com.navaplayer.model

import androidx.room.*

@Dao
interface FavoriteDAO {
    @Query("SELECT * FROM favorites_tbl")
    suspend fun getAll(): List<ONLINEMP3>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(song: ONLINEMP3)

    @Delete
    suspend fun delete(song: ONLINEMP3)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites_tbl WHERE id = :songId)")
    suspend fun isFavorite(songId: String): Boolean
}
