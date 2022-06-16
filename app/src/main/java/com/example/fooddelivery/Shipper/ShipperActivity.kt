package com.example.fooddelivery.Shipper

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.location.Geocoder
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.fooddelivery.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_shipper.*
import kotlinx.android.synthetic.main.activity_shipper_order_dialog.*
import kotlinx.android.synthetic.main.activity_shipper_order_dialog.view.*
import kotlinx.android.synthetic.main.fragment_shipper_new_order.*
import java.util.*

class ShipperActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var preferences : SharedPreferences
    lateinit var mMap:GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipper)

        preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE)

        viewPager.adapter = PageAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
//        val mapFragment = supportFragmentManager.findFragmentById(R.id.frmMaps) as SupportMapFragment
//
//        mapFragment.getMapAsync(this)

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
                        if (i.data.getValue("idShipper").toString() == "" && i.data.getValue("status").toString()=="waiting" && !preferences.getBoolean("isDelivering", false) && btnStatus.text == "Receive order"){
                            val mDialogView = LayoutInflater.from(this, ).inflate(R.layout.activity_shipper_order_dialog, null)

                            val mBuilder = AlertDialog.Builder(this)
                                .setView(mDialogView)

                            val mAlertDialog = mBuilder.show()
                            //mDialogView.textNameCus.setText("má m")
                            val mapFragment: MapView =  mDialogView.findViewById(R.id.frmMaps)

                            if(mapFragment!=null){
                                mapFragment.onCreate(null)
                                mapFragment.onResume()
                                mapFragment.getMapAsync(this)
                            }
                            var idCus=""
                            var fb = FirebaseFirestore.getInstance().collection("WaitingOrders")
                            fb.get().addOnCompleteListener { task ->
                                for (i in task.result) {
                                    if (i.data.getValue("idShipper")
                                            .toString() == "" && !preferences.getBoolean("isDelivering",
                                            false) && btnStatus.text == "Receive order") {
                                        idCus = i.data.getValue("idCustomer").toString()
                                        mDialogView.textIDBill.text = i.id
                                        mDialogView.textQuantity.text = i.data.getValue("quantity").toString() + "item(s)"
                                        mDialogView.textSubTotal.text = i.data.getValue("total").toString()+" VND"
                                        mDialogView.textTotal.text = (i.data.getValue("deliveryFee").toString().toInt() + i.data.getValue("total").toString().toInt()).toString()+" VND"
                                        mDialogView.textDeliveryFee.text = i.data.getValue("deliveryFee").toString()+" VND"
                                        mDialogView.textDist.text = i.data.getValue("distance").toString() + "km"
                                        var address= Geocoder(this, Locale.getDefault()).getFromLocation(i.data.getValue("latCus").toString().toDouble(),i.data.getValue("longCus").toString().toDouble(),2).get(0).featureName+" "+
                                                Geocoder(this, Locale.getDefault()).getFromLocation(i.data.getValue("latCus").toString().toDouble(),i.data.getValue("longCus").toString().toDouble(),2).get(0).thoroughfare
                                        mDialogView.textAddress.text=address

                                        fbCustomer.get().addOnCompleteListener{
                                            for(j in it.result){
                                                if (j.id==idCus){
                                                    mDialogView.textNameCus.text=j.data.getValue("displayName").toString()
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            mDialogView.btnAcceptOrder.setOnClickListener {
                                firebase.document(i.id).update("idShipper", idShipper)
                                firebase.document(i.id).update("status", "incoming")

                                val editor : SharedPreferences.Editor = preferences.edit()
                                editor.putBoolean("isDelivering", true)
                                editor.apply()
                                mAlertDialog.dismiss()

                                finish()
                                startActivity(intent)
                                textViewNoOrder.visibility= View.GONE
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

    override fun onMapReady(p0: GoogleMap) {
        this?.let { MapsInitializer.initialize(it) }
        mMap=p0
        mMap.mapType= GoogleMap.MAP_TYPE_NORMAL
        var sharedPreferences = this.getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        var idShipper = sharedPreferences.getString("ID", "")
        var getLatLng =FirebaseFirestore.getInstance().collection("WaitingOrders")
            .get().addOnCompleteListener { work ->
                if (work.isSuccessful) {
                    for (i in work.result) {
                        if (i.data.getValue("idShipper").toString() == "" && !preferences.getBoolean("isDelivering", false) && btnStatus.text == "Receive order") {
                            var latCus=i.data.getValue("latCus").toString().toDouble()
                            var longCus=i.data.getValue("longCus").toString().toDouble()

                            val addrCus = com.google.android.gms.maps.model.LatLng(latCus,longCus)
                            mMap.addMarker(MarkerOptions().position(addrCus).title("Customer's position"))
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(addrCus,15f))
                        }
                    }

                }
            }
    }
}