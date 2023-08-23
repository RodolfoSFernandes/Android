package com.example.rockstore.adapter.viewHolder

import android.media.MediaPlayer
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rockstore.R
import com.example.rockstore.model.MusicModel

class MusicViewHolder(view: View): RecyclerView.ViewHolder(view) {

    var mediaPlayer: MediaPlayer? = null

    val image: ImageView = view.findViewById(R.id.image_music)
    val textName: TextView = view.findViewById(R.id.text_music_name)
    val textAlbum: TextView = view.findViewById(R.id.text_music_album)
    val textYear: TextView = view.findViewById(R.id.text_music_year)
    val textSampleName: TextView = view.findViewById(R.id.text_sample_name)

    val playButton: ImageView = view.findViewById(R.id.img_play)
    val stopButton: ImageView = view.findViewById(R.id.img_stop)

}