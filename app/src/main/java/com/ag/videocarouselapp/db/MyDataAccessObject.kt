package com.ag.videocarouselapp.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MyDataAccessObject {

    @Query("SELECT song_id FROM ListenedSong")
    fun fetchListenedSongs(): LiveData<List<Int>>

    @Insert
    fun addListenedSong(song_id: ListenedSong)

    @Query("DELETE FROM ListenedSong")
    fun clearListenedSongs()
}