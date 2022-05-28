package com.example.fooddelivery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fooddelivery.`object`.RestaurantMenuList
import kotlinx.android.synthetic.main.activity_restaurant_menu_detail.*

class RestaurantMenuDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_menu_detail)

        btnBack.setOnClickListener {
            finish()
        }

        val menuName = intent.getParcelableExtra<RestaurantMenuList>("menuName")
        if(menuName!=null){
            editTextMenuName.setText(menuName.menuName)
        }
    }
}