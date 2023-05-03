package com.terafort.onereads.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.terafort.onereads.adaptermain.AdapterHomeMain
import com.terafort.onereads.data.HomeData
import com.terafort.onereads.databinding.FragmentHomeBinding
import com.terafort.onereads.model.HomeViewmodel
class HomeFragment : Fragment() {
    private val homeDataList: MutableList<HomeData> = mutableListOf()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewmodel
    private lateinit var adapter: AdapterHomeMain
    private var isGridLayout = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapter = AdapterHomeMain(homeDataList)
        binding.recyclerviewhome.adapter = adapter

        binding.menuLine.setOnClickListener {
            isGridLayout = !isGridLayout // toggle the layout mode on click

            // update the layout manager
            binding.recyclerviewhome.layoutManager = if (isGridLayout) {
                GridLayoutManager(context, 3)
            } else {
                LinearLayoutManager(context)
            }

            // update the adapter view type
            adapter.setViewType(if (isGridLayout) AdapterHomeMain.VIEW_TYPE_GRID else AdapterHomeMain.VIEW_TYPE_LINEAR)
        }

        setupRecyclerView()
        setupViewModel()
        return binding.root
    }

    private fun setupRecyclerView() {
        val layoutManager = if (isGridLayout) {
            GridLayoutManager(context, 3)
        } else {
            LinearLayoutManager(context)
        }
        adapter = AdapterHomeMain(homeDataList)
        binding.recyclerviewhome.adapter = adapter
        binding.recyclerviewhome.layoutManager = layoutManager
        binding.recyclerviewhome.setHasFixedSize(true)
    }

    private fun setupViewModel() {
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(this, factory).get(HomeViewmodel::class.java)
        viewModel.myDataList.observe(viewLifecycleOwner) { data ->
            homeDataList.clear()
            homeDataList.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }
}