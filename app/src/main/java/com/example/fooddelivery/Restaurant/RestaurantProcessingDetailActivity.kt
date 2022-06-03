package com.example.fooddelivery.Restaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelivery.Customer.CustomAdapterListName
import com.example.fooddelivery.R
import com.example.fooddelivery.model.CheckOutTemp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_restaurant_processing_detail.*

class RestaurantProcessingDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_processing_detail)


        var billID : String = ""
        var customerID : String = ""

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

        btnBack.setOnClickListener {
            finish()
        }

        billID = intent.getStringExtra("billID").toString()

        var fb = FirebaseFirestore.getInstance().collection("WaitingOrders")
        var fbCustomer = FirebaseFirestore.getInstance().collection("Customer")

        fb.get().addOnCompleteListener { task ->
            for (i in task.result) {
                if(i.id == billID){
                    textViewOrderID.text = billID
                    textViewDate.text = i.data.getValue("date").toString()
                    textViewDeliveryFee.text = i.data.getValue("deliveryFee").toString()
                    textViewSubTotal.text = i.data.getValue("total").toString()
                    textViewTotal.text = (textViewDeliveryFee.text.toString().toLong() + textViewSubTotal.text.toString().toLong()).toString()
                    customerID = i.data.getValue("idCustomer").toString()
                    textViewCustomerPhone.text = i.data.getValue("idCustomer").toString()
                    fbCustomer.get().addOnCompleteListener {
                        for(j in it.result){
                            if(j.id == customerID){
                                textViewCustomerName.text = j.data.getValue("displayName").toString()
                                textViewCustomerAddress.text = j.data.getValue("address").toString()
                            }
                        }
                    }
                }
            }
        }
    }
}