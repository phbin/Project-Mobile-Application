package com.example.fooddelivery.Customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fooddelivery.R
import com.example.fooddelivery.Restaurant.RestaurantStatisticsActivity
import kotlinx.android.synthetic.main.activity_customer_menu.*

class CustomerMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_menu)
        btnBack.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        btnHome.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        textHome.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        btnHistory.setOnClickListener{

        }
        textHistory.setOnClickListener{

        }
        btnProfile.setOnClickListener{
            val intent = Intent(this, CustomerProfileActivity::class.java)
            startActivity(intent)
        }
        textProfile.setOnClickListener{
            val intent = Intent(this, CustomerProfileActivity::class.java)
            startActivity(intent)
        }
    }
}