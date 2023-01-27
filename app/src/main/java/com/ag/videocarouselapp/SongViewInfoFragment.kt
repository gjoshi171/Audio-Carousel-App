
package com.ag.videocarouselapp

import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ag.videocarouselapp.db.MyDatabaseRepository
import com.squareup.picasso.Picasso


private const val LOG_TAG = "Song/SongViewInfoFragment"

class SongViewInfoFragment: Fragment() {
    private lateinit var song_id: Integer
    private lateinit var title: String
    private lateinit var lyrics: String
    private lateinit var artist__name: String
    //private lateinit var artist_id: Integer
    private lateinit var image: String
    private lateinit var audio: String

    private lateinit var titleView: TextView
    private lateinit var lyricsView: TextView
    private lateinit var artistView: TextView
    private lateinit var imageView: ImageView

    private lateinit var dbRepo: MyDatabaseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.v(LOG_TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.view_song_info, container, false)

        this.dbRepo = MyDatabaseRepository(this)

        title = "Error, missing title."
        artist__name = "Error, Missing artist name"
        lyrics = ""
        image = "https://quingabe.com/cs411/images/01%20-%20whats%20going%20on.png"
        audio = "https://quingabe.com/cs411/audio/01%20-%20Mercy%20Mercy%20Me%20%28The%20Ecology%29-.mp3"

        var song = arguments?.getBundle("song")
        if (song != null) {
            var s = Integer(song.getInt("song_id"))
            if (s != null) {
                song_id = s
            }
            var t = song.getString("title")
            if (t != null) {
                title = t
            }
            var a = song.getString("artist__name")
            if (a != null) {
                artist__name = a
            }
            var l = song.getString("lyrics")
            if (l != null) {
                lyrics = l
            }
            var i = song.getString("image")
            if (i != null) {
                image = i
            }
            var au = song.getString("audio")
            if (au != null) {
                audio = au
            }
        }

        this.dbRepo.addListenedSong(song_id.toInt())
        this.imageView = view.findViewById(R.id.song_image)

        if (image != null) {
            Picasso.get()
                .load(image)
                .into(this.imageView)
            Log.v(LOG_TAG, "ImgSlideFrag.updateToCurrentImage passed")
        }

        this.titleView = view.findViewById(R.id.song_title)
        if (title != null) {
            titleView.text = title
        }
        if (lyrics != null) {
            this.lyricsView = view.findViewById(R.id.song_lyrics)
            lyricsView.text = lyrics
        }
        if (artist__name != null) {
            this.artistView = view.findViewById(R.id.song_artist)
            artistView.text = artist__name
        }
        return view
    }

    companion object {
        fun newInstance(): SongViewInfoFragment {
            return SongViewInfoFragment()
        }
    }
}
