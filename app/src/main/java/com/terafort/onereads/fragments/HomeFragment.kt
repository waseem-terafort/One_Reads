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
import com.terafort.onereads.R
import com.terafort.onereads.adaptermain.AdapterDocuments
import com.terafort.onereads.adaptermain.AdapterHomeMain
import com.terafort.onereads.adaptermain.RecentAndFavorite
import com.terafort.onereads.data.HomeData
import com.terafort.onereads.databinding.FragmentHomeBinding
import com.terafort.onereads.model.HomeViewmodel
class HomeFragment : Fragment() {
    private val homeDataList: MutableList<HomeData> = mutableListOf()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewmodel
    private lateinit var adapter: AdapterHomeMain
    private lateinit var AllDocuments: AdapterDocuments
    private lateinit var favoriteAdapter: RecentAndFavorite
    private var isGridLayout = true
    private var isIconChanged = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapter = AdapterHomeMain(homeDataList)
        AllDocuments = AdapterDocuments(homeDataList)
        favoriteAdapter = RecentAndFavorite(homeDataList)
        binding.recyclerviewhome.adapter = AllDocuments
        setupRecyclerView()
        binding.textViewdocument.setOnClickListener {
            onDocumentClick(it)
        }
        binding.textViewRecent.setOnClickListener {
            onClickrecent(it)
        }
        binding.textViewFavorite.setOnClickListener {
            onFavoriteClick(it)
        }
        binding.lineartoGrid.setOnClickListener {
            isGridLayout = !isGridLayout // toggle the layout mode on click
            // update the layout manager
            binding.recyclerviewhome.layoutManager = if (isGridLayout) {
                GridLayoutManager(context, 2)
            } else {

                LinearLayoutManager(context)
            }

            // update the adapter view type
            adapter.setViewType(if (isGridLayout) AdapterHomeMain.VIEW_TYPE_GRID else AdapterHomeMain.VIEW_TYPE_LINEAR)
            when (isGridLayout) {
                true -> binding.lineartoGrid.setImageResource(R.drawable.linear_icon)
                false -> binding.lineartoGrid.setImageResource(R.drawable.vector)
            }
            isIconChanged = true
        }
/**        binding.recyclerviewhome.layoutManager = GridLayoutManager(context, 2) // Default layout manager is GridLayoutManager
        AllDocuments = AdapterDocuments(homeDataList)
        favoriteAdapter = RecentAndFavorite(homeDataList)
        binding.recyclerviewhome.adapter = favoriteAdapter // Default adapter is RecentAdapter*/

        setupViewModel()
        return binding.root
    }

    private fun setupRecyclerView() {
        val layoutManager = if (isGridLayout) {
            GridLayoutManager(context, 2)
        } else {
            LinearLayoutManager(context)
        }
        adapter = AdapterHomeMain(homeDataList)
        binding.recyclerviewhome.layoutManager = layoutManager
        binding.recyclerviewhome.setHasFixedSize(true)
        binding.recyclerviewhome.adapter = AllDocuments
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
    fun onDocumentClick(view: View) {
        binding.recyclerviewhome.layoutManager = GridLayoutManager(context, 2)
        if (isIconChanged) {
            binding.lineartoGrid.setImageResource(R.drawable.linear_icon)
            isIconChanged = false // reset the icon changed flag
        }
        binding.recyclerviewhome.adapter = AllDocuments
    }

    fun onClickrecent(view: View) {
        binding.recyclerviewhome.layoutManager = LinearLayoutManager(context)
        binding.lineartoGrid.setImageResource(R.drawable.sync_icon)
        binding.recyclerviewhome.adapter = favoriteAdapter
    }
    fun onFavoriteClick(view: View) {
        binding.recyclerviewhome.layoutManager = LinearLayoutManager(context)
        binding.lineartoGrid.setImageResource(R.drawable.sync_icon)
        binding.recyclerviewhome.adapter = favoriteAdapter
    }
    override fun onResume() {
        super.onResume()
        // Reset the icon to its default state
        binding.lineartoGrid.setImageResource(R.drawable.linear_icon)
    }
}