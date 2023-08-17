package com.example.rockstore.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rockstore.R
import com.example.rockstore.databinding.ActivityMusicImageBinding

class MusicImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMusicImageBinding
    private lateinit var i: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        i= intent

        binding.imageAlbum1.setOnClickListener{sendID(R.drawable.jeopardy) }
        binding.imageAlbum2.setOnClickListener{sendID(R.drawable.from_the_lions_mouth) }
        binding.imageAlbum3.setOnClickListener{sendID(R.drawable.patti_smith_horses) }
        binding.imageAlbum4.setOnClickListener{sendID(R.drawable.bright_green_field) }
        binding.imageAlbum5.setOnClickListener{sendID(R.drawable.joy_division_unknown_pleasure) }
        binding.imageAlbum6.setOnClickListener{sendID(R.drawable.nick_cave_and_the_bad_seeds_boatman) }
        binding.buttonRemoveImage.setOnClickListener{sendID(R.drawable.default_album_300_g4) }

    }

    private fun sendID(id: Int) {
        i.putExtra("id", id)
        setResult(1, i)
        finish()

    }
}