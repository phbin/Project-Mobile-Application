package com.example.fooddelivery.checkout

import android.content.Context
//import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.fooddelivery.R
import com.example.fooddelivery.`object`.PreviousOrdersClass
import com.example.fooddelivery.`object`.RestaurantPopularClass

class CustomAdapterPreviousOrders(var context: Context, var listitem:ArrayList<PreviousOrdersClass>):
    BaseAdapter() {
    class ViewHolder(row: View){
        var imageRest : ImageView = row.findViewById(R.id.imgPrevious)
        var textName : TextView = row.findViewById(R.id.namePrevious)

    }
    override fun getCount(): Int {
        return listitem.size
    }

    override fun getItem(pos: Int): Any {
        return listitem[pos]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(pos: Int, convertview: View?, p2: ViewGroup?): View {
        var view: View?
        var viewholder: ViewHolder
        if(convertview==null)
        {
            var layoutinflater: LayoutInflater = LayoutInflater.from(context)
            view=layoutinflater.inflate(R.layout.listview_previousorders,null)
            viewholder= ViewHolder(view)
            view.tag=viewholder
        }else{
            view=convertview
            viewholder= convertview.tag as ViewHolder
        }
        var listname: PreviousOrdersClass = getItem(pos) as PreviousOrdersClass
        viewholder.imageRest.setImageResource(listname.image)
        viewholder.textName.text=listname.name
        return view as View
    }
}