package com.example.fooddelivery.checkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.`object`.CheckOutTemp

class CustomAdapterListName (var listitem:ArrayList<CheckOutTemp>):
    RecyclerView.Adapter<CustomAdapterListName.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.listview_choosingitem,parent,false)
            return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem=listitem[position]
        holder.textAmount.text=currentItem.amount
        holder.textDetail.text=currentItem.detail
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
        var textDetail: TextView
        var textPrice: TextView
        init{
            textAmount=itemView.findViewById(R.id.textAmount)
            textName=itemView.findViewById(R.id.textName)
            textDetail=itemView.findViewById(R.id.textDetail)
            textPrice=itemView.findViewById(R.id.textPrice)
        }
    }
}