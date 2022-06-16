package com.example.fooddelivery.Customer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.model.RestaurantClass
import com.example.fooddelivery.model.RestaurantOrders
import com.squareup.picasso.Picasso

class Search_RecyclerView(private val mList: MutableList<RestaurantClass>) : RecyclerView.Adapter<Search_RecyclerView.ViewHolder>() {

    lateinit var itemClick : Search_RecyclerView.onIntemClickListener
    var onItemClick : ((RestaurantOrders) -> Unit)? = null

    interface onIntemClickListener{
        fun onClickItem(position: Int)
    }

    fun setOnIntemClickListener(listener: onIntemClickListener){
        itemClick=listener
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_restaurant, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.name.text = itemsViewModel.name
        holder.location.text = itemsViewModel.location
        Picasso.get().load(itemsViewModel.image).into(holder.image)

        holder.itemView.setOnClickListener {
//            onItemClick?.invoke(listItems[position])
            itemClick.onClickItem(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val image: ImageView = itemView.findViewById(R.id.imageRestaurantExploreMore)
        val name: TextView = itemView.findViewById(R.id.textNameRestaurantExploreMore)
        val location: TextView = itemView.findViewById(R.id.textLocationExploreMore)
    }
}