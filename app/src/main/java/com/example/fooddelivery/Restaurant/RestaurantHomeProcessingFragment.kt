package com.example.fooddelivery.Restaurant

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.RestaurantOrdersAdapter
import com.example.fooddelivery.model.RestaurantOrders
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_restaurant_home_processing.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RestaurantHomeProcessingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RestaurantHomeProcessingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RestaurantMenuRecyclerAdapter.ViewHolder>? = null

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
        return inflater.inflate(R.layout.fragment_restaurant_home_processing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        listViewRestaurantOrders.layoutManager = layoutManager

        var sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        var idRestaurant = sharedPreferences.getString("ID", "")

        var orderArray : ArrayList<RestaurantOrders> = ArrayList()

//          orderArray.add(RestaurantOrders(""+idRestaurant, "Linh, Thủ Đức", "Thế Vĩ", "1 món", "200.000đ"))
//        orderArray.add(RestaurantOrders("002", "Kiên Giang", "Vĩ", "2 món", "250.000đ"))
//        orderArray.add(RestaurantOrders("003", "Cần Thơ", "Huỳnh", "5 món", "100.000đ"))
//        orderArray.add(RestaurantOrders("004", "Long An", "Thế", "3 món", "300.000đ"))

        var fb = FirebaseFirestore.getInstance().collection("Bill")
        var fbCustomer = FirebaseFirestore.getInstance().collection("Customer")
        fb.get().addOnCompleteListener {task ->
            for (i in task.result) {
                    if (i.data.getValue("idRestaurant").toString() == idRestaurant) {
                        fbCustomer.get().addOnCompleteListener {
                            for (j in it.result) {
                                if (j.id == i.data.getValue("idCustomer")) {
                                    var customerName = j.data.getValue("displayName").toString()
                                    var customerAddress = j.data.getValue("address").toString()
                                    orderArray.add(
                                        RestaurantOrders("" + i.id,
                                            "" + customerAddress,
                                            "" + customerName,
                                            "" +i.data.getValue("quantity")+"dish(es)",
                                            "" + i.data.getValue("total").toString()))
                                }
                            }
                            listViewRestaurantOrders.adapter = RestaurantOrdersAdapter(requireActivity().applicationContext,orderArray)
                        }
                    }
                }
            }
        }

//        recyclerView.adapter = RestaurantOrdersAdapter(requireActivity().applicationContext, orderArray )
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RestaurantHomeProcessingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RestaurantHomeProcessingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}