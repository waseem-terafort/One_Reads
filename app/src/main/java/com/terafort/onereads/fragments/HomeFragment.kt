package com.terafort.onereads.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.terafort.onereads.adaptermain.AdapterHomeMain
import com.terafort.onereads.databinding.FragmentHomeBinding
import com.terafort.onereads.model.HomeViewmodel
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewmodel
    private lateinit var adapter: AdapterHomeMain

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setupViewModel()
        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = AdapterHomeMain(emptyList())
        binding.recyclerviewhome.adapter = adapter
        binding.recyclerviewhome.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewhome.setHasFixedSize(true)
    }

    private fun setupViewModel() {
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(this, factory).get(HomeViewmodel::class.java)
        viewModel.myDataList.observe(viewLifecycleOwner) { data ->
            adapter.setDataList(data)
        }
    }
}