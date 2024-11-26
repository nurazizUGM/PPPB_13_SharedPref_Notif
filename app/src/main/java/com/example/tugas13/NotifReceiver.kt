package com.example.tugas13

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotifReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val pref = PrefManager.getInstance(context)
        val msg = intent!!.getStringExtra("MESSAGE")
        if (msg == "LOGOUT") {
            pref.clear()
            Toast.makeText(context, "Logged out successfully!", Toast.LENGTH_SHORT).show()
            context.startActivity(
                Intent(
                    context,
                    MainActivity::class.java
                ).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) })
        }
    }
}