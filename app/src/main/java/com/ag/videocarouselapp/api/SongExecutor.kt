
package com.ag.videocarouselapp.api

import android.media.Image
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

private const val LOG_TAG = "Song/Api/SongExecutor"
const val BASE_URL = "https://quingabe.com/api/"

class SongExecutor {
    private val api: SongApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        this.api = retrofit.create(SongApi::class.java)
    }

    fun fetchSongs(): LiveData<List<SongItem>> {
        val responseLiveData: MutableLiveData<List<SongItem>> = MutableLiveData()
        val songRequest: Call<SongResponse> = this.api.fetchSongs()

        songRequest.enqueue(object: Callback<SongResponse> {
            override fun onFailure(call: Call<SongResponse>, t: Throwable) {
                Log.e(LOG_TAG, "Failed to reach Song Api")
            }

            override fun onResponse(call: Call<SongResponse>, response: Response<SongResponse>) {
                Log.d(LOG_TAG, "Response received from SongAPI")
                val songResponse: SongResponse? = response.body()
                Log.d(LOG_TAG, "$songResponse")
                val songResponseData: SongResponseData? = songResponse?.data

                var songs: List<SongItem> = songResponseData?.songs ?: mutableListOf()
                Log.d(LOG_TAG, "Songs: ${songs.size}")
                responseLiveData.value = songs
            }
        })

        return responseLiveData
    }
    fun postSong(title: String, artist: Integer, lyrics: String, image: File?, audio: File?): LiveData<Integer> {
        val song_idLiveData: MutableLiveData<Integer> = MutableLiveData(Integer(-1))

        val titleRB = RequestBody.create(MediaType.parse("text/plain"), title)
        val lyricsRB = RequestBody.create(MediaType.parse("text/plain"), title)
        var imageRB: RequestBody? = null
        var audioRB: RequestBody? = null


        // i think this will work for image/audio upload.
        // Comment out if causing issues
        if (image != null) {
            imageRB = RequestBody.create(MediaType.parse("multipart/form-data"), image)
        }
        if (audio != null) {
            audioRB = RequestBody.create(MediaType.parse("multipart/form-data"), audio)
        }

        // post new song (generator)
        val songPostReq: Call<SongPostResponse> = this.api.postSong(titleRB, artist, lyricsRB, imageRB, audioRB)

        Log.d(LOG_TAG, "Enqueue request to post song $title, $artist, $lyrics")
        //actually calling to enqueue it
        songPostReq.enqueue(object:Callback<SongPostResponse> {
            override fun onFailure(call: Call<SongPostResponse>, t: Throwable) {
                Log.e(LOG_TAG, "Failed to post song")

                Log.e(LOG_TAG, "${t.message}")
            }

            override fun onResponse(
                call: Call<SongPostResponse>,
                response: Response<SongPostResponse>
            ) {
                Log.d(LOG_TAG, "Response from posting received")
                val songPostResponse: SongPostResponse? = response.body()
                if (songPostResponse?.status == "success") {
                    val songPostData: SongPostData = songPostResponse.data
                    song_idLiveData.value = songPostData.song_id
                } else {
                    Log.d(LOG_TAG, "Request to post song has failed, ${songPostResponse?.status}")
                }

            }
        })
    return song_idLiveData //should ovserve this and do some actition to go back and refresh 2nd activity
    }
}