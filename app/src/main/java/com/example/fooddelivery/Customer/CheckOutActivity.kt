package com.example.fooddelivery.Customer


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.directions.route.*
import com.example.fooddelivery.R
import com.example.fooddelivery.model.CheckOutTemp
import com.example.fooddelivery.model.OrderInfoClass
import com.example.fooddelivery.model.WaitingOrderClass
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_check_out.*
import java.text.SimpleDateFormat
import java.util.*


class CheckOutActivity : AppCompatActivity(), OnMapReadyCallback
{
    var latitude=0.0
    var longitude=0.0
    var quantity=""
    lateinit var mMap:GoogleMap
    var arrayListName: ArrayList<CheckOutTemp> = ArrayList()
    //    var sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
//    val phoneNumber = sharedPreferences.getString("ID", "")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)
        latitude=intent.getDoubleExtra("lat",0.0)
        longitude=intent.getDoubleExtra("long",0.0)
        var preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idCustomer = preferences.getString("ID","")
        var idRestaurant = intent.getStringExtra("idRestaurant").toString()
        quantity = intent.getStringExtra("quantity").toString()
        //map
        val mapFragment:MapView = findViewById(R.id.frmMaps)

        if(mapFragment!=null) {
            mapFragment.onCreate(null)
            mapFragment.onResume()
            mapFragment.getMapAsync(this)
        }
        LoadInfo()
        //input list item picking
        LoadCart()
        //bottom sheet dialog payment
        //clickButtonChoose()
        clickButtonAdd()
        var total:Long=0
        var fb = FirebaseFirestore.getInstance().collection("Customer")
            .document("$idCustomer")
            .collection("Cart")
        var promo = FirebaseFirestore.getInstance().collection("Restaurant")
            .document("$idRestaurant")
            .collection("promotion")
        var wo = FirebaseFirestore.getInstance().collection("WaitingOrders")

        var promotionPosition = intent.getStringExtra("promotionPosition")
        Log.d("AA",""+promotionPosition)
        Log.d("AA",""+idRestaurant)

        if (promotionPosition != null) {
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
            LoadInfo()
            LoadTotal()
        }

        //Event click button back
        btnBack.setOnClickListener {
            val intent=Intent(this, RestaurantActivity::class.java)
            intent.putExtra("idRes", idRestaurant)
            intent.putExtra("lat",latitude)
            intent.putExtra("long",longitude)
            intent.putExtra("quantity",quantity)
            startActivity(intent)
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
                            .collection("ListBill").add(addListBill)
                    }
                }
            }
            wo.document("" + id).collection("ListBill")
            wo.get().addOnCompleteListener {work->
                if (work.isSuccessful) {
                    promo.get().addOnCompleteListener {
                        if (it.isSuccessful) {
                            for ((index, i) in it.result.withIndex()) {
                                if (index == promotionPosition?.toInt()) {
                                    var getLatLng =FirebaseFirestore.getInstance().collection("Customer")
                                        .get().addOnCompleteListener{work->
                                            if(work.isSuccessful){
                                                for(i in work.result){
                                                    if(i.id==idCustomer){
                                                        //add list bill
                                                        var addBill = OrderInfoClass("" + sdf.format(date),
                                                            "" + textSubFee.text.substring(0, textTotal.text.length - 4).trim(),
                                                            "$idCustomer",
                                                            "" + i.id,
                                                            "$idRestaurant",
                                                            "",
                                                            "" + quantity,
                                                            "waiting",
                                                            "" + (-textSubFee.text.substring(0,
                                                                textSubFee.text.length - 4)
                                                                .toLong() + textTotal.text.substring(0,
                                                                textTotal.text.length - 4).toLong()),""+i.data.getValue("latitude").toString(),
                                                            ""+i.data.getValue("longitude").toString(),""+textDistance.text.substring(0,
                                                                textDistance.text.length - 2))
                                                        wo.document("" + id)
                                                            .set(addBill)
                                                    }
                                                }
                                            }
                                        }

                                    btnOrder.visibility = View.GONE
                                    progressBar.visibility = View.VISIBLE

                                    val intent = Intent(this, CustomerFindingShipperActivity::class.java)
                                    intent.putExtra("idRes", idRestaurant)
                                    intent.putExtra("idCus", idCustomer)
                                    intent.putExtra("deliveryFee", textSubFee.text)
                                    intent.putExtra("subTotal", textSubPrice.text)
                                    intent.putExtra("total", textTotal.text)
                                    //intent.putExtra("listBill", arrayListName)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    startActivity(intent)
                                } else {
                                    //object waiting order info
                                    var getLatLng =FirebaseFirestore.getInstance().collection("Customer")
                                        .get().addOnCompleteListener{work->
                                            if(work.isSuccessful){
                                                for(i in work.result){
                                                    if(i.id==idCustomer){
                                                        var addBill = OrderInfoClass("" + sdf.format(date),
                                                            "" +textSubFee.text.substring(0,
                                                                textSubFee.text.length - 4).trim(),
                                                            "$idCustomer",
                                                            "",
                                                            "$idRestaurant",
                                                            "",
                                                            "" + quantity,
                                                            "waiting",
                                                            "" + (-textSubFee.text.substring(0,
                                                                textSubFee.text.length - 4)
                                                                .toLong() + textTotal.text.substring(0,
                                                                textTotal.text.length - 4).toLong()),""+i.data.getValue("latitude").toString(),
                                                            ""+i.data.getValue("longitude").toString(),""+textDistance.text.substring(0,
                                                                textDistance.text.length - 2))
                                                        wo.document("" + id)
                                                            .set(addBill)
                                                    }
                                                }
                                            }
                                        }
                                    btnOrder.visibility = View.GONE
                                    progressBar.visibility = View.VISIBLE

                                    val intent = Intent(this, CustomerFindingShipperActivity::class.java)
                                    intent.putExtra("idRes", idRestaurant)
                                    intent.putExtra("idCus", idCustomer)
                                    intent.putExtra("deliveryFee", textSubFee.text)
                                    intent.putExtra("subTotal", textSubPrice.text)
                                    intent.putExtra("total", textTotal.text)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    startActivity(intent)
                                }
//                            wo.document("" + id)
//                                .update("distance",textDistance.text.substring(0,
//                                    textDistance.text.length - 2))
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
    }

    //Payment Method Fragment
//    fun clickButtonChoose()
//    {
//        btnChoose.setOnClickListener {
//            var dlg = BottomSheetDialog(this, R.style.BottomSheetStyle)
//            var rootView: View = LayoutInflater
//                .from(this)
//                .inflate(R.layout.fragment_payment, findViewById(R.id.paymentSheetBottom))
//            dlg.setContentView(rootView)
//            dlg.show()
//
//            //click momo
//            rootView.findViewById<View>(R.id.btnMomo).setOnClickListener{
//                imgPaymentMethod.setBackgroundResource(R.drawable.logomomo)
//                imgPaymentMethod.layoutParams.height=80
//                imgPaymentMethod.layoutParams.width=80
//                textPaymentMethod.text="Momo"
//                textPaymentMethod.setTextColor(resources.getColor(R.color.grey))
//                dlg.dismiss()
//            }
//
//            //click visa
//            rootView.findViewById<View>(R.id.btnVisa).setOnClickListener{
//                imgPaymentMethod.setBackgroundResource(R.drawable.logovisa)
//                imgPaymentMethod.layoutParams.height=40
//                imgPaymentMethod.layoutParams.width=94
//                textPaymentMethod.text="Visa"
//                textPaymentMethod.setTextColor(resources.getColor(R.color.grey))
//                dlg.dismiss()
//            }
////
////            //click mastercard
//            rootView.findViewById<View>(R.id.btnMastercard).setOnClickListener{
//                imgPaymentMethod.setBackgroundResource(R.drawable.logomc)
//                imgPaymentMethod.layoutParams.height=50
//                imgPaymentMethod.layoutParams.width=100
//                textPaymentMethod.text="Mastercard"
//                textPaymentMethod.setTextColor(resources.getColor(R.color.grey))
//                dlg.dismiss()
//            }
//        }
//    }
    fun clickButtonAdd()
    {
        var idRestaurant = intent.getStringExtra("idRestaurant").toString()
        btnAdd.setOnClickListener {
            var intent = Intent(this, FragmentPromotion::class.java)
            intent.putExtra("idRestaurant", idRestaurant)
            intent.putExtra("lat",latitude)
            intent.putExtra("quantity",quantity)
            intent.putExtra("long",longitude)
            startActivity(intent)
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        var preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idCustomer = preferences.getString("ID","")
        this?.let { MapsInitializer.initialize(it)}
        mMap=p0
        mMap.mapType=GoogleMap.MAP_TYPE_NORMAL
        var fb=FirebaseFirestore.getInstance().collection("Customer")
            .get().addOnCompleteListener{
                if(it.isSuccessful){
                    for(i in it.result){
                        if(i.id==idCustomer){
                            val addr = com.google.android.gms.maps.model.LatLng(i.data.getValue("latitude").toString().toDouble(),
                                i.data.getValue("longitude").toString().toDouble())
                            mMap.addMarker(MarkerOptions().position(addr).title("Your current position!"))
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(addr,15f))
                        }
                    }
                }
            }
    }

    private fun LoadInfo() {
        var result=FloatArray(10)
        var fbcus = FirebaseFirestore.getInstance().collection("Customer")
        var fbRes = FirebaseFirestore.getInstance().collection("Restaurant")
        var idRestaurant = intent.getStringExtra("idRestaurant").toString()
        var preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idCustomer = preferences.getString("ID","")
        fbcus.get().addOnCompleteListener {task ->
            for (i in task.result) {
                if (i.id == "$idCustomer") {
                    textViewName.text = i.data.getValue("displayName").toString()
                    textViewAddress.text = i.data.getValue("address").toString()
                    textViewNoPhone.text = "$idCustomer"
                    //calculate distance and delivery fee
                    fbRes.get().addOnCompleteListener{
                        if(it.isSuccessful){
                            for (j in it.result){
                                if (j.id == "$idRestaurant") {
                                    Location.distanceBetween(i.data.getValue("latitude").toString().toDouble(),
                                        i.data.getValue("longitude").toString().toDouble(),
                                        j.data.getValue("latitude").toString().toDouble(),
                                        j.data.getValue("longitude").toString().toDouble(),result)
                                    textDistance.setText(String.format("%.1f", result[0]/1000)+"km")
                                    if(result[0]/1000<=1){
                                        textSubFee.setText("19000 VND")
                                    }else if(result[0]/1000>1 && result[0]/1000<=3){
                                        textSubFee.setText("23000 VND")
                                    }else{
                                        var count=(((result[0]/1000)-3)*5000+23000).toInt()
                                        textSubFee.setText("$count"+" VND")
                                    }
                                    LoadTotal()
                                }
                            }
                        }
                    }
                }
                continue
            }

        }
    }

    private fun LoadCart(){
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
            var subPrice = textSubPrice.text.substring(0, textSubPrice.text.length - 4).toLong()
            var priceDiscount = textDiscount.text.substring(0, textDiscount.text.length - 4).toLong()

            textTotal.setText((subPrice+fee+priceDiscount).toString()+" VND")
        }
    }

}




