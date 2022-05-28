package com.example.fooddelivery.Restaurant

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.fooddelivery.R
import com.example.fooddelivery.model.RestaurantPromotionList
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.btnBack

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