package com.example.fooddelivery.Shipper

import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fooddelivery.R
import com.example.fooddelivery.model.RestaurantMenuList
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_shipper_income_day.*
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShipperIncomeDayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShipperIncomeDayFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var preferences : SharedPreferences


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
        return inflater.inflate(R.layout.fragment_shipper_income_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var fb = FirebaseFirestore.getInstance().collection("Bill")
        var income : Long = 0

        val myCalendar = Calendar.getInstance()

        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.TAIWAN)
        textViewDate.text = sdf.format((myCalendar.time))
        textViewDateTotal.text = sdf.format((myCalendar.time))

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar)
        }

        btnPickTime.setOnClickListener{
            DatePickerDialog(requireActivity(), R.style.MyDatePickerStyle, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        preferences = requireActivity().getSharedPreferences("SHARED_PREF", AppCompatActivity.MODE_PRIVATE)
        val idShipper = preferences.getString("ID", "")

        fb.get().addOnCompleteListener {
            for (i in it.result) {
                if(i.data.getValue("idShipper").toString()== idShipper){
                    if(i.data.getValue("date").toString() == textViewDate.text.toString()){
                        income += i.data.getValue("deliveryFee").toString().toLong()
                        textViewIncome.text = income.toString()
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
         * @return A new instance of fragment ShipperIncomeDayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShipperIncomeDayFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.TAIWAN)
        var income : Long = 0
        textViewDate.text = sdf.format((myCalendar.time))
        textViewDateTotal.text = sdf.format((myCalendar.time))

        var fb = FirebaseFirestore.getInstance().collection("Bill")

        fb.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (i in it.result) {
                    if(i.data.getValue("date").toString() == textViewDate.text.toString()){
                        income += i.data.getValue("deliveryFee").toString().toLong()
                        textViewIncome.text = income.toString()
                    }
                    else continue
                }
                textViewIncome.text = income.toString()
                income = 0
            }
        }
    }
}


