package com.example.rockstore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rockstore.model.MusicModel
import com.example.rockstore.R
import com.example.rockstore.adapter.viewHolder.MusicViewHolder
import com.example.rockstore.adapter.listener.MusicOnClickListener

class MusicListAdapter(
    private val musicList: List<MusicModel>,
    private val musicOnClickListener: MusicOnClickListener
) : RecyclerView.Adapter<MusicViewHolder>(){
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
        if (music.imageId>0){
            holder.image.setImageResource(music.imageId)
        } else{
            holder.image.setImageResource(R.drawable.default_album_300_g4)
        }
        holder.itemView.setOnClickListener{
            musicOnClickListener.clickListener(music)
        }
    }
}