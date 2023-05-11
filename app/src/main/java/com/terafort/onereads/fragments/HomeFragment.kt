package com.terafort.onereads.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.terafort.onereads.R
import com.terafort.onereads.adaptermain.AdapterDocuments
import com.terafort.onereads.adaptermain.AdapterHomeMain
import com.terafort.onereads.adaptermain.RecentAndFavorite
import com.terafort.onereads.adaptermain.adapterfavorite.TestAdapter
import com.terafort.onereads.data.FavoriteData
import com.terafort.onereads.data.HomeData
import com.terafort.onereads.data.OneReadClassData
import com.terafort.onereads.data.searchviewdata.RecntDataList
import com.terafort.onereads.databinding.FragmentHomeBinding
import com.terafort.onereads.model.HomeViewmodel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment(),RecentAndFavorite.OnItemClickListener {
    private val homeDataList: MutableList<HomeData> = mutableListOf()
    var favoriteList=listOf<FavoriteData>()
    var RecentList=listOf<RecntDataList>()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewmodel
    private lateinit var adapter: AdapterHomeMain
    private lateinit var AllDocuments: AdapterDocuments
    private lateinit var recentAdapter: RecentAndFavorite
//    private lateinit var favoriteAdapter: AdapterFavorite
    private lateinit var favoriteAdapter: TestAdapter
    private var isGridLayout = true
    private var isIconChanged = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        try {
            viewModel = ViewModelProvider(this@HomeFragment, factory)[HomeViewmodel::class.java]
        } catch (ex: Exception) {
            Log.e("HomeFragment", "Error initializing HomeViewModel: ${ex.message}")
        }
        return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.textViewdocument.setTextColor(ContextCompat.getColor(requireContext(), R.color.nick))
//        // Reset the text color of the other TextViews
//        binding.textViewRecent.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
//        binding.textViewFavorite.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        lifecycleScope.launch(Dispatchers.IO) {
            if(requireContext()!=null) {
                val db = Room.databaseBuilder(
                    requireContext(),
                    OneReadClassData::class.java,
                    "restaurant-db"
                ).build()
                favoriteList = db.restaurantDao().getAllRestaurants()
                Log.e("checklist",""+favoriteList)
            }
            withContext(Dispatchers.Main) {
                setupViewModel()
                setupRecyclerView()
                handleClicks()
            }
        }
    }

        private fun handleClicks(){
        binding.textViewdocument.setOnClickListener {
            onDocumentClick(it)
        }
        binding.textViewRecent.setOnClickListener {
            onClickrecent(it,RecentList)
        }
        binding.textViewFavorite.setOnClickListener {
            onFavoriteClick(it,favoriteList)
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
        adapter = AdapterHomeMain(homeDataList)
        binding.recyclerviewhome.layoutManager =layoutManager
        binding.recyclerviewhome.setHasFixedSize(false)
        binding.recyclerviewhome.adapter = adapter
        adapter.notifyDataSetChanged()
//        AllDocuments = AdapterDocuments(homeDataList)
        recentAdapter = RecentAndFavorite(homeDataList,this)
        Log.e("checklist2",""+recentAdapter)
    }
    private fun setupViewModel() {
            viewModel.myDataList.observe(viewLifecycleOwner) { data ->
                homeDataList.clear()
                homeDataList.addAll(data)
        }
        Log.e("checklist",""+homeDataList)
    }
    fun onDocumentClick(view: View) {
        binding.recyclerviewhome.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerviewhome.adapter = adapter
        binding.lineartoGrid.setImageResource(R.drawable.linear_icon)
//        binding.textViewdocument.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
//
//        binding.textViewdocument.setOnClickListener {
//            // Set text color for current click event
//            binding.textViewdocument.setTextColor(ContextCompat.getColor(requireContext(), R.color.nick))
//            binding.textViewRecent.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
//            binding.textViewFavorite.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
//        }
    }
    fun onClickrecent(view: View,list: List<RecntDataList>) {
        binding.recyclerviewhome.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
//        val syncImage = view?.findViewById<ImageView>(R.id.syncimage)
//        syncImage?.visibility = View.VISIBLE
//        val grid = view?.findViewById<ImageView>(R.id.lineartoGrid)
//        grid?.visibility=View.GONE
        RecentList =list
        binding.recyclerviewhome.adapter = recentAdapter
//        binding.textViewRecent.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
//
//        binding.textViewRecent.setOnClickListener {
//            // Set text color for current click event
//            binding.textViewRecent.setTextColor(ContextCompat.getColor(requireContext(), R.color.nick))
//            binding.textViewdocument.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
//            binding.textViewFavorite.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
//        }
    }
    fun onFavoriteClick(view: View, list: List<FavoriteData>) {
        binding.recyclerviewhome.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
//        val syncImage = view?.findViewById<ImageView>(R.id.syncimage)
//        syncImage?.visibility = View.VISIBLE
//        val grid = view?.findViewById<ImageView>(R.id.lineartoGrid)
//        grid?.visibility=View.GONE
        favoriteList = list
        binding.recyclerviewhome.adapter = recentAdapter
//        binding.textViewFavorite.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
//
//        binding.textViewFavorite.setOnClickListener {
//            // Set text color for current click event
//            binding.textViewFavorite.setTextColor(ContextCompat.getColor(requireContext(), R.color.nick))
//            binding.textViewdocument.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
//            binding.textViewRecent.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
//        }
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(requireContext(), ""+position, Toast.LENGTH_SHORT).show()
    }

}
