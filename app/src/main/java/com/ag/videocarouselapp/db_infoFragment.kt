
package com.ag.videocarouselapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

private const val LOG_TAG = "db_info"

class db_infoFragment: Fragment() {
    private lateinit var counter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(LOG_TAG, "OnCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.db_info, container, false)

        this.counter = view.findViewById(R.id.dbText) as TextView

        return view
    }

    companion object {
        fun newInstance(): db_infoFragment {
            return db_infoFragment()
        }
    }
}