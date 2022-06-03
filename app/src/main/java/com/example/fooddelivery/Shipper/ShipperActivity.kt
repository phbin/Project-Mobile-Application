package com.example.fooddelivery.Shipper

import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentTransaction
import com.example.fooddelivery.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_shipper.*
import kotlinx.android.synthetic.main.activity_shipper_order_dialog.view.*

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

        val idShipper = preferences.getString("ID", "")

        var fb = FirebaseFirestore.getInstance().collection("Shipper")
        fb.get().addOnCompleteListener {
            for(i in it.result){
                if(i.id == preferences.getString("ID", ""))
                {
                    if(i.data.getValue("status").toString() == "Stopped"){
                        btnStatus.setTextColor(Color.parseColor("#828282"))
                        btnStatus.text = i.data.getValue("status").toString()
                        val icon = applicationContext.resources.getDrawable(R.drawable.ic_status_closed)
                        btnStatus.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
                    }
                    else
                    {
                        btnStatus.text = i.data.getValue("status").toString()
                        btnStatus.setTextColor(Color.parseColor("#FFFFFF"))
                        val icon = applicationContext.resources.getDrawable(R.drawable.ic_shipper_status)
                        btnStatus.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
                    }
                    return@addOnCompleteListener
                }
                continue
            }
        }


        btnStatus.setOnClickListener {
            val id = preferences.getString("ID", "")
            if(btnStatus.text == "Receive order")
            {

                btnStatus.text="Stopped"
                btnStatus.setTextColor(Color.parseColor("#828282"))
                val icon = applicationContext.resources.getDrawable(R.drawable.ic_status_closed)
                btnStatus.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
                var fb = FirebaseFirestore.getInstance().collection("Shipper").document("$id").update("status", "Stopped")
            }
            else{
                btnStatus.text="Receive order"
                preferences.getString("STATUS", btnStatus.text.toString())
                btnStatus.setTextColor(Color.parseColor("#FFFFFF"))
                val icon = applicationContext.resources.getDrawable(R.drawable.ic_shipper_status)
                btnStatus.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
                var fb = FirebaseFirestore.getInstance().collection("Shipper").document("$id").update("status", "Receive order")
            }
        }

        var firebase = FirebaseFirestore.getInstance().collection("WaitingOrders")
        var fbCustomer = FirebaseFirestore.getInstance().collection("Customer")

        firebase.addSnapshotListener { value, error ->
            if(error != null){
                return@addSnapshotListener
            }
            if(value != null){
                firebase.get().addOnCompleteListener { task ->
                    for (i in task.result) {
                        if (i.data.getValue("idShipper").toString() == "" && !preferences.getBoolean("isDelivering", false)){
                            val mDialogView = LayoutInflater.from(this, ).inflate(R.layout.activity_shipper_order_dialog, null)

                            val mBuilder = AlertDialog.Builder(this)
                                .setView(mDialogView)

                            val mAlertDialog = mBuilder.show()

                            mDialogView.btnAcceptOrder.setOnClickListener {
                                firebase.document(i.id).update("idShipper", idShipper)
                                firebase.document(i.id).update("status", "incoming")

                                val editor : SharedPreferences.Editor = preferences.edit()
                                editor.putBoolean("isDelivering", true)
                                editor.apply()
                                mAlertDialog.dismiss()

                                finish();
                                startActivity(getIntent());
                            }

                            mDialogView.btnClose.setOnClickListener {
                                mAlertDialog.dismiss()
                                return@setOnClickListener
                            }
                        }
                    }
                }
            }
        }

//        btnGetOrder.setOnClickListener {
//            val mDialogView = LayoutInflater.from(this, ).inflate(R.layout.activity_shipper_order_dialog, null)
//
//            val mBuilder = AlertDialog.Builder(this)
//                .setView(mDialogView)
//
//            val mAlertDialog = mBuilder.show()
//
//            mDialogView.btnAcceptOrder.setOnClickListener {
//                mAlertDialog.dismiss()
//            }
//
//            mDialogView.btnClose.setOnClickListener {
//                mAlertDialog.dismiss()
//            }
//        }

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