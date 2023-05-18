package com.terafort.onereads

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.terafort.onereads.databinding.ActivityMainBinding
import androidx.navigation.fragment.NavHostFragment
import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val REQUEST_PERMISSIONS_CODE = 123

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Find the NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomview.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.settingsFragment -> {
                    navController.navigate(R.id.settingsFragment)
                    true
                }
                else -> false
            }
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.privacyPolicy) {
                binding.bottomview.visibility = View.GONE
            } else {
                binding.bottomview.visibility = View.VISIBLE
            }
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.documentsFragment) {
                binding.bottomview.visibility = View.GONE
            } else {
                binding.bottomview.visibility = View.VISIBLE
            }
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.MANAGE_EXTERNAL_STORAGE),
                REQUEST_PERMISSIONS_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied
            }
        }
    }
}
