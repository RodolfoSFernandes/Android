package com.example.rockstore.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rockstore.model.MusicModel
import com.example.rockstore.R
import com.example.rockstore.adapter.MusicListAdapter
import com.example.rockstore.adapter.listener.MusicOnClickListener
import com.example.rockstore.database.DBHelper
import com.example.rockstore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var musicList: List<MusicModel>
    private lateinit var adapter: MusicListAdapter
    private lateinit var result: ActivityResultLauncher<Intent>
    private lateinit var dbHelper: DBHelper
    private var ascDesc: Boolean = true

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)
        val sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)

        binding.recyclerViewMusic.layoutManager = LinearLayoutManager(applicationContext)
        loadList()


        binding.buttonLogout.setOnClickListener {

            mediaPlayer?.stop()

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("username", "")
            editor.apply()
            finish()
        }

        binding.buttonAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }


        /*binding.listViewMusic.setOnItemClickListener { _, _, position, _ ->
            /*Toast.makeText(applicationContext, musicList[position].name, Toast.LENGTH_SHORT ).show()*/
            val intent = Intent(applicationContext, MusicDetailActivity::class.java)
            intent.putExtra("id", musicList[position].id)
            //startActivity(intent)
            result.launch(intent)
        }*/

        binding.buttonAdd.setOnClickListener {
            result.launch(Intent(applicationContext, NewMusicActivity::class.java))
        }

        binding.buttonOrder.setOnClickListener {
            if (ascDesc) {
                binding.buttonOrder.setImageResource(R.drawable.baseline_arrow_upward_24)
            } else {
                binding.buttonOrder.setImageResource(R.drawable.baseline_arrow_downward_24)
            }
            ascDesc = !ascDesc
            musicList = musicList.reversed()
            placeAdapter()
        }

        result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null && it.resultCode == 1) {
                loadList()

            } else if (it.data != null && it.resultCode == 0) {
                Toast.makeText(applicationContext, "Operation canceled", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun placeAdapter() {
        adapter = MusicListAdapter(musicList, MusicOnClickListener { music ->
            val intent = Intent(applicationContext, MusicDetailActivity::class.java)
            intent.putExtra("id", music.id)
            result.launch(intent)

        })

        binding.recyclerViewMusic.adapter = adapter
    }

    private fun loadList() {
        musicList = dbHelper.getAllMusic().sortedWith(compareBy { it.name })
        placeAdapter()

        /* musicList = dbHelper.getAllMusic()

        adapter =
            ArrayAdapter(
                applicationContext,
                android.R.layout.simple_list_item_1,
                musicList
            )

        binding.listViewMusic.adapter=adapter */
    }

}