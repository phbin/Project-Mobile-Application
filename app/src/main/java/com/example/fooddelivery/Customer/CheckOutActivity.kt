package com.example.fooddelivery.Customer


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelivery.*
import com.example.fooddelivery.model.CheckOutTemp
import com.example.fooddelivery.model.PromotionClass
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_check_out.*
import kotlinx.android.synthetic.main.activity_check_out.btnBack
import kotlinx.android.synthetic.main.activity_check_out.listviewItem


class CheckOutActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var mMap:GoogleMap
//    var sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
//    val phoneNumber = sharedPreferences.getString("ID", "")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)
        var promotionPosition= intent.getStringExtra("promotionPosition")
        if(promotionPosition!="") {
            var fb = FirebaseFirestore.getInstance().collection("Restaurant")
                .document("0393751403")
                .collection("promotion")
            fb.get().addOnCompleteListener {
                if (it.isSuccessful) {
                    for ((index, i) in it.result.withIndex()) {
                        if (index == promotionPosition?.toInt()) {
                            textPromotion.text=i.data.getValue("name").toString()
                            var discount=i.data.getValue("value").toString().toInt()
                            var getTotal=textTotal.text.substring(0,textTotal.text.length-4).toLong()
                            //Log.d("AAA",""+getTotal)
                            Log.d("AA",""+textTotal.text.substring(0,textTotal.text.length-4).toLong())
                            if(discount!=0){
                                textDiscount.text= "-"+((getTotal*discount)/100).toString()+" VND"
                                textTotal.text= (getTotal-discount).toString()+" VND"
                            }
                            imgPromotion.setBackgroundResource(R.drawable.iccoupon)

                        }
                    }
                }
            }
        }
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
        LoadCart()

        //bottom sheet dialog payment
        clickButtonChoose()
        clickButtonAdd()
        LoadInfo()
        LoadTotal()
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
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap=p0
        val addr = com.google.android.gms.maps.model.LatLng(-34.0,151.0)
        mMap.addMarker(MarkerOptions().position(addr).title("hihi"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(addr,15f))
    }

    private fun LoadInfo() {
        var fb = FirebaseFirestore.getInstance().collection("Customer")
        fb.get().addOnCompleteListener {
            for (i in it.result) {
                if (i.id == "0393751403") {
                    textViewName.text = i.data.getValue("displayName").toString()
                    textViewAddress.text = i.data.getValue("address").toString()
                    textViewNoPhone.text = "0393751403"
                    return@addOnCompleteListener
                }
                continue
            }
        }
    }

    private fun LoadCart(){
        var arrayListName: ArrayList<CheckOutTemp> = ArrayList()
        var fb = FirebaseFirestore.getInstance().collection("Customer")
            .document("0393751403")
            .collection("Cart")
        fb.get().addOnCompleteListener {
            for (i in it.result) {
                arrayListName.add(CheckOutTemp("" + i.data.getValue("quantity"),
                    "" + i.data.getValue("nameFD"),
                    "" + i.data.getValue("price")+" VND"))
            }
            listviewItem.layoutManager = LinearLayoutManager(this)
            listviewItem.adapter = CustomAdapterListName(arrayListName)
        }
    }
    private fun LoadTotal(){
        var total:Long=0
        var fb = FirebaseFirestore.getInstance().collection("Customer")
            .document("0393751403")
            .collection("Cart")
        fb.get().addOnCompleteListener {
            for (i in it.result) {
                var a=i.data.getValue("price").toString().toLong()
                total=total+a
            }
            textSubPrice.text=total.toString()+" VND"
            textTotal.setText((total+18000).toString()+" VND")
        }
    }
}




