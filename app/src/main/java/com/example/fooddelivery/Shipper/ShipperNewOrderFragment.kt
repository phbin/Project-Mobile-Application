package com.example.fooddelivery.Shipper

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddelivery.Customer.CustomAdapterListName
import com.example.fooddelivery.R
import com.example.fooddelivery.model.CheckOutTemp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_shipper_new_order.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShipperNewOrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShipperNewOrderFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shipper_new_order, container, false)
        // Inflate the layout for this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnStartDelivery.setOnClickListener {
            if(btnStartDelivery.text.toString() == "Start delivery"){
                btnStartDelivery.text = "Finish Delivery"
            }
            else{
                Toast.makeText(requireActivity(), "Xong đơn", Toast.LENGTH_SHORT).show()
            }
        }

        var orderArray : ArrayList<CheckOutTemp> = ArrayList()

        orderArray.add(
            CheckOutTemp("2",
                "Shaking Beef Tri-Tip",
                "30.000 VNĐ")
        )

        listviewItem.layoutManager= LinearLayoutManager(requireActivity())
        listviewItem.adapter = CustomAdapterListName(orderArray)
        listviewItem.setHasFixedSize(true)

        var sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        var idShipper = sharedPreferences.getString("ID", "")

        var fb = FirebaseFirestore.getInstance().collection("Bill")
        var fbCustomer = FirebaseFirestore.getInstance().collection("Customer")
        var fbRestaurant = FirebaseFirestore.getInstance().collection("Restaurant")

        fb.get().addOnCompleteListener { task ->
            for (i in task.result) {
                if (i.data.getValue("idShipper")
                        .toString() == idShipper && i.data.getValue("status")
                        .toString() == "incoming" && i.data.getValue("quantity").toString() != "0"
                ) {
                    textViewOrderID.text = i.id
                    textViewSubTotal.text = i.data.getValue("total").toString()
                    textViewDeliveryFee.text = i.data.getValue("deliveryFee").toString()
                    fbCustomer.get().addOnCompleteListener {
                        for (j in it.result) {
                            if (j.id == i.data.getValue("idCustomer")) {
                                textViewCustomerName.text = j.data.getValue("displayName").toString()
                                textViewCustomerAddress.text = j.data.getValue("address").toString()
                            }
                        }
                    }
                    fbRestaurant.get().addOnCompleteListener {
                        for (k in it.result) {
                            if (k.id == i.data.getValue("idRestaurant")) {
                                textViewResName.text = k.data.getValue("displayName").toString()
                                textViewResAddress.text = k.data.getValue("address").toString()
                            }
                        }
                    }
                }
            }
            textViewTotal.text = (textViewSubTotal.text.toString().toLong() + textViewDeliveryFee.text.toString().toLong()).toString()
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
}