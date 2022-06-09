package com.example.fooddelivery.Customer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.Restaurant.RestaurantOrdersAdapter
import com.example.fooddelivery.model.CustomerCategory
import com.example.fooddelivery.model.RestaurantOrders
import com.squareup.picasso.Picasso

class Category_RecyclerView (var context : Context, var listItem : List<CustomerCategory>)
    : RecyclerView.Adapter<Category_RecyclerView.MyViewHolder>() {

    lateinit var itemClick : Category_RecyclerView.onIntemClickListener
    var onItemClick : ((RestaurantOrders) -> Unit)? = null

    interface onIntemClickListener{
        fun onClickItem(position: Int)
    }

    fun setOnIntemClickListener(listener: onIntemClickListener){
        itemClick=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_category,parent,false)
    return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(listItem[position].image).into(holder.imgCategory)

        holder.itemView.setOnClickListener {
//            onItemClick?.invoke(listItems[position])
            itemClick.onClickItem(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var imgCategory : ImageView = itemView.findViewById(R.id.imgCategory)
    }

}