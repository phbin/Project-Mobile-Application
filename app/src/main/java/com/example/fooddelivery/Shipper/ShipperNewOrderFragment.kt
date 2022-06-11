package com.example.fooddelivery.Shipper

import android.content.Context
import android.content.SharedPreferences
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
import com.example.fooddelivery.model.OrderInfoClass
import com.example.fooddelivery.model.OrderStatusChange
import com.example.fooddelivery.model.WaitingOrderClass
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

    lateinit var references : SharedPreferences

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

        var sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        var idShipper = sharedPreferences.getString("ID", "")

        if(!sharedPreferences.getBoolean("isDelivering", false)){
            newOrderLayout.visibility = View.GONE
        }


        var fb = FirebaseFirestore.getInstance().collection("WaitingOrders")
        var fbCustomer = FirebaseFirestore.getInstance().collection("Customer")
        var fbRestaurant = FirebaseFirestore.getInstance().collection("Restaurant")
        var fbBill =FirebaseFirestore.getInstance().collection("Bill")


        var date : String = ""
        var deliveryFee : String = ""
        var idCustomer : String = ""
        var idPromotion : String = ""
        var idRestaurant : String = ""
        var quantity : Int = 0
        var status : String = ""
        var total : String = ""
        var idBill : String = ""


        btnFinish.setOnClickListener {
            val editor : SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean("isDelivering", false)
            editor.apply()

            var change = idShipper?.let { it1 ->
                OrderStatusChange(date, deliveryFee, idCustomer, idPromotion, idRestaurant,
                    it1, quantity, status, total)
            }

            if (change != null) {
//                fbBill.document(idBill).set(change).addOnCompleteListener {
//                    fb.document(idBill).delete()
//                }
                fbBill.document(idBill).set(change)

                fb.get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        for(i in it.result){
                            if(idShipper == i.data.getValue("idShipper")){
                                fb.document(i.id).collection("ListBill").get().addOnCompleteListener { task->
                                    for(j in task.result){
                                        var addListBill = WaitingOrderClass(
                                            ""+j.data.getValue("idRestaurant"),
                                            ""+j.data.getValue("idCategory"),
                                            ""+j.data.getValue("idItem"),
                                            ""+j.data.getValue("price"),
                                            ""+j.data.getValue("quantity")
                                        )
                                        fbBill.document(idBill).collection("ListBill").add(addListBill)
                                        fb.document(idBill).delete()
                                        editor.putBoolean("isDelivering", true)
                                        editor.apply()
                                        newOrderLayout.visibility = View.GONE
                                    }
                                }
                            }
                        }
                    }
                }
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


        fb.get().addOnCompleteListener { task ->
            for (i in task.result) {
                if (i.data.getValue("idShipper").toString() == idShipper &&
                    i.data.getValue("status").toString() == "incoming" &&
                    i.data.getValue("quantity").toString() != "0"
                ) {
                    newOrderLayout.visibility = View.VISIBLE

                    date = i.data.getValue("date").toString()
                    deliveryFee = i.data.getValue("deliveryFee").toString()
                    idCustomer = i.data.getValue("idCustomer").toString()
                    idPromotion = i.data.getValue("idPromotion").toString()
                    idRestaurant = i.data.getValue("idRestaurant").toString()
                    quantity = i.data.getValue("quantity").toString().toInt()
                    status = "success"
                    total = i.data.getValue("total").toString()
                    idBill = i.id

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