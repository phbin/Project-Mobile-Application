package com.example.fooddelivery.Restaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.Customer.CustomAdapterPromotion
import com.example.fooddelivery.R
import com.example.fooddelivery.model.RestaurantDishesList

class RestaurantDishesAdapter(
    var listItems: ArrayList<RestaurantDishesList>)
    : RecyclerView.Adapter<RestaurantDishesAdapter.ViewHolder>() {
    //delete current item
    lateinit var itemClick : onDeleteItemClickListener
    interface onDeleteItemClickListener{
        fun onDeleteItemClick(position: Int)
    }
    fun setOnDeleteItemClickListener(listener: onDeleteItemClickListener){
        itemClick=listener
    }
    //get position by click
    lateinit var positionClick : onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        positionClick=listener
    }

    //var onItemClick : ((RestaurantDishesList) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantDishesAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.lisview_dishes_management, parent, false)
        return ViewHolder(v, positionClick)
    }

    override fun onBindViewHolder(holder: RestaurantDishesAdapter.ViewHolder, position: Int) {

        val dishesList = listItems[position]
        holder.textViewDishName.text = dishesList.name

        holder.btnRemove.setOnClickListener {
            itemClick.onDeleteItemClick(position)
            notifyDataSetChanged()
        }

//        holder.itemView.setOnClickListener {
//            onItemClick?.invoke(dishesList)
//        }
    }

    override fun getItemCount(): Int {
        if(listItems != null){
            return listItems.size
        }
        return 0
    }

    inner class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var textViewDishName: TextView = itemView.findViewById(R.id.textViewDishName)
        val btnRemove: Button = itemView.findViewById(R.id.btnRemove)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}