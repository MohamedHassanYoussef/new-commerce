package com.example.commerceapp.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.commerceapp.MainActivity
import com.example.commerceapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val userName = binding.editTextTextUserName.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()


            val adminName = "Mohamed"
            val adminPassword = "Mohamed@123"

            if (userName.isBlank()) {
                Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show()
            } else if (password.isBlank()) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
            } else if (userName == adminName && password == adminPassword) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                val Intent: Intent = Intent(
                    this,
                    MainActivity::class.java
                )
                startActivity(Intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
