package com.ag.videocarouselapp.api

import com.google.gson.annotations.SerializedName

class SongResponseData {

    @SerializedName("songs")
    lateinit var songs: List<SongItem>

}