package com.terafort.onereads.adaptermain.adapterfavorite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.terafort.onereads.R
import com.terafort.onereads.data.FavoriteData
import com.terafort.onereads.fragments.HomeFragment
import com.terafort.onereads.viewmodel.FavoriteViewModel

class AdapterFavorite(
    private val context: Context,
    private val onFavClicked: (FavoriteData) -> Unit
            )
    : ListAdapter<FavoriteData, AdapterFavorite.Favoriteholder>(DIFF_ITEM_CALLBACK)
{
    companion object {

        val DIFF_ITEM_CALLBACK = object : DiffUtil.ItemCallback<FavoriteData>() {
            override fun areItemsTheSame(oldItem: FavoriteData, newItem: FavoriteData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavoriteData, newItem: FavoriteData): Boolean {
                return oldItem == newItem
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):Favoriteholder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_view_recent_favorite, parent, false)
        return Favoriteholder(view)
    }

    override fun onBindViewHolder(holder:Favoriteholder, position: Int) {
        getItem(position)?.let { movie ->

            holder.bind(movie)

            holder.favourite.setOnClickListener {
                onFavClicked.invoke(movie)
            }

            holder.movieVotes.setImageResource(R.drawable.menu_icon)
        }
    }
        class Favoriteholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val image: AppCompatImageView = itemView.findViewById(R.id.testimage5)
            private val movieTitle: AppCompatTextView = itemView.findViewById(R.id.Header5)
            private val date:AppCompatTextView=itemView.findViewById(R.id.datefavorite)
            private val time:AppCompatTextView=itemView.findViewById(R.id.timefavorite)
            private val movieLanguages: AppCompatTextView = itemView.findViewById(R.id.nicknameofheader5)
            val movieVotes: AppCompatImageView = itemView.findViewById(R.id.menurecent5)
            val favourite: AppCompatImageView = itemView.findViewById(R.id.favoriterecent5)

            fun bind(result: FavoriteData) {
                movieTitle.text = result.documentname
                movieLanguages.text = result.documentfiles
                date.text=result.documentsdate
                time.text=result.documentstime
                Glide.with(itemView.context)
                    .load(result.documentimage)
                    .into(image)
                if (result.favorite) {
                    favourite.setImageResource(R.drawable.favorite_blue_icon)
                } else {
                    favourite.setImageResource(R.drawable.favorite_icon)
                }
            }
        }
}
