package com.example.fooddelivery.Restaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.model.RestaurantMenuList

class RestaurantMenuRecyclerAdapter(
    var listItems: ArrayList<RestaurantMenuList>)
    : RecyclerView.Adapter<RestaurantMenuRecyclerAdapter.ViewHolder>() {

    lateinit var itemClick : onDeleteItemClickListener
    interface onDeleteItemClickListener{
        fun onDeleteItemClick(position: Int)
    }
    fun setOnDeleteItemClickListener(listener: onDeleteItemClickListener){
        itemClick=listener
    }
    var onItemClick : ((RestaurantMenuList) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.listview_menu_management,parent,false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val menu = listItems[position]
        holder.textViewMenuName.text = menu.menuName

        holder.btnRemove.setOnClickListener {
            itemClick.onDeleteItemClick(position)
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
//        init {
//            itemView.setOnClickListener {
//                listener.onItemClick(adapterPosition)
//            }
//        }
    }
}