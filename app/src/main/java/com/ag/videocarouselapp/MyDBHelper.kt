package com.ag.videocarouselapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

 class MyDBHelper(context: Context): SQLiteOpenHelper(context, "UploadedDataDB", null, 1){
     override fun onCreate(db: SQLiteDatabase?) {
      db?.execSQL("CREATE TABLE UploadedData(songId INTEGER PRIMARY KEY AUTOINCREMENT, songTitle TEXT)")
      db?.execSQL("CREATE TABLE ListenedSongs(id INTEGER PRIMARY KEY, songId INTEGER, listened BIT)")
     }

     override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
         TODO("Not yet implemented")
     }
}