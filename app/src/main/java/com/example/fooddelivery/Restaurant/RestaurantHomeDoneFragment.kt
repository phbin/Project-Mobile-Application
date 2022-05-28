package com.example.fooddelivery.Restaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelivery.R
import com.example.fooddelivery.Shipper.ShipperHistoryFragment.Companion.recyclerView
import com.example.fooddelivery.model.RestaurantOrders

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

        recyclerView = view.findViewById(R.id.recyclerViewDoneOrders)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)

        var orderArray : ArrayList<RestaurantOrders> = ArrayList()

        orderArray.add(RestaurantOrders("005", "Linh, Thủ Đức", "Thế Vĩ", "1 món", "200.000đ"))
        orderArray.add(RestaurantOrders("002", "Kiên Giang", "Vĩ", "2 món", "250.000đ"))
        orderArray.add(RestaurantOrders("003", "Cần Thơ", "Huỳnh", "5 món", "100.000đ"))
        orderArray.add(RestaurantOrders("004", "Long An", "Thế", "3 món", "300.000đ"))


        recyclerView.adapter = RestaurantDoneOrdersAdapter(requireActivity().applicationContext, orderArray )
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