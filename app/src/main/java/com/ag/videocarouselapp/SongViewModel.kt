
package com.ag.videocarouselapp

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ag.videocarouselapp.api.SongExecutor
import com.ag.videocarouselapp.api.SongItem
import java.util.*

private const val LOG_TAG = "Song/SongViewModel"

class SongViewModel: ViewModel() {

    private lateinit var mediaPlayer: MediaPlayer
    private var playerPrepped = false
    private var playing = false

    fun prepMediaPlayer(audio: String) {
        Log.v(LOG_TAG, "Prepping media player")
        var audioUrl = audio
        this.mediaPlayer = MediaPlayer()
        this.mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build())
        this.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
            this.mediaPlayer.setDataSource(audioUrl)
            this.mediaPlayer.prepare()
            playerPrepped = true
        } catch (e: Exception) {
            e.printStackTrace()
            playerPrepped = false
        }
    }

    fun playPauseAudio(): Boolean {
        if (!playerPrepped) {
            Log.e(LOG_TAG, "Play attempted without loading a song")
            return false
        }

        Log.v(LOG_TAG, "playing flag: ${this.playing}")
        if (!playing) {
            Log.v(LOG_TAG, "Play/Resume song")
            this.mediaPlayer.start()
            playing = true;
            return playing
        }
        else {
            Log.v(LOG_TAG, "Pause song")
            this.mediaPlayer.pause();
            playing = false;
            return false // paused
        }
    }

    fun stopAudio() {
        if (this.playerPrepped) {
            Log.v(LOG_TAG, "Stop Song")
            this.mediaPlayer.release()
            this.playerPrepped = false
            playing = false
        }
    }
}