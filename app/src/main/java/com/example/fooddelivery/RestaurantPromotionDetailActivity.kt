package com.example.fooddelivery

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fooddelivery.`object`.RestaurantMenuList
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.*

class RestaurantPromotionDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_promotion_detail)

        val promotionName = intent.getParcelableExtra<RestaurantMenuList>("promotionName")
        Toast.makeText(this, "$promotionName",Toast.LENGTH_SHORT).show()
        if(promotionName!=null){
            editTextPromotionName.setText(promotionName.menuName)
        }
    }
}