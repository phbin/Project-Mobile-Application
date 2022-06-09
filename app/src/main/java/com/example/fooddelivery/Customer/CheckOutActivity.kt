package com.example.fooddelivery.Customer


import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelivery.*
import com.example.fooddelivery.model.CheckOutTemp
import com.example.fooddelivery.model.OrderInfoClass
import com.example.fooddelivery.model.WaitingOrderClass
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_check_out.*
import java.text.SimpleDateFormat
import java.util.*


class CheckOutActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var mMap:GoogleMap
//    var sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
//    val phoneNumber = sharedPreferences.getString("ID", "")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_check_out)

    var preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
    val idCustomer = preferences.getString("ID","")
    val idRestaurant = intent.getStringExtra("idRestaurant")
    val quantity = intent.getStringExtra("quantity")

    var total:Long=0
    var fb = FirebaseFirestore.getInstance().collection("Customer")
        .document("$idCustomer")
        .collection("Cart")
    var promo = FirebaseFirestore.getInstance().collection("Restaurant")
        .document("$idCustomer")
        .collection("promotion")
    var wo = FirebaseFirestore.getInstance().collection("WaitingOrders")



    var promotionPosition = intent.getStringExtra("promotionPosition")
//    Log.d("AA",""+promotionPosition)

    if (promotionPosition != null) {
//        Log.d("AA","run")
        promo.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                fb.get().addOnCompleteListener {
                    for (i in it.result) {
                        var a=i.data.getValue("price").toString().toLong()
                        total=total+a
                    }
                    var fee= textSubFee.text.substring(0, textSubFee.text.length - 4).toLong()
                    textSubPrice.text=total.toString()+" VND"
                    textTotal.setText((total+fee).toString()+" VND")
                    //get promo
                    for ((index, i) in task.result.withIndex()) {
                        if (index == promotionPosition?.toInt()) {
//                            Log.d("AA","hic")
                            textPromotion.text = i.data.getValue("name").toString()
                            var discount = i.data.getValue("value").toString().toInt()
                            var subPrice = textSubPrice.text.substring(0, textSubPrice.text.length - 4).toLong()
                            var fee = textSubFee.text.substring(0, textSubFee.text.length - 4).toLong()

                            var getTotal = textTotal.text.substring(0, textTotal.text.length - 4).toLong()
//                            Log.d("AAA",""+getTotal)
//                            Log.d("AA",""+textTotal.text.substring(0,textTotal.text.length-4).toLong())
                            if (discount != 0) {
                                textDiscount.text = "-" + ((subPrice * discount) / 100).toString() + " VND"
                                textTotal.text = (getTotal - ((subPrice * discount) / 100)).toString() + " VND"
                            }
                            imgPromotion.setBackgroundResource(R.drawable.iccoupon)
                        }
                    }
                }
            }
        }
    }else{
        LoadTotal()
    }
    //map
    val mapFragment = supportFragmentManager
        .findFragmentById(R.id.frmMaps) as SupportMapFragment
    mapFragment.getMapAsync(this)

    //Event click button back
    btnBack.setOnClickListener {
        finish()
    }
    var sdf = SimpleDateFormat("dd-MM-yyyy")

    //Event click order
    btnOrder.setOnClickListener {
        val date = Date()
        var id: String = wo.document().id
        fb.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (i in task.result) {
                    //object item chosen
                    var addListBill =
                        WaitingOrderClass("$idCustomer",
                            "" + i.data.getValue("idCategory"),
                            "" + i.data.getValue("idItem"),
                            "" + i.data.getValue("price"),
                            "" + i.data.getValue("quantity"))
                    wo.document("" + id)
                        .collection("ListBill")
                        .add(addListBill)
                }
            }
        }
        wo.document("" + id).collection("ListBill")
        wo.get().addOnCompleteListener {
            if (it.isSuccessful) {
                promo.get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        for ((index, i) in it.result.withIndex()) {
                            if (index == promotionPosition?.toInt()) {
                                //add list bill
                                var addBill = OrderInfoClass("" + sdf.format(date),
                                    "" + textFee.text.substring(0, textTotal.text.length - 4),
                                    "$idCustomer",
                                    "" + i.id,
                                    "$idRestaurant",
                                    "",
                                    "" + quantity,
                                    "waiting",
                                    "" + (-textSubFee.text.substring(0,
                                        textSubFee.text.length - 4)
                                        .toLong() + textTotal.text.substring(0,
                                        textTotal.text.length - 4).toLong()))

                                wo.document("" + id)
                                    .set(addBill)

                                val intent = Intent(this, CustomerFindingShipperActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                            } else {
                                //object waiting order info
                                var addBill = OrderInfoClass("" + sdf.format(date),
                                    "" + textSubFee.text.substring(0,
                                        textSubFee.text.length - 4),
                                    "$idCustomer",
                                    "",
                                    "$idRestaurant",
                                    "",
                                    "" + quantity,
                                    "waiting",
                                    "" + (-textSubFee.text.substring(0,
                                        textSubFee.text.length - 4)
                                        .toLong() + textTotal.text.substring(0,
                                        textTotal.text.length - 4).toLong()))
                                wo.document("" + id)
                                    .set(addBill)

                                val intent = Intent(this, CustomerFindingShipperActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                            }
                        }
                    }
                }
            }
        }
        fb.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (i in it.result) {
                    fb.document("" + i.id).delete()
                }
            }
        }
    }

    //input list item picking
    LoadCart()
    //bottom sheet dialog payment
    clickButtonChoose()
    clickButtonAdd()
    LoadInfo()
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
        var preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idCustomer = preferences.getString("ID","")
        fb.get().addOnCompleteListener {
            for (i in it.result) {
                if (i.id == "$idCustomer") {
                    textViewName.text = i.data.getValue("displayName").toString()
                    textViewAddress.text = i.data.getValue("address").toString()
                    textViewNoPhone.text = "$idCustomer"
                    return@addOnCompleteListener
                }
                continue
            }
        }
    }

    private fun LoadCart(){
        var arrayListName: ArrayList<CheckOutTemp> = ArrayList()
        var preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idCustomer = preferences.getString("ID","")
        var fb = FirebaseFirestore.getInstance().collection("Customer")
            .document("$idCustomer")
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
        var preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idCustomer = preferences.getString("ID","")
        var fb = FirebaseFirestore.getInstance().collection("Customer")
            .document("$idCustomer")
            .collection("Cart")
        fb.get().addOnCompleteListener {
            for (i in it.result) {
                var a=i.data.getValue("price").toString().toLong()
                total=total+a
            }
            var fee= textSubFee.text.substring(0, textSubFee.text.length - 4).toLong()
            textSubPrice.text=total.toString()+" VND"
            textTotal.setText((total+fee).toString()+" VND")
        }
    }
}




