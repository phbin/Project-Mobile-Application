package com.example.fooddelivery.checkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.`object`.ShipperOrderHistory

class ShipperAdapterHistory (var context : Context, var listItems : List<ShipperOrderHistory>) : RecyclerView.Adapter<ShipperAdapterHistory.ViewHolder>() {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var textviewOrderID : TextView = itemView.findViewById(R.id.textViewOrderID)
        var textViewAddress : TextView = itemView.findViewById(R.id.textViewAddress)
        var textViewName : TextView = itemView.findViewById(R.id.textViewName)
        var textViewQuantity : TextView = itemView.findViewById(R.id.textViewQuantity)
        var textViewPrice : TextView = itemView.findViewById(R.id.textViewPrice)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return ViewHolder(inflater.inflate(R.layout.listview_order_history, parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textviewOrderID.text = listItems[position].orderID
        holder.textViewAddress.text = listItems[position].address
        holder.textViewName.text = listItems[position].name
        holder.textViewQuantity.text = listItems[position].quantity
        holder.textViewPrice.text = listItems[position].price
    }

    override fun getItemCount(): Int {
       return listItems.size
    }

//    override fun getCount(): Int {
//        return listItems.size
//    }
//
//    override fun getItem(position: Int): Any {
//        return listItems.get(position)
//    }
//
//    override fun getItemId(p0: Int): Long {
//        return p0.toLong()
//    }
//
//    override fun getView(position: Int, convertview: View?, p2: ViewGroup?): View {
//        var view : View?
//        var viewholder : ViewHolder
//        if(convertview == null){
//            var layoutinflater: LayoutInflater = LayoutInflater.from(context)
//            view=layoutinflater.inflate(R.layout.listview_order_history,null)
//            viewholder= ViewHolder(view)
//            view.tag=viewholder
//        }
//        else{
//            view = convertview
//            viewholder = convertview.tag as ViewHolder
//        }
//        var orderHistory : ShipperOrderHistory = getItem(position) as ShipperOrderHistory
//        viewholder.textviewOrderID.text = orderHistory.orderID
//        viewholder.textViewAddress.text = orderHistory.address
//        viewholder.textViewName.text = orderHistory.name
//        viewholder.textViewQuantity.text = orderHistory.quantity
//        viewholder.textViewPrice.text = orderHistory.price
//
//        return view as View
//    }
}