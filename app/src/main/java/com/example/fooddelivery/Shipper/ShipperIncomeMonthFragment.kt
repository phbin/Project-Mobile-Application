package com.example.fooddelivery.Shipper

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.fooddelivery.R
import kotlinx.android.synthetic.main.fragment_shipper_income_month.*
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShipperIncomeMonthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShipperIncomeMonthFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_shipper_income_month, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val c  = Calendar.getInstance()

        val y = c.get(Calendar.YEAR)
        val m = c.get(Calendar.MONTH) + 1
        val d= c.get(Calendar.DAY_OF_MONTH)

        textViewDate.text = "$m/$y"
        textViewDateTotal.text = "$m/$y"

        btnPickTime.setOnClickListener {

            val dp = DatePickerDialog(requireActivity(), R.style.MyDatePickerStyle,
                { view, year, monthOfYear, _ ->
                    var erg = ""
                    erg += ((monthOfYear)).toString()
                    erg += "/$year"
                    (textViewDate as TextView).text = erg
                    textViewDateTotal.text = erg
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
         * @return A new instance of fragment ShipperIncomeMonthFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShipperIncomeMonthFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}