package com.example.fooddelivery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.`object`.ShipperOrderHistory
import com.example.fooddelivery.checkout.ShipperAdapterHistory
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
        recyclerView = view.findViewById(R.id.recyclerViewHistory)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)

        var orderArray : ArrayList<ShipperOrderHistory> = ArrayList()

        orderArray.add(ShipperOrderHistory("001", "Linh Trung, Thủ Đức", "Thế Vĩ", "1 món", "200.000đ"))
        orderArray.add(ShipperOrderHistory("002", "Kiên Giang", "Vĩ", "2 món", "250.000đ"))
        orderArray.add(ShipperOrderHistory("003", "Cần Thơ", "Huỳnh", "5 món", "100.000đ"))
        orderArray.add(ShipperOrderHistory("004", "Long An", "Thế", "3 món", "300.000đ"))


        recyclerView.adapter = ShipperAdapterHistory(requireActivity().applicationContext, orderArray )
    }
}