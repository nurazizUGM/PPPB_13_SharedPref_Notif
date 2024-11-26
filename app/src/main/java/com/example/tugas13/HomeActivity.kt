package com.example.tugas13

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tugas13.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val pref by lazy { PrefManager.getInstance(this) }
    private val channelId = "LOGOUT_NOTIF"
    private val notifId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogout.setOnClickListener {
            pref.clear()
            Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show()
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                ).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
            )
        }

        val receiverIntent = Intent(this, NotifReceiver::class.java).putExtra("MESSAGE", "LOGOUT")
        val receiver = PendingIntent
            .getBroadcast(this, 0, receiverIntent, PendingIntent.FLAG_IMMUTABLE)
        val mainIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.baseline_notifications_none_24)
            .setContentTitle("Logout?")
            .setContentText("Are you want to logout?")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(0, "logout", receiver)
            .setContentIntent(mainIntent)

        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notifChannel = NotificationChannel(
                channelId,
                "My Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notifManager.createNotificationChannel(notifChannel)
            notifManager.notify(notifId, builder.build())
        } else {
            notifManager.notify(notifId, builder.build())
        }
    }
}