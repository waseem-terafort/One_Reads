package com.terafort.onereads.adaptermain

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.terafort.onereads.R
import com.terafort.onereads.adaptermain.adapterfavorite.TestAdapter
import com.terafort.onereads.data.HomeData
import com.terafort.onereads.data.searchviewdata.RecntDataList

class RecentAndFavorite(private var homeDataList: List<HomeData>,val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view_recent_favorite, parent, false)
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
        myViewHolder.favotite.setOnClickListener {
            itemClickListener.onItemClick(position)
        }
        /*      holder.favourites.setOnClickListener {
          Toast.makeText(context,"fav click", Toast.LENGTH_SHORT).show()
          if (!AdapterFavorite.DBAsyncTask(context, restaurantEntity, 1).execute().get()) {

              val result= AdapterFavorite.DBAsyncTask(context, restaurantEntity, 2).execute().get()

              if(result){

                  Toast.makeText(context,"Added to favourites", Toast.LENGTH_SHORT).show()

                  holder.favourites.setTag("liked")//new value
                  holder.favourites.background =
                      context.resources.getDrawable(R.drawable.favorite_blue_icon)
              }else{
                  Toast.makeText(context,"Some error occurred", Toast.LENGTH_SHORT).show()
              }

          } else {

              val result= AdapterFavorite.DBAsyncTask(context, restaurantEntity, 3).execute().get()
              if(result){

                  Toast.makeText(context,"Removed from favourites", Toast.LENGTH_SHORT).show()

                  holder.favourites.tag = "Unlike"
                  holder.favourites.background =
                      context.resources.getDrawable(R.drawable.favorite_icon)
              }else{

                  Toast.makeText(context,"Some error occurred", Toast.LENGTH_SHORT).show()
              }
          }
      }*/
        myViewHolder.menu.setOnClickListener {

        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.testimage5)
        val textViewTag: TextView = itemView.findViewById(R.id.Header5)
        val textViewdate: TextView = itemView.findViewById(R.id.nicknameofheader5)
        val favotite:ImageView=itemView.findViewById(R.id.favoriterecent5)
        val menu:ImageView=itemView.findViewById(R.id.menurecent5)

    }
}