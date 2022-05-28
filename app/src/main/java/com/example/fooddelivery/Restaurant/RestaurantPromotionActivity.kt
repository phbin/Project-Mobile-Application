package com.example.fooddelivery.Restaurant

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.Customer.CustomAdapterPromotion
import com.example.fooddelivery.R
import com.example.fooddelivery.model.PromotionClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_restaurant_promotion.*

class RestaurantPromotionActivity : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CustomAdapterPromotion.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_promotion)
        var sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val phoneNumber = sharedPreferences.getString("ID", "")
        btnBack.setOnClickListener {
            val intent = Intent(this, RestaurantMenuActivity::class.java)
            finish()
            startActivity(intent)
        }

        btnAddPromotion.setOnClickListener {
            val intent = Intent(this, RestaurantAddingPromotionActivity::class.java)
            startActivity(intent)
        }


        var promotionItems: ArrayList<PromotionClass> = ArrayList()
        var fb = FirebaseFirestore.getInstance().collection("Restaurant")
            .document("0393751403")
            .collection("promotion")
        fb.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (i in it.result) {
                    promotionItems.add(PromotionClass("" + i.data.getValue("code").toString(),
                        "" + i.data.getValue("description").toString(),
                        "" + i.data.getValue("expiryDate").toString(),
                        "" + i.data.getValue("name").toString(),
                        "" + i.data.getValue("value").toString(),
                        "" + i.data.getValue("image").toString()))
                }
            }
            layoutManager = LinearLayoutManager(this)
            recyclerViewMenu.layoutManager = layoutManager

            adapter = CustomAdapterPromotion(promotionItems)
            recyclerViewMenu.adapter = adapter

//            (adapter as CustomAdapterPromotion).onClick = {
//                val intent = Intent(this, RestaurantPromotionDetailActivity::class.java)
//                intent.putExtra("promotionName", it)
//                startActivity(intent)
//            }
        }
    }
}
