package com.example.fooddelivery.Customer

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.model.DishByCategory
import com.example.fooddelivery.model.RestaurantAppertizer

class DishByCategoryAdapter(val context: Context, var listitem: ArrayList<DishByCategory>, var idRestaurant : String)
    :RecyclerView.Adapter<DishByCategoryAdapter.ViewHolder>() {
//    lateinit var itemClick : onItemClickListener
//    interface onItemClickListener{
//        fun onItemClick(position: Int)
//    }
//    fun setOnItemClickListener(listener: onItemClickListener){
//        itemClick=listener
//    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_appertizer_grouped,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem=listitem[position]
        val categoryPosition=position
        holder.textNameCategory.text=currentItem.nameCategory
        var list:ArrayList<RestaurantAppertizer> = currentItem.list
        var resApperAdapter=Restaurant_Appertizer_RecyclerView(list)
        holder.restaurantAppertizerRecyclerView.adapter=resApperAdapter
        (holder.restaurantAppertizerRecyclerView.adapter as Restaurant_Appertizer_RecyclerView)
            .setOnItemClickListener(
            object : Restaurant_Appertizer_RecyclerView.onItemClickListener{
            override fun onItemClick(position: Int) {
                var intent= Intent(context,DishDetailActivity::class.java)
                intent.putExtra("itemPosition",position)
                intent.putExtra("categoryPosition",categoryPosition)
                intent.putExtra("idRes", idRestaurant)
                context.startActivity(intent)
            }

        })
//        resApperAdapter.setOnItemClickListener{
//
//        }
    //holder.textNameRestaurantExploreMore.text=currentItem.list[position].name
//        holder.
    }

    override fun getItemCount(): Int {
        if(listitem != null){
            return listitem.size
        }
        return 0
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        //var imageIC : ImageView
        var textNameCategory : TextView=itemView.findViewById(R.id.textNameCategory)
        var restaurantAppertizerRecyclerView:RecyclerView=itemView.findViewById(R.id.restaurantAppertizerRecyclerView)
//        init {
//            itemView.setOnClickListener {
//                listener.onItemClick(adapterPosition)
//            }
//        }
    }
}