package com.example.fooddelivery.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.BaseAdapter
import android.widget.Toast
import com.example.fooddelivery.R
import com.example.fooddelivery.`object`.PromotionClass
import com.example.fooddelivery.databinding.ActivityFragmentPromotionBinding
import com.example.fooddelivery.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_check_out.*
import kotlinx.android.synthetic.main.activity_fragment_promotion.*

class FragmentPromotion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_promotion)

        btnBackPromotion.setOnClickListener {
            var intent = Intent(this, CheckOutActivity::class.java)
            startActivity(intent)
        }
        var arrayListProm: ArrayList<PromotionClass> = ArrayList()
        arrayListProm.add(PromotionClass(R.drawable.iccoupon,
            "Promotion 15% for Loteria",
            "Expried on 11 May 2021"))
        arrayListProm.add(PromotionClass(R.drawable.iccoupon,
            "Promotion 15% for Loteria",
            "Expried on 11 May 2021"))
        arrayListProm.add(PromotionClass(R.drawable.iccoupon,
            "Promotion 15% for Loteria",
            "Expried on 11 May 2021"))
        arrayListProm.add(PromotionClass(R.drawable.iccoupon,
            "Promotion 15% for Loteria",
            "Expried on 11 May 2021"))
        arrayListProm.add(PromotionClass(R.drawable.iccoupon,
            "Promotion 15% for Loteria",
            "Expried on 11 May 2021"))
        arrayListProm.add(PromotionClass(R.drawable.iccoupon,
            "Promotion 15% for Loteria",
            "Expried on 11 May 2021"))
        arrayListProm.add(PromotionClass(R.drawable.iccoupon,
            "Promotion 15% for Loteria",
            "Expried on 11 May 2021"))
        arrayListProm.add(PromotionClass(R.drawable.iccoupon,
            "Promotion 15% for Loteria",
            "Expried on 11 May 2021"))
        promotionListView.adapter = CustomAdapterPromotion(this, arrayListProm)

//        promotionListView.isClickable = true
//
//        lateinit var binding: ActivityFragmentPromotionBinding
//        binding = ActivityFragmentPromotionBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        promotionListView.setOnItemClickListener { parent, view, position, id ->
//            val ic: Int = arrayListProm[position].ic
//            val name: String = arrayListProm[position].name
//            val date: String = arrayListProm[position].date
//
//            val intent = Intent(this, CheckOutActivity::class.java)
//            intent.putExtra("name",name)
//            intent.putExtra("ic",ic)
//
//            startActivity(intent)
//        }
    }
}

