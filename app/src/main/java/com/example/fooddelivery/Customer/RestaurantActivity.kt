package com.example.fooddelivery.Customer


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.model.RestaurantAppertizer
import com.example.fooddelivery.model.RestaurantPopular
import kotlinx.android.synthetic.main.activity_restaurant.*

class RestaurantActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapterRestaurantPopular: Restaurant_Popular_RecyclerView
    private lateinit var adapterRestaurantAppertizer: Restaurant_Appertizer_RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        recyclerview = findViewById(R.id.restaurantPopularRecyclerView)
        adapterRestaurantPopular = Restaurant_Popular_RecyclerView()
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        recyclerview.adapter = adapterRestaurantPopular

        recyclerview = findViewById(R.id.restaurantAppertizerRecyclerView)
        adapterRestaurantAppertizer = Restaurant_Appertizer_RecyclerView()
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recyclerview.adapter = adapterRestaurantAppertizer

        var arrayListRestaurantPopular: ArrayList<RestaurantPopular> = ArrayList()
        arrayListRestaurantPopular.add(
            RestaurantPopular(R.drawable.square_mint,"Hieu",10000)
        )
        arrayListRestaurantPopular.add(
            RestaurantPopular(R.drawable.square_mint,"Hieu", 10000)
        )
        arrayListRestaurantPopular.add(
            RestaurantPopular(R.drawable.square_mint,"Hieu", 10000)
        )

        var arrayListRestaurantAppertizer: ArrayList<RestaurantAppertizer> = ArrayList()
        arrayListRestaurantAppertizer.add(
            RestaurantAppertizer(R.drawable.square_mint,"Hieu", "Trai thang", 10000)
        )
        arrayListRestaurantAppertizer.add(
            RestaurantAppertizer(R.drawable.square_mint,"Hieu", "Trai thang", 10000)
        )
        arrayListRestaurantAppertizer.add(
            RestaurantAppertizer(R.drawable.square_mint,"Hieu", "Trai thang", 10000)
        )
        btnCart.setOnClickListener{
            val intent= Intent(this,CartActivity::class.java)
            startActivity(intent)
        }
    }
}