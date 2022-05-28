package com.example.fooddelivery

import Restaurant_Appertizer_RecyclerView
import Restaurant_Popular_RecyclerView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.Adapter.Category_RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.`object`.RestaurantAppertizer
import com.example.fooddelivery.`object`.RestaurantClass
import com.example.fooddelivery.`object`.RestaurantPopular

class DishDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_detail)



    }
}