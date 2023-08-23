package com.example.rockstore.adapter.listener

import com.example.rockstore.model.MusicModel

class MusicOnClickListener (val clickListener: (music: MusicModel) -> Unit){
    fun onClick (music: MusicModel) = clickListener


}
