package com.terafort.onereads.adaptermain.adapterfavorite

import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.terafort.onereads.R
import com.terafort.onereads.data.FavoriteData
import com.terafort.onereads.data.OneReadClassData
import com.terafort.onereads.data.searchviewdata.RecntDataList

class TestAdapter(val context: Context, var itemList: List<FavoriteData>, val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<TestAdapter.MyViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int,view: View)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val restName: TextView =view.findViewById(R.id.Header5)
        val restRating: TextView = view.findViewById(R.id.nicknameofheader5)
        val restImage: ImageView =view.findViewById(R.id.testimage5)
        val favourites : ImageView =view.findViewById(R.id.favoriterecent5)
        val llContent: ImageView =view.findViewById(R.id.menurecent5)
        init {
            favourites.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            itemClickListener.onItemClick(absoluteAdapterPosition,itemView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_recent_favorite, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val restaurant=itemList[position]
        holder.restName.text=restaurant.restaurantName
        holder.restRating.text=restaurant.restaurentRating
        holder.restImage.setImageResource(restaurant.costPerPerson)
        holder.llContent.setOnClickListener {

        }
        val restaurantEntity= FavoriteData(
            restaurant.restaurantId,
            restaurant.restaurantName,
            restaurant.restaurentRating,
            restaurant.costPerPerson,
            restaurant.restaurantImage
        )
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
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    class DBAsyncTask(val context: Context, private val restaurantEntity: FavoriteData, private val mode: Int) : AsyncTask<Void, Void, Boolean>() {

        private val db=
            Room.databaseBuilder(context, OneReadClassData::class.java, "restaurant-db").build()

        override fun doInBackground(vararg p0: Void?): Boolean {


            when (mode) {
                1 -> {
                    val restaurant: FavoriteData? = db.restaurantDao()
                        .getRestaurantById(restaurantEntity.restaurantId)
                    db.close()
                    return restaurant != null
                }
                2 -> {
                    db.restaurantDao().insertRestaurant(restaurantEntity)
                    db.close()
                    return true
                }
                3 -> {
                    db.restaurantDao().deleteRestaurant(restaurantEntity)
                    db.close()
                    return true
                }
                else->return false
            }
        }
    }
}