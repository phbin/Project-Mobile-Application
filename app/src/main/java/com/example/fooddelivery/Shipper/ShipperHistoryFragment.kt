package com.example.fooddelivery.Shipper

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.OrderDetailActivity
import com.example.fooddelivery.R
import com.example.fooddelivery.Restaurant.RestaurantMenuRecyclerAdapter
import com.example.fooddelivery.model.ShipperOrderHistory
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_shipper_history.*
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShipperHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShipperHistoryFragment : Fragment() {

    lateinit var preferences : SharedPreferences
    private var adapter: RecyclerView.Adapter<RestaurantMenuRecyclerAdapter.ViewHolder>? = null
    lateinit var recyclerView : RecyclerView
//
//    companion object {
//        lateinit var recyclerView : RecyclerView
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_shipper_history, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var customerName : String = ""
        var customerAddress : String = ""
        var billID : String = ""

        preferences = requireActivity().getSharedPreferences("SHARED_PREF", AppCompatActivity.MODE_PRIVATE)
        val idShipper = preferences.getString("ID", "")

        var orderHistoryList : ArrayList<ShipperOrderHistory> = ArrayList()

        var fb = FirebaseFirestore.getInstance().collection("Bill")
        var fbCustomer = FirebaseFirestore.getInstance().collection("Customer")

        fb.get().addOnCompleteListener { task ->
            for (i in task.result) {
                if(i.data.getValue("idShipper").toString()== idShipper)
                {
                    fbCustomer.get().addOnCompleteListener {
                        for(j in it.result){
                            if(j.id == i.data.getValue("idCustomer")){
                                customerName = j.data.getValue("displayName").toString()
                                var customerAddress = Geocoder(requireActivity(), Locale.getDefault()).getFromLocation(i.data.getValue("latCus").toString().toDouble(),i.data.getValue("longCus").toString().toDouble(),2).get(0).featureName+" "+
                                        Geocoder(requireActivity(), Locale.getDefault()).getFromLocation(i.data.getValue("latCus").toString().toDouble(),i.data.getValue("longCus").toString().toDouble(),2).get(0).thoroughfare
                                billID = i.id
                                orderHistoryList.add(
                                    ShipperOrderHistory(billID,
                                        "" + customerAddress,
                                        "" + customerName,
                                        "",
                                        "" + i.data.getValue("total").toString()
                                    )
                                )
                            }
                        }

                        recyclerViewHistory.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
                        recyclerViewHistory.adapter = ShipperAdapterHistory(requireActivity().applicationContext, orderHistoryList )

//                        (recyclerView.adapter as ShipperAdapterHistory).onItemClick = {
//                            val intent = Intent(requireActivity(), OrderDetailActivity::class.java)
//                            intent.putExtra("billID", billID)
//                            startActivity(intent)
//                        }

                        (recyclerViewHistory.adapter as ShipperAdapterHistory).setOnIntemClickListener(object :
                            ShipperAdapterHistory.onIntemClickListener {
                            override fun onClickItem(position: Int) {
                                val intent = Intent(requireActivity(), OrderDetailActivity::class.java)
                                intent.putExtra("billID", orderHistoryList[position].orderID)
                                startActivity(intent)
                            }

                        })
                    }
                }
            }
        }

//        fb.addSnapshotListener { value, error ->
//            if (error != null){
//                return@addSnapshotListener
//            }
//            if(value!=null){
//                var orderHistory : ArrayList<ShipperOrderHistory> = ArrayList()
//                fb.get().addOnCompleteListener { task ->
//                    for (i in task.result) {
//                        if(i.data.getValue("idShipper").toString()== idShipper)
//                        {
//                            fbCustomer.get().addOnCompleteListener {
//                                for(j in it.result){
//                                    if(j.id == i.data.getValue("idCustomer")){
//                                        customerName = j.data.getValue("displayName").toString()
//                                        customerAddress = j.data.getValue("address").toString()
//                                        billID = i.id
//                                        orderHistory.add(
//                                            ShipperOrderHistory(billID,
//                                                "" + customerAddress,
//                                                "" + customerName,
//                                                "",
//                                                "" + i.data.getValue("total").toString()
//                                            )
//                                        )
//                                    }
//                                }
//
//                                recyclerViewHistory.layoutManager = LinearLayoutManager(requireActivity())
//                                recyclerViewHistory.adapter = ShipperAdapterHistory(requireActivity(),orderHistory )
//                                recyclerViewHistory.setHasFixedSize(true)
//
////                        (recyclerView.adapter as ShipperAdapterHistory).onItemClick = {
////                            val intent = Intent(requireActivity(), OrderDetailActivity::class.java)
////                            intent.putExtra("billID", billID)
////                            startActivity(intent)
////                        }
//
//                                (recyclerViewHistory.adapter as ShipperAdapterHistory).setOnIntemClickListener(object :
//                                    ShipperAdapterHistory.onIntemClickListener {
//                                    override fun onClickItem(position: Int) {
//                                        val intent = Intent(requireActivity(), OrderDetailActivity::class.java)
//                                        intent.putExtra("billID", orderHistory[position].orderID)
//                                        startActivity(intent)
//                                    }
//
//                                })
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }
}