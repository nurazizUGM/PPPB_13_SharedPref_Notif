package com.example.tugas13

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tugas13.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity, android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request the POST_NOTIFICATIONS permission
                ActivityCompat.requestPermissions(
                    this@MainActivity, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1
                )
            }
        }

        if (prefManager.getIsLoggedIn()) {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        with(binding) {
            btnLogin.setOnClickListener {
                val u = prefManager.getUsername()
                val p = prefManager.getPassword()
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()

                if (username.equals(u) && password.equals(p)) {
                    Toast.makeText(this@MainActivity, "Logged in successfully!", Toast.LENGTH_SHORT)
                        .show()
                    prefManager.setIsLoggedIn(true)
                    startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                } else {
                    Toast.makeText(this@MainActivity, "Invalid credentials!", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            txtRegister.setOnClickListener {
                startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.etPassword.setText("")
    }
}