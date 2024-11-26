package com.example.tugas13

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tugas13.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val prefManager by lazy { PrefManager.getInstance(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            txtLogin.setOnClickListener {
                finish()
            }

            btnRegister.setOnClickListener {
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                if (username.equals("")) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Username is required!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (password.equals("")) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Password is required!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    prefManager.setUsername(username)
                    prefManager.setPassword(password)
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration successful!",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }
    }
}