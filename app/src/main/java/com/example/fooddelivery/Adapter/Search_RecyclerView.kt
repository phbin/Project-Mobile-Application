package com.example.fooddelivery.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.model.RestaurantClass

class Search_RecyclerView(private val mList: MutableList<RestaurantClass>) : RecyclerView.Adapter<Search_RecyclerView.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent:

                                    ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_restaurant, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]


        holder.image.setImageResource(ItemsViewModel.image)

        holder.name.text = ItemsViewModel.name
        holder.dishes.text = ItemsViewModel.dishes
        holder.location.text = ItemsViewModel.location


    }

    override fun getItemCount(): Int {
        return mList.size
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val image: ImageView = itemView.findViewById(R.id.imageRestaurantExploreMore)
        val name: TextView = itemView.findViewById(R.id.textNameRestaurantExploreMore)
        val dishes: TextView= itemView.findViewById(R.id.textDishExploreMore)
        val location: TextView = itemView.findViewById(R.id.textLocationExploreMore)
    }
}