package com.example.fooddelivery.Customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fooddelivery.R
import com.example.fooddelivery.model.RestaurantPopularClass
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_popular_in_your_area.*

class PopularInYourAreaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_in_your_area)
        backButton.setOnClickListener {
            val intent: Intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }

        var arrayListRestau: ArrayList<RestaurantPopularClass> = ArrayList()
        arrayListRestau.add(
            RestaurantPopularClass(
                R.drawable.square_mint,
                "Shaking Beef Tri-tip",
                "5",
                "3 km"
            )
        )

        arrayListRestau.add(
            RestaurantPopularClass(
                R.drawable.square_mint,
                "Shaking Beef Tri-tip",
                "5",
                "3 km"
            )
        )
        arrayListRestau.add(
            RestaurantPopularClass(
                R.drawable.square_mint,
                "Shaking Beef Tri-tip",
                "5",
                "3 km"
            )
        )
        arrayListRestau.add(
            RestaurantPopularClass(
                R.drawable.square_mint,
                "Shaking Beef Tri-tip",
                "5",
                "3 km"
            )
        )

        arrayListRestau.add(
            RestaurantPopularClass(
                R.drawable.square_mint,
                "Shaking Beef Tri-tip",
                "5",
                "3 km"
            )
        )
        arrayListRestau.add(
            RestaurantPopularClass(
                R.drawable.square_mint,
                "Shaking Beef Tri-tip",
                "5",
                "3 km"
            )
        )
        arrayListRestau.add(
            RestaurantPopularClass(
                R.drawable.square_mint,
                "Shaking Beef Tri-tip",
                "5",
                "3 km"
            )
        )

        arrayListRestau.add(
            RestaurantPopularClass(
                R.drawable.square_mint,
                "Shaking Beef Tri-tip",
                "5",
                "3 km"
            )
        )
        arrayListRestau.add(
            RestaurantPopularClass(
                R.drawable.square_mint,
                "Shaking Beef Tri-tip",
                "5",
                "3 km"
            )
        )
        restaurantPopularGridView.adapter = CustomAdapterRestaurantPopular(this, arrayListRestau)
    }
}