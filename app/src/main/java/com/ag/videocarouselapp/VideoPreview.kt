package com.ag.videocarouselapp

import java.util.UUID

data class VideoPreview(
    var song_id: Int,
    var artist_id: Int,
    var artist__name: String,
    var title: String,
    var lyrics: String,
    var image: String,
    var audio: String,
    var played: Boolean = false
)