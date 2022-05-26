package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_restaurant_promotion.*

class RestaurantPromotionActivity : AppCompatActivity() {
    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RestaurantPromotionAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_promotion)

        btnBack.setOnClickListener {
            val intent = Intent(this, RestaurantHomeActivity::class.java)
            startActivity(intent)
        }

        btnAddPromotion.setOnClickListener {
            val intent = Intent(this, RestaurantAddingPromotionActivity::class.java)
            startActivity(intent)
        }


        var promotionItems : ArrayList<RestaurantPromotionList> = ArrayList()

        promotionItems.add(RestaurantPromotionList("Giảm 10%", "20/02/2022"))
        promotionItems.add(RestaurantPromotionList("Giảm 15%","20/02/2022"))
        promotionItems.add(RestaurantPromotionList("Giảm 10%", "20/02/2022"))
        promotionItems.add(RestaurantPromotionList("Giảm 15%","20/02/2022"))
        promotionItems.add(RestaurantPromotionList("Giảm 10%", "20/02/2022"))
        promotionItems.add(RestaurantPromotionList("Giảm 15%","20/02/2022"))

        layoutManager = LinearLayoutManager(this)
        recyclerViewMenu.layoutManager = layoutManager

        adapter = RestaurantPromotionAdapter(promotionItems)
        recyclerViewMenu.adapter = adapter

        (adapter as RestaurantPromotionAdapter).onItemClick = {
            val intent = Intent(this, RestaurantPromotionDetailActivity::class.java)
            intent.putExtra("promotionName", it)
            startActivity(intent)
        }
    }
}