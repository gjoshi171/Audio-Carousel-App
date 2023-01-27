
package com.ag.videocarouselapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import com.ag.videocarouselapp.api.SongExecutor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

private const val LOG_TAG = "Song/UploadAudioFragment"
class UploadAudioFragement : Fragment() {
    private lateinit var titleText: TextView
    private lateinit var lyricsText: TextView
    private lateinit var audioText: TextView
    private lateinit var imageText: TextView
    private lateinit var enterLyrics: EditText
    private lateinit var enterTitle: EditText
    private lateinit var chooseAudioButton: Button
    private lateinit var chooseImageButton: Button
    private lateinit var saveButton: Button
    private lateinit var clearButton: Button
    private lateinit var submit: Button

    lateinit var userPref: UserPrefs
    var title = ""
    var lyrics = ""
    var upload = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        userPref = UserPrefs(requireActivity())
        observeData()
    }

//    override fun onSaveInstanceState(outState: Bundle): View? {
//        super.onSaveInstanceState(outState)
//
//        enterLyrics = outState.findViewById(R.id.enter_lyrics)
//        enterTitle = view.findViewById(R.id.enter_title)
//
//        val lyrics = enterLyrics.text.toString()
//        val title = enterTitle.text.toString()
//
//        GlobalScope.launch {
//            userPref.storeUser(title, lyrics)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_upload_audio_fragement,container,false)

        this.enterLyrics = view.findViewById(R.id.enter_lyrics)
        this.enterTitle = view.findViewById(R.id.enter_title)
        this.chooseAudioButton = view.findViewById(R.id.choose_audio_button)
        this.chooseImageButton = view.findViewById(R.id.choose_Image_Button)
        this.saveButton = view.findViewById(R.id.saveText)
        this.clearButton = view.findViewById(R.id.clearText)
        this.submit = view.findViewById(R.id.submit_button)


        this.chooseImageButton.setOnClickListener {

            val intent = Intent()
                .setType("image/*")
                .setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
        }

        this.chooseAudioButton.setOnClickListener {

            val intent = Intent()
                .setType("audio/*")
                .setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
        }

        this.saveButton.setOnClickListener {
            val title: String = this.enterTitle.text.toString()
            val lyrics: String = this.enterLyrics.text.toString()

            GlobalScope.launch {
                userPref.storeUser(title, lyrics)
            }
        }

        this.clearButton.setOnClickListener {
            GlobalScope.launch {
                userPref.storeUser("", "")
            }
        }

        this.submit.setOnClickListener{
            /*
            var helper = MyDBHelper(ThirdActivity().applicationContext)
            var db = helper.readableDatabase
            var cv = ContentValues()
            cv.put("songTitle",enterTitle.text.toString())
            db.insert("UploadedData", null, cv )

             */

            upload += 1

            val title: String = this.enterTitle.text.toString()
            val lyrics: String = this.enterLyrics.text.toString()
            val artist: Integer = Integer(2)
            val image: File? = null
            val audio: File? = null
            Log.d(LOG_TAG, "Trying to post song with: $title, $artist")
            if (title != null) {
                if (lyrics != null) {

                    SongExecutor().postSong(title, artist, lyrics, image, audio)
                    activity?.let {
                        val intent = Intent(it, SecondActivity::class.java)
                        it.startActivity(intent)
                    }
                }
            }
        }

        return view
    }

    private fun observeData() {
        this.userPref.userTitleFlow.asLiveData().observe(this) {
            title = it
            enterTitle.setText(title)
        }

        this.userPref.userLyricsFlow.asLiveData().observe(this) {
            lyrics = it
            enterLyrics.setText(lyrics)
        }
    }
}