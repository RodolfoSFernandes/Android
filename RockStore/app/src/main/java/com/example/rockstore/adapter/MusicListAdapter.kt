package com.example.rockstore.adapter

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.rockstore.model.MusicModel
import com.example.rockstore.R
import com.example.rockstore.adapter.viewHolder.MusicViewHolder
import com.example.rockstore.adapter.listener.MusicOnClickListener

class MusicListAdapter(
    private val musicList: List<MusicModel>,
    private val musicOnClickListener: MusicOnClickListener,
) : RecyclerView.Adapter<MusicViewHolder>() {


    private var mediaPlayer: MediaPlayer? = null

    private fun initializeMediaPlayer(context: Context, music: MusicModel) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, music.sampleSong)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_music, parent, false)
        return MusicViewHolder(view)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val music = musicList[position]
        holder.textName.text = music.name
        holder.textAlbum.text = music.album
        holder.textYear.text = music.year.toString()
        holder.textSampleName.text = music.sampleName

        if (music.imageId > 0) {
            holder.image.setImageResource(music.imageId)
        } else {
            holder.image.setImageResource(R.drawable.default_album_300_g4)
        }

        holder.mediaPlayer?.release()
        holder.mediaPlayer = MediaPlayer.create(holder.itemView.context, music.sampleSong)

        holder.playButton.setOnClickListener {
            holder.mediaPlayer?.start()

            holder.playButton.setColorFilter(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.green_olive
                )
            )
        }

        holder.stopButton.setOnClickListener {
            holder.mediaPlayer?.pause()
            holder.mediaPlayer?.seekTo(0)


            holder.playButton.setColorFilter(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.red_album
                )
            )
        }


        holder.itemView.setOnClickListener {
            musicOnClickListener.clickListener(music)
        }
    }


    override fun onViewRecycled(holder: MusicViewHolder) {
        holder.mediaPlayer?.release()
        holder.mediaPlayer = null
        super.onViewRecycled(holder)
    }


}