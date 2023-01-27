
package com.ag.videocarouselapp

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders

private const val LOG_TAG = "Song/SongViewButtonsFragment"

class SongViewButtons : Fragment()
{
    private lateinit var songViewModel: SongViewModel
    // is music state still required or is it handled through API?
    // private lateinit var music: MusicState
    private lateinit var fragmentMG: FragmentManager
    private lateinit var songViewActivity: SongViewActivity
    private lateinit var playButton: Button
    private lateinit var stopButton: Button

    private var isPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        songViewActivity = getActivity() as SongViewActivity
        //music = viewsongActivity.textInputModel

        fragmentMG = songViewActivity.supportFragmentManager

        this.songViewModel= ViewModelProviders.of(this.requireActivity()).get(SongViewModel::class.java)
//        this.viewsongModel.songIndex.value = this.loadIndex()

    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        this.music.saveText(this.viewsongModel.getSongIndex().toString())
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.view_song_buttons, container, false)
        this.playButton = view.findViewById(R.id.play_button)

        var audio = ""
        var song = arguments?.getBundle("song")
        if (song != null) {
            var a = song.getString("audio")
            if (a != null) {
                audio = a
            }
        }
        this.songViewModel.prepMediaPlayer(audio)
        this.playButton.setOnClickListener {
            this.isPlaying = this.songViewModel.playPauseAudio()

            if (this.isPlaying) {
                this.playButton.text = getString(R.string.pause_button_text)
            }
            else {
                this.playButton.text = getString(R.string.play_button_text)
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        songViewModel.stopAudio()
    }



}