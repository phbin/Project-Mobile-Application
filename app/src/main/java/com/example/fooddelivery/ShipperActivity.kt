package com.example.fooddelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_shipper.*
import kotlinx.android.synthetic.main.activity_shipper.view.*
import kotlinx.android.synthetic.main.activity_shipper_order_dialog.view.*
import kotlinx.android.synthetic.main.fragment_shipper_new_order.*

class ShipperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipper)

        viewPager.adapter = PageAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

        val fm = supportFragmentManager
        val fragmentOrder = FragmentOrder()


        btnStatus.setOnClickListener {
            Toast.makeText(this, "Nhan dc r ne", Toast.LENGTH_SHORT).show()
            cardView.visibility = View.GONE
        }

        btnGetOrder.setOnClickListener {

            fragmentOrder.show(fm, "Order Fragment")
//            val view = View.inflate(this@ShipperActivity, R.layout.activity_shipper_order_dialog, null)
//
//            val builder = AlertDialog.Builder(this@ShipperActivity)
//
//            val dialog = builder.create()
//
//            builder.setView(view)
//
//
//            dialog.show()
//            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
//            dialog.setCancelable(false)
//
//            view.btnGetOrder.setOnClickListener {
//                dialog.dismiss()
//            }
//
//            view.btnClose.setOnClickListener {
//                dialog.dismiss()
//            }
        }
    }
}