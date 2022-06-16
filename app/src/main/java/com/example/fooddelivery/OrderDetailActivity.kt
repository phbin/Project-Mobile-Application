package com.example.fooddelivery

import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelivery.Customer.CustomAdapterListName
import com.example.fooddelivery.model.CheckOutTemp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.fragment_shipper_new_order.listviewItem
import java.util.*
import kotlin.collections.ArrayList

class OrderDetailActivity : AppCompatActivity() {

    var billID : String = ""
    var customerID : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        var fb = FirebaseFirestore.getInstance().collection("Bill")
        var fbCustomer = FirebaseFirestore.getInstance().collection("Customer")
        var fbRes = FirebaseFirestore.getInstance().collection("Restaurant")
        billID = intent.getStringExtra("billID").toString()
        var idRestaurant = ""

        var arrayListName: ArrayList<CheckOutTemp> = ArrayList()

        fb.get().addOnCompleteListener { task ->
            for(i in task.result){
                if(i.id == billID){
                    idRestaurant = i.data.getValue("idRestaurant").toString()
                    fb.document("$billID").collection("ListBill").get().addOnCompleteListener { work ->
                        for(j in work.result){
                            fbRes.document("$idRestaurant").collection("categoryMenu").document(j.data.getValue("idCategory").toString()).collection("Item").get().addOnCompleteListener {
                                for(k in it.result){
                                    if(j.data.getValue("idItem") == k.id){
                                        arrayListName.add(
                                            CheckOutTemp(
                                            ""+j.data.getValue("quantity"),
                                                ""+k.data.getValue("name"),
                                                ""+j.data.getValue("price")
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
            finish()
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
                    textDistance.text=i.data.getValue("distance").toString()+"km"
                    var address=
                        Geocoder(this, Locale.getDefault()).getFromLocation(i.data.getValue("latCus").toString().toDouble(),i.data.getValue("longCus").toString().toDouble(),2).get(0).featureName+" "+
                            Geocoder(this, Locale.getDefault()).getFromLocation(i.data.getValue("latCus").toString().toDouble(),i.data.getValue("longCus").toString().toDouble(),2).get(0).thoroughfare
                    textViewCustomerAddress.text = address
                    fbCustomer.get().addOnCompleteListener {
                        for(j in it.result){
                            if(j.id == customerID){
                                textViewCustomerName.text = j.data.getValue("displayName").toString()

                            }
                        }
                    }
                }
            }
        }
    }
}