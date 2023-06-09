package com.terafort.onereads.adaptermain


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.terafort.onereads.data.FavoriteData
import com.terafort.onereads.data.HomeData

import com.terafort.onereads.databinding.MainRecyclerViewBinding

class AdapterHomeMain(private var homeDataList: List<HomeData>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick2(position: Int)
    }
    private var viewType = VIEW_TYPE_LINEAR

    companion object {
        const val VIEW_TYPE_LINEAR = 1
        const val VIEW_TYPE_GRID = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_LINEAR) {
            val inflater = LayoutInflater.from(parent.context)
            val binding = MainRecyclerViewBinding.inflate(inflater, parent, false)
            LinearViewHolder(binding)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            val binding = MainRecyclerViewBinding.inflate(inflater, parent, false)
            GridViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val homeData = homeDataList[position]
        if (holder is LinearViewHolder) {
            holder.bind(homeData)
        } else if (holder is GridViewHolder) {
            holder.bind(homeData)
        }
    }

    override fun getItemCount(): Int {
        return homeDataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    fun setViewType(viewType: Int) {
        this.viewType = viewType
        notifyDataSetChanged()
    }

    inner class LinearViewHolder(private val binding: MainRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(homeData: HomeData) {
            binding.testimage.setImageResource(homeData.homeimageview)
            binding.Header.text = homeData.textViewHeader
            binding.nicknameofheader.text = homeData.textViewnickname

        }
        init {
            itemView.setOnClickListener {
                listener.onItemClick2(adapterPosition)
            }
        }
    }

    inner class GridViewHolder(private val binding: MainRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(homeData: HomeData) {
            binding.testimage.setImageResource(homeData.homeimageview)
            binding.Header.text = homeData.textViewHeader
            binding.nicknameofheader.text = homeData.textViewnickname

        }
        init {
            itemView.setOnClickListener {
                listener.onItemClick2(adapterPosition)
            }
        }
    }
}
