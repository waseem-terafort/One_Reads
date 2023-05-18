package com.terafort.onereads.fragments


import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.terafort.onereads.R
import com.terafort.onereads.adaptermain.AdapterHomeMain
import com.terafort.onereads.adaptermain.adapterfavorite.AdapterFavorite
import com.terafort.onereads.data.FavoriteData
import com.terafort.onereads.data.HomeData
import com.terafort.onereads.databinding.FragmentHomeBinding
import com.terafort.onereads.viewmodel.FactoryOfFavorite
import com.terafort.onereads.viewmodel.FavoriteViewModel
import com.terafort.onereads.viewmodel.HomeViewmodel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
//,RecentAndFavorite.OnItemClickListener
class HomeFragment : Fragment() , AdapterHomeMain.OnItemClickListener {
    private val homeDataList: MutableList<HomeData> = mutableListOf()
    var favoriteList=listOf<FavoriteData>()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewmodel
    private lateinit var viewmodelFavorite:FavoriteViewModel
    private lateinit var factoryOfFavorite: FactoryOfFavorite
    private lateinit var adapter: AdapterHomeMain
    private var isGridLayout = true
    private var isIconChanged = false
    private val viewModels: FavoriteViewModel by lazy {
        ViewModelProvider(this, FactoryOfFavorite(requireContext().applicationContext))[FavoriteViewModel::class.java]
    }
    private val favorite by lazy {
        AdapterFavorite(requireContext()) {
            viewModels.updateBookmark(it.id)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        factoryOfFavorite = FactoryOfFavorite(requireContext())
        viewModel = ViewModelProvider(this)[HomeViewmodel::class.java]
        viewmodelFavorite = ViewModelProvider(this, factoryOfFavorite)[FavoriteViewModel::class.java]

        return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[viewModel::class.java]
        setupViewModel()
        viewmodelFavorite.filteredMovieList.observe(viewLifecycleOwner, Observer {
            favorite.submitList(it)
        })
        lifecycleScope.launch(Dispatchers.IO) {
            delay(500)
            withContext(Dispatchers.Main) {
                setupRecyclerView()
//                setRecyclerView()
                handleClicks()
            }
        }
    }
        private fun handleClicks(){
        binding.textViewdocument.setOnClickListener {

            onDocumentClick(it)
        }
        binding.textViewRecent.setOnClickListener {
            viewmodelFavorite.getAllBookmark(false)
            setRecyclerView()
        }
        binding.textViewFavorite.setOnClickListener {
            setRecyclerView()
            viewmodelFavorite.getAllBookmark(true)
        }
        binding.lineartoGrid.setOnClickListener {
            isGridLayout = !isGridLayout
            val layoutManager = if (isGridLayout) {
                GridLayoutManager(context, 2)
            } else {
                LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            }
            binding.recyclerviewhome.layoutManager = layoutManager
            adapter.setViewType(if (isGridLayout) AdapterHomeMain.VIEW_TYPE_GRID else AdapterHomeMain.VIEW_TYPE_LINEAR)
            when (isGridLayout) {
                true -> binding.lineartoGrid.setImageResource(R.drawable.linear_icon)
                false -> binding.lineartoGrid.setImageResource(R.drawable.vector)
            }
            isIconChanged = true
        }
    }
    private fun setupRecyclerView() {
        val layoutManager = if (isGridLayout) {
            GridLayoutManager(context, 2)
        } else {
            LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }
        adapter = AdapterHomeMain(homeDataList, this)
        binding.recyclerviewhome.layoutManager =layoutManager
        binding.recyclerviewhome.setHasFixedSize(false)
        binding.recyclerviewhome.adapter = adapter
        adapter.notifyDataSetChanged()

    }
    private fun setRecyclerView() {
        binding.syncimage.visibility=View.VISIBLE
        binding.lineartoGrid.visibility=View.GONE
        binding.recyclerviewhome.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewhome.adapter = favorite
    }
    private fun setupViewModel() {
            viewModel.myDataList.observe(viewLifecycleOwner) { data ->
                homeDataList.clear()
                homeDataList.addAll(data)
        }
    }
    fun onDocumentClick(view: View) {
        binding.lineartoGrid.visibility=View.VISIBLE
        binding.syncimage.visibility=View.GONE
        binding.recyclerviewhome.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerviewhome.adapter = adapter
        binding.lineartoGrid.setImageResource(R.drawable.linear_icon)
    }

    override fun onItemClick2(position: Int) {
        when (position) {
            0 -> {
                NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_documentsFragment)
            }
            1 ->{
                NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_PDFFragment)
            }
            2->{
                NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_exelFragment)
            }
            3->{
                NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_docFragment)
            }
            4->{
                NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_PPTFragment)
            }
            5->{
                NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_TXTFragment)
            }
        }
    }
}
