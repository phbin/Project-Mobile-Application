package com.example.fooddelivery.Restaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelivery.OrderDetailActivity
import com.example.fooddelivery.R
import com.example.fooddelivery.Shipper.ShipperAdapterHistory
import com.example.fooddelivery.Shipper.ShipperHistoryFragment
import com.example.fooddelivery.model.RestaurantOrders
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_restaurant_home_done.*
import kotlinx.android.synthetic.main.fragment_restaurant_home_processing.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RestaurantHomeDoneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RestaurantHomeDoneFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var orderArray: ArrayList<RestaurantOrders> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_home_done, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var sharedPreferences =
            requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idRestaurant = sharedPreferences.getString("ID", "")

        var fb = FirebaseFirestore.getInstance().collection("Bill")
        var fbCustomer = FirebaseFirestore.getInstance().collection("Customer")
        fb.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (i in it.result) {
                    if (i.data.getValue("idRestaurant").toString() == idRestaurant && i.data.getValue("status").toString()=="success") {
                        fbCustomer.get().addOnCompleteListener {
                            for (j in it.result) {
                                if (j.id == i.data.getValue("idCustomer")) {
                                    var customerName = j.data.getValue("displayName").toString()
                                    var customerAddress = j.data.getValue("address").toString()
                                    orderArray.add(
                                        RestaurantOrders("" + i.id,
                                            "" + customerAddress,
                                            "" + customerName,
                                            "" +i.data.getValue("quantity")+" dish(s/es)",
                                            "" + i.data.getValue("total").toString()))
                                }
                            }
                            recyclerViewDoneOrders.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
                            recyclerViewDoneOrders.adapter = RestaurantDoneOrdersAdapter(requireActivity().applicationContext,orderArray)

                            (recyclerViewDoneOrders.adapter as RestaurantDoneOrdersAdapter).setOnIntemClickListener(object :
                                RestaurantDoneOrdersAdapter.onIntemClickListener {
                                override fun onClickItem(position: Int) {
                                    val intent = Intent(requireActivity(), OrderDetailActivity::class.java)
                                    intent.putExtra("billID", orderArray[position].orderID)
                                    startActivity(intent)
                                }

                            })

                        }
                    }
                }
            }
        }

        fb.addSnapshotListener { value, error ->
            if(error != null){
                return@addSnapshotListener
            }
            if(value != null){
                var orderArrayList: ArrayList<RestaurantOrders> = ArrayList()
                fb.get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (i in it.result) {
                            if (i.data.getValue("idRestaurant").toString() == idRestaurant && i.data.getValue("status").toString()=="success") {
                                fbCustomer.get().addOnCompleteListener {
                                    for (j in it.result) {
                                        if (j.id == i.data.getValue("idCustomer")) {
                                            var customerName = j.data.getValue("displayName").toString()
                                            var customerAddress = j.data.getValue("address").toString()
                                            orderArrayList.add(
                                                RestaurantOrders("" + i.id,
                                                    "" + customerAddress,
                                                    "" + customerName,
                                                    "" +i.data.getValue("quantity")+" dish(s/es)",
                                                    "" + i.data.getValue("total").toString()))
                                        }
                                    }
                                    recyclerViewDoneOrders.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
                                    recyclerViewDoneOrders.adapter = RestaurantDoneOrdersAdapter(requireActivity().applicationContext,orderArrayList)

                                    (recyclerViewDoneOrders.adapter as RestaurantDoneOrdersAdapter).setOnIntemClickListener(object :
                                        RestaurantDoneOrdersAdapter.onIntemClickListener {
                                        override fun onClickItem(position: Int) {
                                            val intent = Intent(requireActivity(), OrderDetailActivity::class.java)
                                            intent.putExtra("billID", orderArray[position].orderID)
                                            startActivity(intent)
                                        }

                                    })

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
         * @return A new instance of fragment RestaurantHomeDoneFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RestaurantHomeDoneFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}