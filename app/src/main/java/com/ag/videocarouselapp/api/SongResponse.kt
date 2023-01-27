package com.ag.videocarouselapp.api

import com.google.gson.annotations.SerializedName

class SongResponse {
    @SerializedName("data")
    lateinit var data: SongResponseData
}