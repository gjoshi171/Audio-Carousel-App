
package com.ag.videocarouselapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ag.videocarouselapp.api.SongExecutor
import com.ag.videocarouselapp.api.SongItem
import java.util.concurrent.Executors

const val BASE_URL = "https://quingabe.com/"
private const val LOG_TAG = "VideoListModel"

class VideoListViewModel: ViewModel() {

    val songLiveData: LiveData<List<SongItem>>

    val videos = mutableListOf<VideoPreview>()

    init {
        this.songLiveData = SongExecutor().fetchSongs()
    }

    fun loadSongs() {

        Log.d(LOG_TAG, "$songLiveData")
        var songDeadData = songLiveData.value
        if (songDeadData != null) {
            Log.d(LOG_TAG, "songDeadData size(songs[]): ${songDeadData.size}")
            songDeadData.forEach {
                Log.d(LOG_TAG, "a song: ${it.title}")
                val song_prev = VideoPreview(
                    it.id,
                    it.artist_id,
                    it.artist__name,
                    it.title,
                    it.lyrics,
                    BASE_URL + it.image,
                    BASE_URL + it.audio
                )

                videos += song_prev

            }
        }
    }
}