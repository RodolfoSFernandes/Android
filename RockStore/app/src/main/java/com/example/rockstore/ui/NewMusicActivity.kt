package com.example.rockstore.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.rockstore.R
import com.example.rockstore.database.DBHelper
import com.example.rockstore.databinding.ActivityNewMusicBinding

class NewMusicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewMusicBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var getContent: ActivityResultLauncher<String>
    private var id: Int? = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(applicationContext)
        val i = intent

        // Initialize the getContent launcher for selecting an MP3 file
        getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.editSampleSong.setText(uri.toString())
            }
        }

        binding.buttonSave.setOnClickListener {

            val sampleSongUriString = binding.editSampleSong.text.toString()
            val sampleSongUri = Uri.parse(sampleSongUriString)
            val sampleSongPath = sampleSongUri.path

            if (sampleSongPath != null && sampleSongPath.endsWith(".mp3")) {
                // The selected file is an MP3, process it
                // Update your database accordingly
            } else {
                // Invalid file selected, show a message to the user
                Toast.makeText(this, "Please select a valid MP3 file.", Toast.LENGTH_SHORT).show()
            }


            val name = binding.editName.text.toString()
            val album = binding.editAlbum.text.toString()
            val genre = binding.editGenre.text.toString()
            val year = binding.editYear.text.toString()
            val sampleName = binding.editSampleName.text.toString()
            val sampleSong = binding.editSampleSong.text.toString().toInt()
            val infoAlbum = binding.editInfoAlbum.text.toString()
            var imageId = -1
            if (id != null) {
                imageId = id as Int
            }

            if (name.isNotEmpty() && album.isNotEmpty() && genre.isNotEmpty() && year.isNotEmpty() && sampleName.isNotEmpty() && infoAlbum.isNotEmpty()) {
                val res = db.insertMusic(name, album, genre, year, sampleName, sampleSong, infoAlbum, imageId)
                if (res > 0) {
                    Toast.makeText(applicationContext, "Insert OK", Toast.LENGTH_SHORT).show()
                    setResult(1, i)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }


        binding.buttonSelectSong.setOnClickListener {
            // Launch the content picker to select an MP3 file
            getContent.launch("audio/*")
        }



        binding.buttonCancel.setOnClickListener {
            setResult(0, i)
            finish()
        }

        binding.imageMusic.setOnClickListener {
            launcher.launch(Intent(applicationContext, MusicImageActivity::class.java))

        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null && it.resultCode == 1) {
                id = it.data?.extras?.getInt("id")
                binding.imageMusic.setImageResource(id!!)
            } else {
                id = -1
                binding.imageMusic.setImageResource(R.drawable.default_album_300_g4)
            }
        }
    }
}