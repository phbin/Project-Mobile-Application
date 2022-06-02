package com.example.fooddelivery.Customer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.model.PromotionClass

class CustomAdapterPromotion(var listitem:ArrayList<PromotionClass>):
    RecyclerView.Adapter<CustomAdapterPromotion.ViewHolder>() {
    //var onClick : ((PromotionClass) -> Unit)? = null

    lateinit var itemClick : onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        itemClick=listener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.listview_promotion,parent,false)
        return ViewHolder(itemView, itemClick)
    }
    class ViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView)
    {
        //var imageIC : ImageView
        var textName : TextView=itemView.findViewById(R.id.textName)
        var textDate: TextView=itemView.findViewById(R.id.textDate)

        init{
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
            //imageIC=itemView.findViewById(R.id.imgIcon)

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem=listitem[position]
        //holder.imageIC.setImageResource(currentItem.ic)
        holder.textName.text=currentItem.name
        holder.textDate.text=currentItem.expiryDate

//        holder.itemView.setOnClickListener {
//            onClick?.invoke(currentItem)
//        }
    }

    override fun getItemCount(): Int {
        if(listitem != null){
            return listitem.size
        }
        return 0    }
}