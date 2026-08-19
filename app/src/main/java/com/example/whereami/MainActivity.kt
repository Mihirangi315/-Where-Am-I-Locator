package com.example.whereami

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var cancellationTokenSource = CancellationTokenSource()
    private lateinit var tvLatitude: TextView
    private lateinit var tvLongitude: TextView
    private lateinit var tvAccuracy: TextView
    private lateinit var tvTimestamp: TextView
    private lateinit var tvStatus: TextView

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) {
            getLocation()
        } else {
            tvStatus.text = getString(R.string.status_permission_denied)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Preserve existing padding while adding system bars insets
            val leftPadding = systemBars.left + (16 * resources.displayMetrics.density).toInt()
            val topPadding = systemBars.top + (16 * resources.displayMetrics.density).toInt()
            val rightPadding = systemBars.right + (16 * resources.displayMetrics.density).toInt()
            val bottomPadding = systemBars.bottom + (16 * resources.displayMetrics.density).toInt()
            v.setPadding(leftPadding, topPadding, rightPadding, bottomPadding)
            insets
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        tvLatitude = findViewById(R.id.tvLatitude)
        tvLongitude = findViewById(R.id.tvLongitude)
        tvAccuracy = findViewById(R.id.tvAccuracy)
        tvTimestamp = findViewById(R.id.tvTimestamp)
        tvStatus = findViewById(R.id.tvStatus)

        findViewById<Button>(R.id.btnGetLocation).setOnClickListener {
            checkPermissionsAndGetLocation()
        }
    }

    private fun checkPermissionsAndGetLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            getLocation()
        }
    }

    private fun getLocation() {
        tvStatus.text = getString(R.string.status_loading)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        // Cancel any previous request
        cancellationTokenSource.cancel()
        cancellationTokenSource = CancellationTokenSource()

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.token)
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    displayLocation(location)
                    tvStatus.text = getString(R.string.status_ready)
                } else {
                    tvStatus.text = getString(R.string.status_location_unavailable)
                }
            }
            .addOnFailureListener {
                tvStatus.text = getString(R.string.status_error)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancellationTokenSource.cancel()
    }

    private fun displayLocation(location: Location) {
        tvLatitude.text = getString(R.string.label_latitude, location.latitude.toString())
        tvLongitude.text = getString(R.string.label_longitude, location.longitude.toString())
        tvAccuracy.text = getString(R.string.label_accuracy, "${location.accuracy}m")

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date(location.time)
        tvTimestamp.text = getString(R.string.label_timestamp, sdf.format(date))
    }
}
