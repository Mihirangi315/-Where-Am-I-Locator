package com.example.whereami

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val rootView = findViewById<android.view.View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ------------------------------------------------------------------
        // TEMPORARY TEST CODE — for UI testing only. Delete once the real
        // FusedLocationProviderClient logic is merged in by your teammate.
        // ------------------------------------------------------------------
        findViewById<Button>(R.id.btnGetLocation).setOnClickListener {
            findViewById<TextView>(R.id.tvLat).text = "6.927100"
            findViewById<TextView>(R.id.tvLng).text = "79.861200"
            findViewById<TextView>(R.id.tvAccuracy).text = "12.5 m"
            findViewById<TextView>(R.id.tvTime).text = "2026-08-19 14:32:10"
        }
        // ------------------------------------------------------------------
    }
}