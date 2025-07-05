package com.navaplayer.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorites_tbl")
data class ONLINEMP3(
    val cat_id: String,
    val category_image: String,
    val category_image_thumb: String,
    val category_name: String,
    val cid: String,
    @PrimaryKey
    val id: String,
    val mp3_artist: String,
    val mp3_description: String,
    val mp3_duration: String,
    val mp3_thumbnail_b: String,
    val mp3_thumbnail_s: String,
    val mp3_title: String,
    val mp3_type: String,
    val mp3_url: String,
    val rate_avg: String,
    val total_download: String,
    val total_rate: String,
    val total_views: String
) : Parcelable
