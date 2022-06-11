package com.example.fooddelivery.Customer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.OrderDetailActivity
import com.example.fooddelivery.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.acitivity_customer_order_history.*

class CustomerOrderHistoryActivity : AppCompatActivity() {
    companion object {
        lateinit var recyclerView : RecyclerView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_customer_order_history)

        var preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idCustomer = preferences.getString("ID","")

        btnBack.setOnClickListener {
            finish()
        }

        var arrayCustomerHistory : ArrayList<CustomerHistoryArrayList> = ArrayList()
        var CustomerHistory : ArrayList<CustomerHistoryArrayList> = ArrayList()


        var fbBill = FirebaseFirestore.getInstance().collection("Bill")
        var fbRes = FirebaseFirestore.getInstance().collection("Restaurant")

        fbBill.get().addOnCompleteListener { task ->
            for (i in task.result) {
                if(i.data.getValue("idCustomer").toString()== idCustomer)
                {
                    fbRes.get().addOnCompleteListener {
                        for(j in it.result){
                            if(j.id == i.data.getValue("idRestaurant")){
                                arrayCustomerHistory.add(
                                    CustomerHistoryArrayList(
                                    ""+i.id,
                                    ""+j.data.getValue("image"),
                                        ""+i.data.getValue("quantity"),
                                        ""+j.data.getValue("displayName"),
                                        ""+i.data.getValue("date"),
                                        ""+i.data.getValue("total")
                                ))
                            }
                        }

                        customerHistoryRecyclerView.layoutManager = LinearLayoutManager(this)
                        customerHistoryRecyclerView.adapter = CustomerOrderHistoryAdapter(this, arrayCustomerHistory )


                        (customerHistoryRecyclerView.adapter as CustomerOrderHistoryAdapter).setOnIntemClickListener(object :
                            CustomerOrderHistoryAdapter.onIntemClickListener {
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


        fbBill.addSnapshotListener { value, error ->
            if(error!=null){
                return@addSnapshotListener
            }
            if(value!=null){
                fbBill.get().addOnCompleteListener { task ->
                    for (i in task.result) {
                        if(i.data.getValue("idCustomer").toString()== idCustomer)
                        {
                            fbRes.get().addOnCompleteListener {
                                for(j in it.result){
                                    if(j.id == i.data.getValue("idRestaurant")){
                                        CustomerHistory.add(
                                            CustomerHistoryArrayList(
                                                ""+i.id,
                                                ""+j.data.getValue("image"),
                                                ""+i.data.getValue("quantity"),
                                                ""+j.data.getValue("displayName"),
                                                ""+i.data.getValue("date"),
                                                ""+i.data.getValue("total")
                                            ))
                                    }
                                }

                                customerHistoryRecyclerView.layoutManager = LinearLayoutManager(this)
                                customerHistoryRecyclerView.adapter = CustomerOrderHistoryAdapter(this, CustomerHistory )


                                (customerHistoryRecyclerView.adapter as CustomerOrderHistoryAdapter).setOnIntemClickListener(object :
                                    CustomerOrderHistoryAdapter.onIntemClickListener {
                                    override fun onClickItem(position: Int) {
                                        val intent = Intent(this@CustomerOrderHistoryActivity, OrderDetailActivity::class.java)
                                        intent.putExtra("billID", CustomerHistory[position].orderID)
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