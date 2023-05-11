package com.terafort.onereads.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

import androidx.navigation.fragment.findNavController

import com.terafort.onereads.databinding.FragmentSettingsBinding



class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding.PrivacyPolicyRightarrow.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToPrivacyPolicy5()
            findNavController().navigate(action)
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}