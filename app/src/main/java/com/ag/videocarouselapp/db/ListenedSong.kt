package com.ag.videocarouselapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "ListenedSong")
data class ListenedSong (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "song_id")
    var song_id: Int
)