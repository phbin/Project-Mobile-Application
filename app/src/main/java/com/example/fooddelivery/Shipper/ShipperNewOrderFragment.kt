package com.example.fooddelivery.Shipper

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelivery.Customer.CustomAdapterListName
import com.example.fooddelivery.R
import com.example.fooddelivery.R.*
import com.example.fooddelivery.model.CheckOutTemp
import com.example.fooddelivery.model.OrderInfoClass
import com.example.fooddelivery.model.OrderStatusChange
import com.example.fooddelivery.model.WaitingOrderClass
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_check_out.*
import kotlinx.android.synthetic.main.fragment_shipper_new_order.*
import kotlinx.android.synthetic.main.fragment_shipper_new_order.listviewItem
import kotlinx.android.synthetic.main.fragment_shipper_new_order.textFee
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShipperNewOrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShipperNewOrderFragment : Fragment(), OnMapReadyCallback {

    lateinit var references : SharedPreferences
    lateinit var mMap:GoogleMap
    lateinit var mView:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView=inflater.inflate(layout.fragment_shipper_new_order, container, false)
        return mView
        // Inflate the layout for this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idShipper = sharedPreferences.getString("ID", "")
        //map
        val mapFragment =  mView.findViewById(R.id.frmMaps) as MapView

        if(mapFragment!=null){
            mapFragment.onCreate(null)
            mapFragment.onResume()
            mapFragment.getMapAsync(this)
        }

        if(!sharedPreferences.getBoolean("isDelivering", false)){
            newOrderLayout.visibility = View.GONE
        }


        var fb = FirebaseFirestore.getInstance().collection("WaitingOrders")
        var fbCustomer = FirebaseFirestore.getInstance().collection("Customer")
        var fbRestaurant = FirebaseFirestore.getInstance().collection("Restaurant")
        var fbBill =FirebaseFirestore.getInstance().collection("Bill")

        var orderArray: ArrayList<CheckOutTemp> = ArrayList()

        btnFinish.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean("isDelivering", false)
            editor.apply()

            var id: String = fbBill.document().id
            fb.get().addOnCompleteListener {
                if (it.isSuccessful) {
                    for (i in it.result) {
                        if (idShipper == i.data.getValue("idShipper")) {
                            fb.document(i.id).collection("ListBill").get()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        for (j in task.result) {
                                            var addListBill = WaitingOrderClass(
                                                "" + j.data.getValue("idRestaurant"),
                                                "" + j.data.getValue("idCategory"),
                                                "" + j.data.getValue("idItem"),
                                                "" + j.data.getValue("price"),
                                                "" + j.data.getValue("quantity")
                                            )
                                            Log.d("AAA",""+id)
                                            fbBill.document(""+id).collection("ListBill")
                                                .add(addListBill)
                                        }
//
//
//
//
                                    }
                                    var addbill= OrderInfoClass("" + i.data.getValue("date"),
                                        "" + i.data.getValue("deliveryFee"),
                                        ""+i.data.getValue("idCustomer"),
                                        "" + i.data.getValue("idPromotion"),
                                        ""+i.data.getValue("idRestaurant"),
                                        ""+i.data.getValue("idShipper"),
                                        "" + i.data.getValue("quantity"),
                                        "success",
                                        "" + i.data.getValue("total"),""+i.data.getValue("latCus"),
                                    ""+i.data.getValue("longCus"),""+i.data.getValue("distance"))
                                    fbBill.document(""+id).set(addbill)
                                }
//                            fbBill.document(""+id).update("latCus",i.data.getValue("latCus"))
//                            fbBill.document(""+id).update("longCus",i.data.getValue("longCus"))
//                            fbBill.document(""+id).update("distance",i.data.getValue("distance"))
                        }
                        fb.document(i.id).delete()
                    }
                }
                var intent= Intent(requireActivity(), ShipperActivity::class.java)
                startActivity(intent)
            }
        }

        fb.get().addOnCompleteListener {task ->
            for (i in task.result) {
                if (i.data.getValue("idShipper").toString() == idShipper &&
                    i.data.getValue("status").toString() == "incoming" &&
                    i.data.getValue("quantity").toString() != "0"
                ) {
                    newOrderLayout.visibility = View.VISIBLE
                    fbCustomer.get().addOnCompleteListener {
                        for(j in it.result){
                            if(j.id == i.data.getValue("idCustomer")){
                                fbRestaurant.get().addOnCompleteListener { res->
                                    for(k in res.result){
                                        if(k.id == i.data.getValue("idRestaurant")){
                                            textViewCustomerName.text = j.data.getValue("displayName").toString()
                                            textViewOrderID.text = i.id
                                            textViewResName.text = k.data.getValue("displayName").toString()
                                            textViewCusPhoneNumber.text = j.id
                                            var subtotal=i.data.getValue("total").toString()
                                            var fee=i.data.getValue("deliveryFee").toString()

                                            textViewSubTotal.text = subtotal+" VND"
                                            textViewDeliveryFee.text = fee+" VND"
                                            textViewTotal.text = (subtotal.toLong() + fee.toLong()).toString()
                                            textDistanceOrder.text=i.data.getValue("distance").toString()+"km"
                                            var address=Geocoder(requireActivity(),
                                            Locale.getDefault()).getFromLocation(i.data.getValue("latCus")
                                            .toString().toDouble(),
                                            i.data.getValue("longCus").toString().toDouble(),
                                            2).get(0).featureName+" "+Geocoder(requireActivity(),
                                                Locale.getDefault()).getFromLocation(i.data.getValue("latCus")
                                                .toString().toDouble(),
                                                i.data.getValue("longCus").toString().toDouble(),
                                                2).get(0).thoroughfare
                                            textViewCustomerAddress.text=address

                                            var adrr=Geocoder(requireActivity(),
                                                Locale.getDefault()).getFromLocation(k.data.getValue("latitude")
                                                .toString().toDouble(),
                                                k.data.getValue("longitude").toString().toDouble(),
                                                2).get(0).featureName+" "+Geocoder(requireActivity(),
                                                Locale.getDefault()).getFromLocation(k.data.getValue("latitude")
                                                .toString().toDouble(),
                                                k.data.getValue("longitude").toString().toDouble(),
                                                2).get(0).thoroughfare
                                            textViewResAddress.text=adrr

                                            var fbListBill = fb.document(i.id).collection("ListBill").get().addOnCompleteListener {lb->
                                                for(l in lb.result){
                                                    fbRestaurant.document("" + i.data.getValue("idRestaurant"))
                                                        .collection("categoryMenu")
                                                        .document("" + l.data.getValue("idCategory"))
                                                        .collection("Item").get()
                                                        .addOnCompleteListener { get ->
                                                            for (m in get.result) {
                                                                if (m.id == l.data.getValue("idItem")) {
                                                                    orderArray.add(
                                                                        CheckOutTemp("" + l.data.getValue(
                                                                            "quantity"),
                                                                            "" + m.data.getValue("name"),
                                                                            "" + l.data.getValue("price") + " VND"))
                                                                }
                                                            }
                                                            listviewItem.layoutManager =
                                                                LinearLayoutManager(requireActivity())
                                                            listviewItem.adapter =
                                                                CustomAdapterListName(orderArray)
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
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShipperNewOrderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShipperNewOrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onMapReady(p0: GoogleMap) {
        context?.let { MapsInitializer.initialize(it) }
        mMap=p0
        mMap.mapType= GoogleMap.MAP_TYPE_NORMAL
        var sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        var idShipper = sharedPreferences.getString("ID", "")
        var getLatLng =FirebaseFirestore.getInstance().collection("WaitingOrders")
            .get().addOnCompleteListener { work ->
                if (work.isSuccessful) {
                    for (i in work.result) {
                        if (idShipper == i.data.getValue("idShipper")) {
                            var latCus=i.data.getValue("latCus").toString().toDouble()
                            var longCus=i.data.getValue("longCus").toString().toDouble()

                            val addrCus = com.google.android.gms.maps.model.LatLng(latCus,longCus)
                            mMap.addMarker(MarkerOptions().position(addrCus).title("Customer's position"))
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(addrCus,15f))
                        }
                    }

                }
            }
//        val addr = com.google.android.gms.maps.model.LatLng(10.856220,106.767600)
//        mMap.addMarker(MarkerOptions().position(addr).title("Restaurant's position"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(addr,15f))


    }
}