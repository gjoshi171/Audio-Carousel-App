
package com.ag.videocarouselapp

import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ag.videocarouselapp.api.SongItem
import com.ag.videocarouselapp.db.MyDatabaseRepository
import com.squareup.picasso.Picasso
import java.io.ObjectStreamException

private const val LOG_TAG = "Song/VideoListFragment"

class VideoListFragment: Fragment() {
    private lateinit var videoRecyclerView: RecyclerView
    private var adapter: VideoAdapter? = null
    private var listenedSongs: List<Int> = listOf()

    private lateinit var dbRepo: MyDatabaseRepository


    private val myVideoListViewModel: VideoListViewModel by lazy {
        ViewModelProviders.of(this.requireActivity()).get(VideoListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(LOG_TAG, "onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.myVideoListViewModel.songLiveData.observe(
            this.viewLifecycleOwner,
            Observer { songlist ->
                Log.d(LOG_TAG, "Song list got size(songs[]): ${songlist.size}")
                this.myVideoListViewModel.loadSongs()
                this.updateUI()
            }
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.video_feed_fragment, container, false)

        this.videoRecyclerView = view.findViewById(R.id.video_recycler_view) as RecyclerView
        this.videoRecyclerView.layoutManager = LinearLayoutManager(context)
        this.dbRepo = MyDatabaseRepository(this)
        this.dbRepo.getListenedSongs()
        this.setupObservers()
        this.updateUI()

        return view
    }

    private fun updateUI() {
        val videos = myVideoListViewModel.videos
        adapter = VideoAdapter(videos, listenedSongs)
        this.videoRecyclerView.adapter = adapter
    }

    private fun setupObservers() {
        this.dbRepo.listenedSongs.observe(this.viewLifecycleOwner) { lsongs ->
             this.listenedSongs = lsongs
            updateUI()
            }
        }

    companion object {
        fun newInstance(): VideoListFragment {
            return VideoListFragment()
        }
    }

    private inner class VideoHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        private lateinit var vid: VideoPreview
        val imageView: ImageButton = this.itemView.findViewById(R.id.video_image)
        val titleTextView: TextView = this.itemView.findViewById(R.id.video_title)
        val lyricsTextView: TextView = this.itemView.findViewById(R.id.video_description)
        val artistTextView: TextView = this.itemView.findViewById(R.id.video_author)

        fun bind(vid: VideoPreview,listenedSongs: List<Int>) {
            this.vid = vid
            titleTextView.text = vid.title
            lyricsTextView.text = vid.lyrics.take(20)
            artistTextView.text = vid.artist__name

            Log.v(LOG_TAG, "Song_id ${vid.song_id}")
            Log.v(LOG_TAG, "Listened list: $listenedSongs")
            if (listenedSongs.contains(vid.song_id)) {
                Log.v(LOG_TAG, "Song_Id: ${vid.song_id} is in $listenedSongs")
                imageView.setBackgroundColor(Color.argb(200,0,100,0))
                imageView.setColorFilter(Color.argb(200,0,100,0))
            }
            else {
                Log.v(LOG_TAG, "Song_Id: ${vid.song_id} is NOT in $listenedSongs")
            }

            Log.v(LOG_TAG, "ImgSlideFrag.updateToCurrentImage image: ${vid.image}")
            if (vid.image != null) {
                Picasso.get()
                    .load(vid.image)
                    .into(imageView)
                Log.v(LOG_TAG, "ImgSlideFrag.updateToCurrentImage passed")
            }
            imageView.setOnClickListener {
                activity?.let{
                    val intent = Intent (it, SongViewActivity::class.java)
                    var bundle = Bundle()
                    bundle.putInt("song_id", vid.song_id)
                    bundle.putString("title", vid.title)
                    bundle.putString("lyrics", vid.lyrics)
                    bundle.putString("artist__name", vid.artist__name)
                    bundle.putInt("artist_id", vid.artist_id)
                    bundle.putString("image", vid.image)
                    bundle.putString("audio", vid.audio)
                    intent.putExtra("song",bundle)
                    it.startActivity(intent)
                }

            }
        }

    }

    private inner class VideoAdapter(var videos: List<VideoPreview>, listenedSongs: List<Int>)
        : RecyclerView.Adapter<VideoHolder>()
    {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
            val view = layoutInflater.inflate(R.layout.video_preview_fragment, parent, false)
            return VideoHolder(view)
        }

        override fun onBindViewHolder(holder: VideoHolder, position: Int) {
            val vid = this.videos[position]
            holder.bind(vid, listenedSongs)
        }

        override fun getItemCount() = videos.size
    }
}