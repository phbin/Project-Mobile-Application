package com.example.fooddelivery.Customer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.model.CartClass
import com.squareup.picasso.Picasso

class CartAdapter(var listitem:ArrayList<CartClass>):
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

//    //delete current item
//    lateinit var itemClick : onDeleteItemClickListener
//    interface onDeleteItemClickListener{
//        fun onDeleteItemClick(position: Int)
//    }
//    fun setOnDeleteItemClickListener(listener: onDeleteItemClickListener){
//        itemClick=listener
//    }
//    //get position by click
//    lateinit var positionClick : onItemClickListener
//    interface onItemClickListener{
//        fun onItemClick(position: Int)
//    }
//    fun setOnItemClickListener(listener: onItemClickListener){
//        positionClick=listener
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_cart, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        //var imageIC : ImageView
        var textNameFD: TextView = itemView.findViewById(R.id.textNameFD)
        var textQuantity: TextView = itemView.findViewById(R.id.textQuantity)
        var textPrice: TextView = itemView.findViewById(R.id.textPrice)
        var imageFD: ImageView = itemView.findViewById(R.id.imageFD)
        //val btnDelete: Button = itemView.findViewById(R.id.btnDelete)

//        init {
//            itemView.setOnClickListener {
//                listener.onItemClick(adapterPosition)
//            }
//        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listitem[position]
        holder.textNameFD.text = currentItem.nameFD
        holder.textQuantity.text = currentItem.quantity
        holder.textPrice.text = currentItem.price

        Picasso.get().load(currentItem.imageFD).into(holder.imageFD)
//        Glide.with(context)
//            .load(currentItem.imageFD)
//            .into(holder.imageFD)

//        holder.btnDelete.setOnClickListener {
//            itemClick.onDeleteItemClick(position)
//            listitem.removeAt(position)
//            notifyDataSetChanged()
//        }
    }

    override fun getItemCount(): Int {
        if (listitem != null) {
            return listitem.size
        }
        return 0
    }
}