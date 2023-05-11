package com.terafort.onereads.adaptermain

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.terafort.onereads.R
import com.terafort.onereads.data.HomeData

class AdapterDocuments(private var homeDataList: List<HomeData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_documents, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return homeDataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myViewHolder = holder as MyViewHolder
        val homeData = homeDataList[position]

        myViewHolder.imageView.setImageResource(homeData.homeimageview)
        myViewHolder.textViewTag.text = homeData.textViewHeader
        myViewHolder.textViewdate.text = homeData.textViewnickname
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.testimage4)
        val textViewTag: TextView = itemView.findViewById(R.id.header3)
        val textViewdate: TextView = itemView.findViewById(R.id.headernick1)
    }
}