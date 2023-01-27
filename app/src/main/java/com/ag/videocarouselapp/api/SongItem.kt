package com.ag.videocarouselapp.api

import java.time.Duration

data class SongItem (
    var id: Int = 0,
    var artist_id: Int = 0,
    var artist__name: String = "",
    var title: String = "",
    var lyrics: String = "",
    var image: String = "",
    var audio: String = ""
)