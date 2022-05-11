package com.example.fooddelivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment

class FragmentOrder : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView:View = inflater.inflate(R.layout.activity_shipper_order_dialog, container, false)
        var cancelButton = rootView.findViewById<Button>(R.id.btnClose)
        var submitButton = rootView.findViewById<Button>(R.id.btnAcceptOrder)

        var newOrderView:View = inflater.inflate(R.layout.fragment_shipper_new_order, container, false)
        var cardView = newOrderView.findViewById<CardView>(R.id.cardView)

        cancelButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                dismiss()
            }
        })

        submitButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                cardView.visibility = View.GONE
                dismiss()
            }
        })

        return rootView
    }
}