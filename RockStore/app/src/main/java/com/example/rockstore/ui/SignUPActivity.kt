package com.example.rockstore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rockstore.database.DBHelper
import com.example.rockstore.databinding.ActivitySignUpactivityBinding

class SignUPActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(this)

        binding.buttonSignup.setOnClickListener{
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()
            val passwordC = binding.editConfirmPassword.text.toString()

            if(username.isNotEmpty() && password.isNotEmpty() && passwordC.isNotEmpty()){
               if (password == passwordC)
               {
                   val res = db.insertUser(username, password)
                   if(res>0) {
                       Toast.makeText(applicationContext, "SignUp ok", Toast.LENGTH_SHORT).show()
                       finish()
                   } else {
                       Toast.makeText(applicationContext, "SignUp error", Toast.LENGTH_SHORT).show()
                       binding.editUsername.setText("")
                       binding.editPassword.setText("")
                       binding.editConfirmPassword.setText("")
                   }
               } else {
                   Toast.makeText(
                       applicationContext,
                       "Passwords don't match ",
                       Toast.LENGTH_SHORT
                   ).show()
                        }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Please insert all required fields ",
                    Toast.LENGTH_SHORT
                ).show()

        }

        }
    }
}