package com.example.fooddelivery.checkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.fooddelivery.R
import com.example.fooddelivery.`object`.RestaurantClass
import com.example.fooddelivery.`object`.RestaurantPopularClass

class CustomAdapterRestaurant(var context: Context, var listitem: ArrayList<RestaurantClass>):
    BaseAdapter() {
    class ViewHolder(row: View){
        var imageRest : ImageView = row.findViewById(R.id.imgRest)
        var textName : TextView = row.findViewById(R.id.textNameRest)
        var textDish: TextView = row.findViewById(R.id.textDish)
        var textRate: TextView = row.findViewById(R.id.textRate)
        var textLocation: TextView = row.findViewById(R.id.textLocation)

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
            view=layoutinflater.inflate(R.layout.listview_restaurant,null)
            viewholder= ViewHolder(view)
            view.tag=viewholder
        }else{
            view=convertview
            viewholder= convertview.tag as ViewHolder
        }
        var listname: RestaurantClass = getItem(pos) as RestaurantClass
        viewholder.imageRest.setImageResource(listname.image)
        viewholder.textName.text=listname.name
        viewholder.textDish.text=listname.dishes
        viewholder.textRate.text=listname.rate
        viewholder.textLocation.text=listname.location
        return view as View
    }
}