package com.example.fooddelivery.Customer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.OrderDetailActivity
import com.example.fooddelivery.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.acitivity_customer_order_history.*

class CustomerOrderHistoryActivity : AppCompatActivity() {

    lateinit var recyclerview : RecyclerView
    lateinit var adapterCustomerHistory: CustomerOrderHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_customer_order_history)

        var preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idCustomer = preferences.getString("ID","")

        btnBack.setOnClickListener {
            finish()
        }

        var fbBill = FirebaseFirestore.getInstance().collection("Bill")
        var fbRes = FirebaseFirestore.getInstance().collection("Restaurant")
        var arrayCustomerHistory : ArrayList<CustomerHistoryArrayList> = ArrayList()

        fbBill.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (i in task.result) {
                    if (i.data.getValue("idCustomer").toString() == idCustomer) {
                        fbRes.get().addOnCompleteListener {
                            if (it.isSuccessful) {
                                for (j in it.result) {
                                    if(i.data.getValue("idRestaurant") == j.id){
                                        arrayCustomerHistory.add(CustomerHistoryArrayList(""+i.id,""+j.data.getValue("image"), ""+i.data.getValue("quantity"), ""+j.data.getValue("displayName"), ""+i.data.getValue("date"), ""+i.data.getValue("total")))
                                    }
                                }
                                recyclerview = findViewById(R.id.customerHistoryRecyclerView)
                                adapterCustomerHistory = CustomerOrderHistoryAdapter(this, arrayCustomerHistory)
                                recyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
                                recyclerview.adapter = adapterCustomerHistory

                                adapterCustomerHistory.setOnIntemClickListener(object : CustomerOrderHistoryAdapter.onIntemClickListener{
                                    override fun onClickItem(position: Int) {
                                        val intent = Intent(this@CustomerOrderHistoryActivity, OrderDetailActivity::class.java)
                                        intent.putExtra("billID", arrayCustomerHistory[position].orderID)
                                        startActivity(intent)
                                    }
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}