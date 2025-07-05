package com.navaplayer.model

import com.google.gson.annotations.SerializedName

data class Music(
    @SerializedName("ONLINE_MP3")
    val ONLINE_MP3: List<ONLINEMP3>
)
