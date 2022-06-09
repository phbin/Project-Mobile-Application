package com.example.fooddelivery.Customer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.Restaurant.RestaurantPromotionDetailActivity
import com.example.fooddelivery.model.PromotionClass
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_check_out.*
import kotlinx.android.synthetic.main.activity_fragment_promotion.*
import kotlinx.android.synthetic.main.activity_restaurant_promotion.*
import kotlinx.android.synthetic.main.activity_sign_in.*


class FragmentPromotion : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CustomAdapterPromotion.ViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_promotion)
        btnBackPromotion.setOnClickListener {
            var intent = Intent(this, CheckOutActivity::class.java)
            startActivity(intent)
        }
        var arrayListProm: ArrayList<PromotionClass> = ArrayList()

        var fb = FirebaseFirestore.getInstance().collection("Restaurant")
            .document("0393751403")
            .collection("promotion")

        fb.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (i in it.result) {
                    arrayListProm.add(PromotionClass(
                        "" + i.data.getValue("description").toString(),
                        "" + i.data.getValue("expiryDate").toString(),
                        "" + i.data.getValue("name").toString(),
                        "" + i.data.getValue("value").toString()))
                }
            }
            layoutManager = LinearLayoutManager(this)
            promotionListView.layoutManager = layoutManager

            adapter = CustomAdapterPromotion(arrayListProm)
            promotionListView.adapter = adapter

            (adapter as CustomAdapterPromotion).setOnItemClickListener(object :
                CustomAdapterPromotion.onItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@FragmentPromotion, CheckOutActivity::class.java)
                    intent.putExtra("promotionPosition", ""+position)
                    startActivity(intent)
                }
            })
        }
    }
}


