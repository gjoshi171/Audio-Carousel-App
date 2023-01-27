package com.ag.videocarouselapp.api

import com.google.gson.annotations.SerializedName

class SongPostResponse {

    @SerializedName("status")
    lateinit var status: String

    @SerializedName("data")
    lateinit var data: SongPostData

}