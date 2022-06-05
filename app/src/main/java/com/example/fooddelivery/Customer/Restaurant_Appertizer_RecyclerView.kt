package com.example.fooddelivery.Customer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.model.RestaurantAppertizer
import com.squareup.picasso.Picasso

class Restaurant_Appertizer_RecyclerView(var listitem:ArrayList<RestaurantAppertizer>): RecyclerView.Adapter<Restaurant_Appertizer_RecyclerView.MyViewHolder>() {
//    var onClick : ((Restaurant_Appertizer_RecyclerView) -> Unit)? = null

    lateinit var itemClick : onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        itemClick=listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_restaurant_appertizer,parent,false)
        return MyViewHolder(view,itemClick)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem=listitem[position]
        holder.textName.text=currentItem.name
        holder.textSize.text=currentItem.size
        holder.textPrice.text=currentItem.price

        Picasso.get().load(currentItem.image).into(holder.imageFD)
//        holder.itemView.setOnClickListener{
//            itemClick.onItemClick(currentItem)
//
//        }
        //holder.imageRestaurantExploreMore=currentItem.name

    }

    override fun getItemCount(): Int {
        if(listitem != null){
            return listitem.size
        }
        return 0
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
        var textName:TextView=itemView.findViewById(R.id.textName)
        var textSize:TextView=itemView.findViewById(R.id.textSize)
        var textPrice:TextView=itemView.findViewById(R.id.textPrice)
        var imageFD: ImageView = itemView.findViewById(R.id.imageFD)
        init{
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }

        }
    }
}