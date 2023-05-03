package com.terafort.onereads.adaptermain

import android.util.Log
import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.terafort.onereads.R

import com.terafort.onereads.data.HomeData

import com.terafort.onereads.databinding.MainRecyclerViewBinding

class AdapterHomeMain(var homeDataList: List<HomeData>) :
    RecyclerView.Adapter<AdapterHomeMain.ViewHolder>() {

    class ViewHolder(private val binding: MainRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(homeData: HomeData) {
            binding.testimage.setImageResource(homeData.Homeimageview)
            binding.Header.text = homeData.textViewHeader
            binding.nicknameofheader.text = homeData.textViewnickname
            Log.e("homedata", "data is showing")
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MainRecyclerViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val homeData = homeDataList[position]
        holder.bind(homeData)
    }

    override fun getItemCount(): Int {
        return homeDataList.size
    }

    fun setDataList(dataList: List<HomeData>) {
        this.homeDataList = dataList
        notifyDataSetChanged()
        Log.e("homedata2", "data is showing")
    }
}

