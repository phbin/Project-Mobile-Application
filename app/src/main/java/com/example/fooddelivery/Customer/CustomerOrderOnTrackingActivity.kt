package com.example.fooddelivery.Customer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelivery.R
import com.example.fooddelivery.model.CheckOutTemp
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_customer_order_on_tracking.*

class CustomerOrderOnTrackingActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_order_on_tracking)
        val mapFragment: MapView = findViewById(R.id.frmMaps)

        if(mapFragment!=null) {
            mapFragment.onCreate(null)
            mapFragment.onResume()
            mapFragment.getMapAsync(this)
        }
        var preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idCustomer = preferences.getString("ID","")

        var idBill : String = ""

        var arrayListName: ArrayList<CheckOutTemp> = ArrayList()

        listviewItem.layoutManager= LinearLayoutManager(this)
        listviewItem.adapter = CustomAdapterListName(arrayListName)
        listviewItem.setHasFixedSize(true)

        var fb = FirebaseFirestore.getInstance().collection("Bill")
        var fbShipper = FirebaseFirestore.getInstance().collection("Shipper")
        var fbWaiting = FirebaseFirestore.getInstance().collection("WaitingOrders")
        var fbRes = FirebaseFirestore.getInstance().collection("Restaurant")


        fbWaiting.get().addOnCompleteListener { task ->
            for(i in task.result){
                if(i.data.getValue("idCustomer") == idCustomer){
                    //Log.d("AA",i.data.getValue("idCustomer").toString()+" "+ idCustomer)
                    textViewOrderID.text = i.id
                    fbWaiting.document(""+i.id).collection("ListBill").get().addOnCompleteListener { work ->
                        for(j in work.result){
                            Log.d("AA",i.data.getValue("idRestaurant").toString()+" ")
                            fbRes.document(""+i.data.getValue("idRestaurant").toString()).collection("categoryMenu").document(""+j.data.getValue("idCategory").toString()).collection("Item").get().addOnCompleteListener {
                                if(it.isSuccessful){
                                    for(k in it.result){
                                        if(k.id == j.data.getValue("idItem").toString()) {
                                            fbShipper.get().addOnCompleteListener { shipper ->
                                                for (l in shipper.result) {
                                                    if (l.id == i.data.getValue("idShipper")) {
                                                        textViewOrderID.text = i.id
                                                        arrayListName.add(CheckOutTemp("" + j.data.getValue(
                                                            "quantity"),
                                                            "" + k.data.getValue("name"),
                                                            "" + j.data.getValue("price")+" VND"))
                                                        idBill = i.id
                                                        textViewShipperName.text = l.data.getValue("displayName").toString()
                                                        textViewShipperVehicle.text = l.data.getValue("transportType").toString()
                                                        textViewShipperLicensePlates.text = l.data.getValue("licensePlates").toString()
                                                        textDistance.text = i.data.getValue("distance").toString()
                                                        textViewTotal.text = i.data.getValue("total").toString()
                                                        if(textDistance.text.toString().toFloat() <=1){
                                                            textViewDeliveryFee.setText("19000")
                                                            textViewDeliveryTime.text = "10mins"
                                                        }else if(textDistance.text.toString().toFloat()<=3){
                                                            textViewDeliveryFee.setText("23000")
                                                            textViewDeliveryTime.text = "15mins"
                                                        }else{
                                                            var count=((textDistance.text.toString().toFloat()-3)*5000+23000).toInt()
                                                            //var fee=count
                                                            textViewDeliveryFee.setText("$count")
                                                            textViewDeliveryTime.text = "25mins"
                                                        }
                                                        textViewTotal.text = (textViewTotal.text.toString().toLong() + textViewDeliveryFee.text.toString().toLong()).toString()+" VND"
                                                        textViewDeliveryFee.text=textViewDeliveryFee.text.toString()+" VND"

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
                    }
                }
            }
        }

        fbWaiting.addSnapshotListener { value, error ->
            var count =0
            if(error != null){
                return@addSnapshotListener
            }
            if(value != null){
                fbWaiting.get().addOnCompleteListener { task ->
                    for (i in task.result) {
                        if (i.data.getValue("idCustomer") == idCustomer) {
                            count++
                        }
                    }
                    if(count==0){
                        val intent = Intent(this, HomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        this?.let { MapsInitializer.initialize(it)}
        mMap=p0
        mMap.mapType=GoogleMap.MAP_TYPE_NORMAL
        var fbWaiting = FirebaseFirestore.getInstance().collection("WaitingOrders")
        var fbRes = FirebaseFirestore.getInstance().collection("Restaurant")

        var preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idCustomer = preferences.getString("ID", "")
        fbWaiting.get().addOnCompleteListener { task ->
            for (i in task.result) {
                if (i.data.getValue("idCustomer") == idCustomer) {
                    var idRes=i.data.getValue("idRestaurant")
                    fbRes.get().addOnCompleteListener {
                        for(j in it.result) {
                            if (j.id == idRes) {
                                val addr = com.google.android.gms.maps.model.LatLng(j.data.getValue("latitude").toString().toDouble(),
                                    j.data.getValue("longitude").toString().toDouble())
                                mMap.addMarker(MarkerOptions().position(addr).title("Your current position!"))
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(addr,15f))
                            }
                        }
                    }
                }
            }
        }
    }
}