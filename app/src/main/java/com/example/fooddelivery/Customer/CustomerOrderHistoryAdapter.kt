package com.example.fooddelivery.Customer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class CustomerOrderHistoryAdapter (var context : Context, var listItem : ArrayList<CustomerHistoryArrayList>)
    : RecyclerView.Adapter<CustomerOrderHistoryAdapter.ViewHolder>(){

    lateinit var itemClick : CustomerOrderHistoryAdapter.onIntemClickListener
    var onItemClick : ((CustomerHistoryArrayList) -> Unit)? = null

    interface onIntemClickListener{
        fun onClickItem(position: Int)
    }

    fun setOnIntemClickListener(listener: onIntemClickListener){
        itemClick=listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_customer_history,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.quantity.text = listItem[position].quantity
        holder.restaurantName.text = listItem[position].restaurantName
        holder.date.text = listItem[position].date
        holder.total.text = listItem[position].total

        Picasso.get().load(listItem[position].image).into(holder.image)

        holder.itemView.setOnClickListener {
//            onItemClick?.invoke(listItems[position])
            itemClick.onClickItem(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var image : CircleImageView = itemView.findViewById(R.id.imageViewRestaurant)
        var quantity : TextView = itemView.findViewById(R.id.textViewQuantity)
        var restaurantName : TextView = itemView.findViewById(R.id.textViewResName)
        var date : TextView = itemView.findViewById(R.id.textViewDate)
        var total : TextView = itemView.findViewById(R.id.textViewTotal)
    }
}