package com.ag.videocarouselapp.db

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.ag.videocarouselapp.SongViewInfoFragment
import java.util.concurrent.Executors

private const val LOG_TAG = "Song/db/MyDatabaseRepository"
private const val DATABASE_NAME = "Songdb"

class MyDatabaseRepository constructor(private val fragment: Fragment) {
    private val database: MyDatabase = Room.databaseBuilder(
        fragment.requireContext().applicationContext,
        MyDatabase::class.java,
        DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()

    private val myDao = database.myDao()
    private val executor = Executors.newSingleThreadExecutor()

    var listenedSongs: LiveData<List<Int>> = this.myDao.fetchListenedSongs()

    init {
        this.watchStuff()
    }

    fun getListenedSongs() {
        listenedSongs.value?.let { pid ->
            this.listenedSongs = this.myDao.fetchListenedSongs()
            this.listenedSongs.observe(this.fragment.viewLifecycleOwner) { lsongs ->
                Log.v(LOG_TAG, "Loaded listened songs list: $lsongs")
            }
        }
    }
    fun addListenedSong(song_id: Int) {
        Log.v(LOG_TAG, "Adding song: $song_id to listened list")
        this.executor.execute {
            this.myDao.addListenedSong(ListenedSong(song_id=song_id))
        }
    }
    fun clearListenedSong() {
        Log.v(LOG_TAG, "Clearing all songs listened list")
        this.executor.execute {
            this.myDao.clearListenedSongs()
        }
    }
    private fun watchStuff() {
        Log.v(LOG_TAG, "Watch stuff")

        this.listenedSongs.observe(this.fragment.viewLifecycleOwner) { lsongs ->
            Log.v(LOG_TAG, "Loaded songs $lsongs")
        }
    }
}