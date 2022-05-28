package com.example.fooddelivery

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.`object`.RestaurantMenuList

class RestaurantMenuRecyclerAdapter(
    var listItems: ArrayList<RestaurantMenuList>)
    : RecyclerView.Adapter<RestaurantMenuRecyclerAdapter.ViewHolder>() {

    var onItemClick : ((RestaurantMenuList) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantMenuRecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.listview_menu_management, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RestaurantMenuRecyclerAdapter.ViewHolder, position: Int) {

        val menu = listItems[position]
        holder.textViewMenuName.text = menu.menuName

        holder.btnRemove.setOnClickListener {
            listItems.removeAt(position)
            notifyDataSetChanged()
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(menu)
        }
    }

    override fun getItemCount(): Int {
        if(listItems != null){
            return listItems.size
        }
        return 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewMenuName: TextView = itemView.findViewById(R.id.textViewMenuName)
        val btnRemove: Button = itemView.findViewById(R.id.btnRemove)
    }
}