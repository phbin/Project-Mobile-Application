package com.example.fooddelivery.checkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.fooddelivery.R
import com.example.fooddelivery.`object`.PromotionClass

class CustomAdapterPromotion(var context: Context, var listitem:ArrayList<PromotionClass>):
    BaseAdapter() {
    class ViewHolder(row: View){
        var imageIC : ImageView = row.findViewById(R.id.imgIcon)
        var textName : TextView = row.findViewById(R.id.textName)
        var textDate: TextView = row.findViewById(R.id.textDate)
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
            view=layoutinflater.inflate(R.layout.listview_promotion,null)
            viewholder= ViewHolder(view)
            view.tag=viewholder
        }else{
            view=convertview
            viewholder= convertview.tag as ViewHolder
        }
        var listname: PromotionClass = getItem(pos) as PromotionClass
        viewholder.imageIC.setImageResource(listname.ic)
        viewholder.textName.text=listname.name
        viewholder.textDate.text=listname.date
        return view as View
    }
}