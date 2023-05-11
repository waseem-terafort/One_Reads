package com.terafort.onereads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.terafort.onereads.databinding.ActivityMainBinding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.terafort.onereads.fragments.HomeFragment
import com.terafort.onereads.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

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
    }


//    private fun replacefragment(fragment: Fragment){
//        val fragmentManager=supportFragmentManager
//        val fragmentTransaction=fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fragmentContainerView,fragment)
//        fragmentTransaction.commit()
//    }

}