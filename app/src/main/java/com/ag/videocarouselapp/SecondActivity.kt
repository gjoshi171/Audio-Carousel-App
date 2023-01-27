
package com.ag.videocarouselapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ag.videocarouselapp.VideoListFragment.Companion.newInstance
import com.google.android.gms.auth.api.signin.GoogleSignIn

private const val LOG_TAG = "Song/Second Activity"
const val UPLOAD_SONG = 97

class SecondActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            Log.d(LOG_TAG, "Second Activity OnCreate started")
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_second)

            val buttonBar =
                this.supportFragmentManager.findFragmentById(R.id.search_y_account_layout)

            if (buttonBar == null) {
                val frag = ButtonBar.newInstance()
                this.supportFragmentManager
                    .beginTransaction()
                    .add(R.id.search_y_account_layout, frag)
                    .commit()
            }

            val videoFeedFragment =
                this.supportFragmentManager.findFragmentById(R.id.video_feed_layout)

            if (videoFeedFragment == null) {
                val frag = VideoListFragment.newInstance()
                this.supportFragmentManager
                    .beginTransaction()
                    .add(R.id.video_feed_layout, frag)
                    .commit()
            }

            val dbInfoFrag = this.supportFragmentManager.findFragmentById(R.id.db_info)

            if (dbInfoFrag == null) {
                val frag = db_infoFragment.newInstance()
                this.supportFragmentManager
                    .beginTransaction()
                    .add(R.id.db_info, frag)
                    .commit()
            }
        }
}