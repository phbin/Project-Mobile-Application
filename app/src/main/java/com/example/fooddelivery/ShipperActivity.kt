package com.example.fooddelivery

import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_shipper.*
import kotlinx.android.synthetic.main.activity_shipper.view.*
import kotlinx.android.synthetic.main.activity_shipper_order_dialog.view.*
import kotlinx.android.synthetic.main.fragment_shipper_new_order.*
import kotlinx.android.synthetic.main.fragment_shipper_profile.*

class ShipperActivity : AppCompatActivity() {

    lateinit var preferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipper)

        preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE)

        viewPager.adapter = PageAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

        val fm = supportFragmentManager
        val fragmentOrder = FragmentOrder()


        btnStatus.setOnClickListener {
            if(btnStatus.text == "Receive order")
            {
                btnStatus.text="Stopped"
                btnStatus.setTextColor(Color.parseColor("#828282"))
                val icon = applicationContext.resources.getDrawable(R.drawable.ic_status_closed)
                btnStatus.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
            }
            else{
                btnStatus.text="Receive order"
                preferences.getString("STATUS", btnStatus.text.toString())
                btnStatus.setTextColor(Color.parseColor("#FFFFFF"))
                val icon = applicationContext.resources.getDrawable(R.drawable.ic_shipper_status)
                btnStatus.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
            }
        }

//        btnGetOrder.setOnClickListener {
//
//            fragmentOrder.show(fm, "Order Fragment")
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
//        }
    }
}