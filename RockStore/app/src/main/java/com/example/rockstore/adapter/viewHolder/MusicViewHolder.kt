package com.example.rockstore.adapter.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rockstore.R

class MusicViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val image: ImageView = view.findViewById(R.id.image_music)
    val textName: TextView = view.findViewById(R.id.text_music_name)
}