package com.example.fooddelivery.checkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.fooddelivery.R
import com.example.fooddelivery.`object`.CheckOutTemp

class CustomAdapterListName (var context: Context, var listitem:ArrayList<CheckOutTemp>):
    BaseAdapter() {
    class ViewHolder(row: View){
        var textAmount : TextView
        var textName : TextView
        var textDetail: TextView
        var textPrice: TextView
        init{
            textAmount=row.findViewById(R.id.textAmount)
            textName=row.findViewById(R.id.textName)
            textDetail=row.findViewById(R.id.textDetail)
            textPrice=row.findViewById(R.id.textPrice)
        }
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
            view=layoutinflater.inflate(R.layout.listview_choosingitem,null)
            viewholder= ViewHolder(view)
            view.tag=viewholder
        }else{
            view=convertview
            viewholder= convertview.tag as ViewHolder
        }
        var listname: CheckOutTemp = getItem(pos) as CheckOutTemp
        viewholder.textAmount.text=listname.amount
        viewholder.textDetail.text=listname.detail
        viewholder.textPrice.text=listname.price
        viewholder.textName.text=listname.name
        return view as View
    }
}