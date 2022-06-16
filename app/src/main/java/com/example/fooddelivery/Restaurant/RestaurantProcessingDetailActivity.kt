package com.example.fooddelivery.Restaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelivery.Customer.CustomAdapterListName
import com.example.fooddelivery.R
import com.example.fooddelivery.model.CheckOutTemp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_restaurant_processing_detail.*

class RestaurantProcessingDetailActivity : AppCompatActivity() {
    var customerID : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_processing_detail)

        var fb = FirebaseFirestore.getInstance().collection("WaitingOrders")
        var fbCustomer = FirebaseFirestore.getInstance().collection("Customer")
        var fbRes = FirebaseFirestore.getInstance().collection("Restaurant")

        var preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idRestaurant = preferences.getString("ID","")


        val billID = intent.getStringExtra("billID")


        var arrayListName: ArrayList<CheckOutTemp> = ArrayList()

        fb.get().addOnCompleteListener { task ->
            for(i in task.result){
                if(i.data.getValue("idRestaurant") == idRestaurant){
                    fb.document("$billID").collection("ListBill")
                        .get().addOnCompleteListener { work ->
                        for(j in work.result){
                            fbRes.document("$idRestaurant")
                                .collection("categoryMenu")
                                .document(j.data.getValue("idCategory").toString())
                                .collection("Item")
                                .get().addOnCompleteListener {
                                    for(k in it.result){
                                    if(j.data.getValue("idItem") == k.id){
                                        arrayListName.add(
                                            CheckOutTemp(
                                                ""+j.data.getValue("quantity"),
                                                ""+k.data.getValue("name"),
                                                ""+j.data.getValue("price")+" VND"
                                            ))
                                    }
                                }
                                    listviewItem.layoutManager= LinearLayoutManager(this)
                                    listviewItem.adapter = CustomAdapterListName(arrayListName)
                                    listviewItem.setHasFixedSize(true)
                                }
                        }
                    }
                }
            }
        }

        btnBack.setOnClickListener {
            var intent= Intent(this, RestaurantHomeActivity::class.java)
            startActivity(intent)
        }


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
                                textDistance.text=i.data.getValue("distance").toString()+"km"
                            }
                        }
                    }
                }
            }
        }
    }
}