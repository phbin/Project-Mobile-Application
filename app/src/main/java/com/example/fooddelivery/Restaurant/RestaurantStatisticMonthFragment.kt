package com.example.fooddelivery.Restaurant

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fooddelivery.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_restaurant_statistic_month.*
import kotlinx.android.synthetic.main.fragment_restaurant_statistic_month.btnPickTime
import kotlinx.android.synthetic.main.fragment_restaurant_statistic_month.textViewDate
import kotlinx.android.synthetic.main.fragment_restaurant_statistic_month.textViewDateTotal
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RestaurantStatisticMonthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RestaurantStatisticMonthFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_restaurant_statistic_month, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var preferences = requireActivity().getSharedPreferences("SHARED_PREF", AppCompatActivity.MODE_PRIVATE)
        val idRestaurant = preferences.getString("ID", "")
        var income : Long = 0

        val c  = Calendar.getInstance()

        val y = c.get(Calendar.YEAR)
        val m = c.get(Calendar.MONTH) + 1
        val d= c.get(Calendar.DAY_OF_MONTH)

        textViewDate.text = "$m-$y"
        textViewDateTotal.text = "$m-$y"

        var fb=FirebaseFirestore.getInstance().collection("Bill")
            .get().addOnCompleteListener {
            for (i in it.result) {
                if(i.data.getValue("idRestaurant").toString()== idRestaurant){
                    if(i.data.getValue("date").toString().subSequence(4,10) == textViewDate.text.toString()){
                        income += i.data.getValue("total").toString().toLong()
                        textViewStatistics.text = income.toString()
                    }
                }
            }
            income = 0
        }

        btnPickTime.setOnClickListener {

            val dp = DatePickerDialog(requireActivity(), R.style.MyDatePickerStyle,
                { view, year, monthOfYear, _ ->
                    var erg = ""
                    erg += ((monthOfYear) +1).toString()
                    erg += "-$year"
                    (textViewDate as TextView).text = erg
                    textViewDateTotal.text = erg

                    var fb=FirebaseFirestore.getInstance().collection("Bill")
                    fb.get().addOnCompleteListener {
                        for (i in it.result) {
                            if(i.data.getValue("idRestaurant").toString()== idRestaurant){
                                if(i.data.getValue("date").toString().subSequence(4,10) == textViewDate.text.toString()){
                                    income += i.data.getValue("total").toString().toLong()
                                    textViewStatistics.text = income.toString()
                                }
                            }
                        }
                        textViewStatistics.text = income.toString()
                        income = 0
                    }
                }, y, m, d
            )
            dp.show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RestaurantStatisticMonthFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RestaurantStatisticMonthFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}