package com.example.fooddelivery.Customer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.model.CheckOutTemp

class CustomAdapterListName (var listitem:ArrayList<CheckOutTemp>):
    RecyclerView.Adapter<CustomAdapterListName.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.listview_choosingitem,parent,false)
            return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem=listitem[position]
        holder.textAmount.text=currentItem.amount
        holder.textPrice.text=currentItem.price
        holder.textName.text=currentItem.name
    }

    override fun getItemCount(): Int {
        return listitem.size
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var textAmount : TextView
        var textName : TextView
        var textPrice: TextView
        init{
            textAmount=itemView.findViewById(R.id.textAmount)
            textName=itemView.findViewById(R.id.textName)
            textPrice=itemView.findViewById(R.id.textPrice)
        }
    }
}