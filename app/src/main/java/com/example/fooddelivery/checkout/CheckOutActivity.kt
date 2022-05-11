package com.example.fooddelivery.checkout


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelivery.*
import com.example.fooddelivery.`object`.CheckOutTemp
import com.example.fooddelivery.`object`.PromotionClass

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.type.LatLng
import kotlinx.android.synthetic.main.activity_check_out.*

class CheckOutActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var mMap:GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        //map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.frmMaps) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Event click button back
        btnBack.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //input list item picking
        var arrayListName: ArrayList<CheckOutTemp> = ArrayList()
        arrayListName.add(CheckOutTemp("1x", "Shaking Beef Tri-Tip", "None", "30.000 VNĐ"))
        arrayListName.add(CheckOutTemp("2x",
            "Shaking Beef Tri-Tip",
            "Add aesculus\nNo spicy",
            "30.000 VNĐ"))
        arrayListName.add(CheckOutTemp("1x", "Shaking Beef Tri-Tip", "Add aesculus", "30.000 VNĐ"))
        listviewItem.layoutManager=LinearLayoutManager(this)
        listviewItem.adapter = CustomAdapterListName(arrayListName)
        listviewItem.setHasFixedSize(true)

        //bottom sheet dialog payment
        clickButtonChoose()
        clickButtonAdd()

    }

    //Payment Method Fragment
    fun clickButtonChoose()
    {
        btnChoose.setOnClickListener {
            var dlg = BottomSheetDialog(this, R.style.BottomSheetStyle)
            var rootView: View = LayoutInflater
                                .from(this)
                                .inflate(R.layout.fragment_payment, findViewById(R.id.paymentSheetBottom))
            dlg.setContentView(rootView)
            dlg.show()

            //click momo
            rootView.findViewById<View>(R.id.btnMomo).setOnClickListener{
                imgPaymentMethod.setBackgroundResource(R.drawable.logomomo)
                imgPaymentMethod.layoutParams.height=80
                imgPaymentMethod.layoutParams.width=80
                textPaymentMethod.text="Momo"
                textPaymentMethod.setTextColor(resources.getColor(R.color.grey))
                dlg.dismiss()
            }

            //click visa
            rootView.findViewById<View>(R.id.btnVisa).setOnClickListener{
                imgPaymentMethod.setBackgroundResource(R.drawable.logovisa)
                imgPaymentMethod.layoutParams.height=40
                imgPaymentMethod.layoutParams.width=94
                textPaymentMethod.text="Visa"
                textPaymentMethod.setTextColor(resources.getColor(R.color.grey))
                dlg.dismiss()
            }

            //click mastercard
            rootView.findViewById<View>(R.id.btnMastercard).setOnClickListener{
                imgPaymentMethod.setBackgroundResource(R.drawable.logomc)
                imgPaymentMethod.layoutParams.height=50
                imgPaymentMethod.layoutParams.width=100
                textPaymentMethod.text="Mastercard"
                textPaymentMethod.setTextColor(resources.getColor(R.color.grey))
                dlg.dismiss()
            }
        }
    }

    fun clickButtonAdd()
    {
        btnAdd.setOnClickListener {
            var intent = Intent(this, FragmentPromotion::class.java)
            startActivity(intent)
//            intent= intent
//            val pos:Int=intent.getIntExtra("getPosition",0)
//            Toast.makeText(this,pos,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap=p0
        val addr = com.google.android.gms.maps.model.LatLng(-34.0,151.0)
        mMap.addMarker(MarkerOptions().position(addr).title("hihi"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(addr,15f))
    }

//    fun GetClick(a:Int){
//        if (a==1){
//            val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
//            setContentView(binding.root)
//
//            val name=intent.getStringExtra("name")
//            val ic=intent.getIntExtra("ic",R.drawable.iccoupon)
//
//            imgPromotion.setImageResource(ic)
//            textPromotion.text=name
//        }
}




