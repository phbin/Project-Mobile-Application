package com.example.fooddelivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RestaurantPromotionAdapter(var listItems: ArrayList<RestaurantPromotionList>)
: RecyclerView.Adapter<RestaurantPromotionAdapter.ViewHolder>() {

    var onItemClick : ((RestaurantPromotionList) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantPromotionAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.listview_restaurant_promotion, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RestaurantPromotionAdapter.ViewHolder, position: Int) {
        val promotions = listItems[position]
        holder.textViewPromotionName.text = promotions.name
        holder.textViewPromotionExpiry.text = promotions.expiry

        holder.btnRemove.setOnClickListener {
            listItems.removeAt(position)
            notifyDataSetChanged()
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(promotions)
        }
    }

    override fun getItemCount(): Int {
        if(listItems != null){
            return listItems.size
        }
        return 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewPromotionName: TextView = itemView.findViewById(R.id.textViewPromotionName)
        var textViewPromotionExpiry : TextView = itemView.findViewById(R.id.textViewExpiry)
        val btnRemove: Button = itemView.findViewById(R.id.btnRemovePromotion)
    }

}