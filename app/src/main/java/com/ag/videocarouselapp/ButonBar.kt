
package com.ag.videocarouselapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.ag.videocarouselapp.db.MyDatabaseRepository

private const val LOG_TAG = "Song/TopBarFragment"

class ButtonBar: Fragment() {
    private lateinit var postButton: Button
    private lateinit var clearButton: Button

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
        val view = inflater.inflate(R.layout.button_bar, container, false)


        this.postButton = view.findViewById(R.id.new_post)

        this.postButton.setOnClickListener{

            activity?.let{
                val intent = Intent (it, ThirdActivity::class.java)
                it.startActivity(intent)
            }

        }

        this.dbRepo = MyDatabaseRepository(this)
        this.clearButton = view.findViewById(R.id.clear_button)

        this.clearButton.setOnClickListener{
            this.dbRepo.clearListenedSong()
        }
        return view
    }

    companion object {
        fun newInstance(): ButtonBar {
            return ButtonBar()
        }
    }
}