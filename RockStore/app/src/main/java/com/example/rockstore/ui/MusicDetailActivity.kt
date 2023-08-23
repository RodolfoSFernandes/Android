package com.example.rockstore.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.rockstore.model.MusicModel
import com.example.rockstore.R
import com.example.rockstore.database.DBHelper
import com.example.rockstore.databinding.ActivityMusicDetailBinding

class MusicDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMusicDetailBinding
    private lateinit var db: DBHelper
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private var imageId: Int = -1
    private var musicModel = MusicModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i = intent
        val id = i.extras?.getInt("id")
        db = DBHelper(applicationContext)
        if (id != null) {
            musicModel = db.getMusic(id)
            populate()
        } else {
            finish()
        }

        binding.buttonBack.setOnClickListener {
            setResult(0, i)
            finish()
        }

        binding.buttonEdit.setOnClickListener {
            binding.layoutEditDelete.visibility = View.VISIBLE
            binding.layoutEdit.visibility = View.GONE
            changeEditText(true)
        }

        binding.buttonCancel.setOnClickListener {
            binding.layoutEditDelete.visibility = View.GONE
            binding.layoutEdit.visibility = View.VISIBLE
            populate()
            changeEditText(false)
        }
        binding.buttonSave.setOnClickListener {
            val res = db.updatemusic(
                id = musicModel.id,
                name = binding.editName.text.toString(),
                album = binding.editAlbum.text.toString(),
                genre = binding.editGenre.text.toString(),
                year = binding.editYear.text.toString().toInt(),
                sampleName = binding.editSampleName.toString(),
                sampleSong = binding.editSampleSong.toString().toInt(),
                infoAlbum = binding.editInfoAlbum.toString(),
                imageId = imageId

            )

            if (res > 0) {
                Toast.makeText(applicationContext, "Update OK", Toast.LENGTH_SHORT).show()
                setResult(1, i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Update ERROR", Toast.LENGTH_SHORT).show()
                setResult(0, i)
                finish()
            }
        }
        binding.buttonDelete.setOnClickListener {
            val res = db.deletemusic(musicModel.id)
            if (res > 0) {
                Toast.makeText(applicationContext, "Delete OK", Toast.LENGTH_SHORT).show()
                setResult(1, i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Delete ERROR", Toast.LENGTH_SHORT).show()
                setResult(0, i)
                finish()
            }
        }

        binding.imageMusic.setOnClickListener {
            if (binding.editName.isEnabled) {
                launcher.launch(
                    Intent(
                        applicationContext,
                        MusicImageActivity::class.java
                    )
                )
            }

        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null && it.resultCode == 1) {
                if (it.data?.extras != null) {
                    imageId = it.data?.getIntExtra("id", 0)!!
                    binding.imageMusic.setImageResource(imageId!!)
                }

            } else {
                imageId = -1
                binding.imageMusic.setImageResource(R.drawable.default_album_300_g4)
            }
        }
    }

    private fun changeEditText(status: Boolean) {
        binding.editName.isEnabled = status
        binding.editAlbum.isEnabled = status
        binding.editGenre.isEnabled = status
        binding.editYear.isEnabled = status
        binding.editSampleName.isEnabled = status
        binding.editSampleSong.isEnabled = status
        binding.editInfoAlbum.isEnabled = status
    }


    private fun populate() {
        binding.editName.setText(musicModel.name)
        binding.editAlbum.setText(musicModel.album)
        binding.editGenre.setText(musicModel.genre)
        binding.editYear.setText(musicModel.year.toString())
        binding.editSampleName.setText(musicModel.sampleName)
        binding.editSampleSong.setText(musicModel.sampleSong)
        binding.editInfoAlbum.setText(musicModel.infoAlbum)
        if (musicModel.imageId > 0) {
            binding.imageMusic.setImageResource(musicModel.imageId)
        } else {
            binding.imageMusic.setImageResource(R.drawable.default_album_300_g4)
        }
    }
}
