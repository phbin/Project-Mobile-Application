package com.example.fooddelivery

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fooddelivery.`object`.RestaurantMenuList
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.*
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.btnBack
import kotlinx.android.synthetic.main.activity_restaurant_promotion_detail.*

class RestaurantPromotionDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_promotion_detail)

        var promotionName = intent.getParcelableExtra<RestaurantPromotionList>("promotionName")

        if(promotionName!=null){
            val editTextPromotionName : EditText = findViewById(R.id.editTextPromotionName)

            editTextPromotionName.setText(promotionName.name)
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}