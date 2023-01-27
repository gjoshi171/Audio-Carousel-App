
package com.ag.videocarouselapp

import android.os.Bundle
import android.util.Log


import androidx.appcompat.app.AppCompatActivity
import com.ag.videocarouselapp.db.MyDatabaseRepository

private const val LOG_TAG = "Song/SongViewActivity"

class SongViewActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_song)


        val song = getIntent().getExtras()
        Log.v(LOG_TAG, "Song intent: $song")

        val songViewInfo = this.supportFragmentManager.findFragmentById(R.id.song_info_container)

        if (songViewInfo == null) {
            val frag = SongViewInfoFragment.newInstance()
            frag.arguments = song
            this.supportFragmentManager
                .beginTransaction()
                .add(R.id.song_info_container, frag)
                .commit()
        }

        val songButtons = this.supportFragmentManager.findFragmentById(R.id.song_button_container)

        if (songButtons == null) {
            val frag = SongViewButtons()
            frag.arguments = song
            this.supportFragmentManager
                .beginTransaction()
                .add(R.id.song_button_container, frag)
                .commit()
        }
    }

}

