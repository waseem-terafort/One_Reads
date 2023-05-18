package com.terafort.onereads.adaptermain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.terafort.onereads.R
import com.terafort.onereads.data.filesdata.FileModel

class AdapterHomeFiles(private var documentsList: List<FileModel>) : RecyclerView.Adapter<AdapterHomeFiles.DocumentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_all_documents_view, parent, false)
        return DocumentViewHolder(view)
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        val document = documentsList[position]
        holder.bind(document)
    }
    fun setData(data: List<FileModel>) {
        documentsList = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return documentsList.size
    }

    inner class DocumentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.testimage5)
        private val headerTextView: TextView = itemView.findViewById(R.id.Header5)
        private val nicknameTextView: TextView = itemView.findViewById(R.id.nicknameofheader5)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateofdocuments)
        private val timeTextView: TextView = itemView.findViewById(R.id.timeofalldocuments)
        private val menuImageView: ImageView = itemView.findViewById(R.id.menurecent5)
        private val favoriteImageView: ImageView = itemView.findViewById(R.id.favoriterecent5)

        fun bind(document: FileModel) {
            imageView.setImageResource(document.imagefiles)
            headerTextView.text = document.name
            nicknameTextView.text = document.nicknamefiles
            dateTextView.text = document.datefiles
            timeTextView.text = document.timefiles
            menuImageView.setOnClickListener {

            }
            if (document.favorite) {
                favoriteImageView.setImageResource(R.drawable.favorite_blue_icon)
            } else {
                favoriteImageView.setImageResource(R.drawable.favorite_icon)
            }
        }
    }
}
