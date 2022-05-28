package com.example.fooddelivery.checkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.fooddelivery.R
import com.example.fooddelivery.`object`.RestaurantPopularClass

class CustomAdapterRestaurantPopular(var context: Context, var listitem:ArrayList<RestaurantPopularClass>):
    BaseAdapter() {
    class ViewHolder(row: View){
        var imageRest : ImageView = row.findViewById(R.id.imgRestPopular)
        var textName : TextView = row.findViewById(R.id.textNameRestPopular)
        var textRate: TextView = row.findViewById(R.id.textRatePopular)
        var textLocation: TextView = row.findViewById(R.id.textLocationPopular)

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
            view=layoutinflater.inflate(R.layout.gridview_restaurantpopular,null)
            viewholder= ViewHolder(view)
            view.tag=viewholder
        }else{
            view=convertview
            viewholder= convertview.tag as ViewHolder
        }
        var listname: RestaurantPopularClass = getItem(pos) as RestaurantPopularClass
        viewholder.imageRest.setImageResource(listname.image)
        viewholder.textName.text=listname.name
        viewholder.textRate.text=listname.rate
        viewholder.textLocation.text=listname.location
        return view as View
    }
}