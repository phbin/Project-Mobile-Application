package com.example.fooddelivery.Shipper

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.example.fooddelivery.Restaurant.RestaurantDishesManagementActivity
import com.example.fooddelivery.Restaurant.RestaurantMenuRecyclerAdapter
import com.example.fooddelivery.model.PromotionClass
import com.example.fooddelivery.model.RestaurantMenuList
import com.example.fooddelivery.model.ShipperOrderHistory
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_restaurant_menu_management.*
import kotlinx.android.synthetic.main.fragment_shipper_history.*

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

    companion object {
        lateinit var recyclerView : RecyclerView
    }

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
                                customerAddress = j.data.getValue("address").toString()
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

                        recyclerView = view.findViewById(R.id.recyclerViewHistory)
                        recyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
                        recyclerView.adapter = ShipperAdapterHistory(requireActivity().applicationContext, orderHistoryList )

//                        (recyclerView.adapter as ShipperAdapterHistory).onItemClick = {
//                            val intent = Intent(requireActivity(), OrderDetailActivity::class.java)
//                            intent.putExtra("billID", billID)
//                            startActivity(intent)
//                        }

                        (recyclerView.adapter as ShipperAdapterHistory).setOnIntemClickListener(object :
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

//        var orderArray : ArrayList<ShipperOrderHistory> = ArrayList()
//
//        orderArray.add(ShipperOrderHistory("001", "Linh Trung, Thủ Đức", "Thế Vĩ", "1 món", "200.000đ"))
//        orderArray.add(ShipperOrderHistory("002", "Kiên Giang", "Vĩ", "2 món", "250.000đ"))
//        orderArray.add(ShipperOrderHistory("003", "Cần Thơ", "Huỳnh", "5 món", "100.000đ"))
//        orderArray.add(ShipperOrderHistory("004", "Long An", "Thế", "3 món", "300.000đ"))



    }
}