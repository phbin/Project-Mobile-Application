package com.example.fooddelivery.Customer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelivery.R
import com.example.fooddelivery.model.CheckOutTemp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_customer_fiding_shipper.*

class CustomerFindingShipperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_fiding_shipper)

        var preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idCustomer = preferences.getString("ID","")

        var arrayListName: ArrayList<CheckOutTemp> = ArrayList()
        arrayListName.add(CheckOutTemp("1", "Shaking Beef Tri-Tip", "30.000 VNĐ"))
        arrayListName.add(
            CheckOutTemp("2",
                "Shaking Beef Tri-Tip",
                "30.000 VNĐ")
        )
        arrayListName.add(
            CheckOutTemp("2",
                "Shaking Beef Tri-Tip",
                "30.000 VNĐ")
        )
        arrayListName.add(
            CheckOutTemp("2",
                "Shaking Beef Tri-Tip",
                "30.000 VNĐ")
        )

        arrayListName.add(CheckOutTemp("1", "Shaking Beef Tri-Tip", "30.000 VNĐ"))
        listviewItem.layoutManager= LinearLayoutManager(this)
        listviewItem.adapter = CustomAdapterListName(arrayListName)
        listviewItem.setHasFixedSize(true)

        var fb = FirebaseFirestore.getInstance().collection("Bill")
        var fbCustomer = FirebaseFirestore.getInstance().collection("Customer")
        var fbWaiting = FirebaseFirestore.getInstance().collection("WaitingOrders")

        fbWaiting.addSnapshotListener { value, error ->
            if(error!=null){
                return@addSnapshotListener
            }
            if(value!=null){
                fbWaiting.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var check = false
                        for (i in task.result) {
                            if(i.data.getValue("idCustomer") == idCustomer){
                                check = true
                                if(i.data.getValue("idShipper")!=""){
                                    val intent = Intent(this, CustomerOrderOnTrackingActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    startActivity(intent)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}