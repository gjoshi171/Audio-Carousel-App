package com.ag.videocarouselapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ListenedSong::class],
    version = 4
)
abstract class MyDatabase: RoomDatabase() {
    abstract fun myDao(): MyDataAccessObject
}
