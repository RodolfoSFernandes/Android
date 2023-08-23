package com.example.rockstore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rockstore.R
import com.example.rockstore.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBackMain.setOnClickListener {
            finish()
        }
    }
}