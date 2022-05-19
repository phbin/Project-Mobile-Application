package com.example.fooddelivery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.`object`.RestaurantOrders

class RestaurantOdersAdapter(var context : Context, var listItems : List<RestaurantOrders>) :
    RecyclerView.Adapter<RestaurantOdersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantOdersAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.listview_restaurant_orders, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RestaurantOdersAdapter.ViewHolder, position: Int) {
        holder.textviewOrderID.text = listItems[position].orderID
        holder.textViewAddress.text = listItems[position].address
        holder.textViewName.text = listItems[position].name
        holder.textViewQuantity.text = listItems[position].quantity
        holder.textViewPrice.text = listItems[position].price
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        var textviewOrderID : TextView = itemView.findViewById(R.id.textViewOrderID)
        var textViewAddress : TextView = itemView.findViewById(R.id.textViewAddress)
        var textViewName : TextView = itemView.findViewById(R.id.textViewName)
        var textViewQuantity : TextView = itemView.findViewById(R.id.textViewQuantity)
        var textViewPrice : TextView = itemView.findViewById(R.id.textViewPrice)
    }

}