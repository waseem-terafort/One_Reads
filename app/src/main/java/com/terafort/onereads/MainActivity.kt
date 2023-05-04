package com.terafort.onereads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.terafort.onereads.databinding.ActivityMainBinding

import com.terafort.onereads.fragments.HomeFragment
import com.terafort.onereads.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replacefragment(HomeFragment())
        binding.bottomview.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeFragment->replacefragment(HomeFragment())
                R.id.settingsFragment->replacefragment(SettingsFragment())
                else ->{
                }
            }
            true
        }
    }
    private fun replacefragment(fragment: Fragment){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment)
        fragmentTransaction.commit()
    }

}