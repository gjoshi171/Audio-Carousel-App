
package com.ag.videocarouselapp.api

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface SongApi {
    @GET("song/{id}/")
    fun fetchSong(
        @Path("id") id: Int? = null
    ): Call<SongResponse>

    @GET("song/")
    fun fetchSongs(
        @Query("title") title: String? = null,
        @Query("artist") artist: Integer? = null,
        @Query("lyrics") lyrics: String? = null,
    ): Call<SongResponse>

    @Multipart
    @POST("song/new/")
    fun postSong(
        @Part("title") title: RequestBody,
        @Part("artist") artist: Integer,
        @Part("lyrics") lyrics: RequestBody,
        @Part("image") image: RequestBody?,
        @Part("audio") audio: RequestBody?

    ): Call<SongPostResponse>
}