package com.example.fooddelivery.Customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fooddelivery.MainActivity
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
            val intent = Intent(this, CustomerOrderHistoryActivity::class.java)
            startActivity(intent)
        }
        textHistory.setOnClickListener{
            val intent = Intent(this, CustomerOrderHistoryActivity::class.java)
            startActivity(intent)
        }
        btnProfile.setOnClickListener{
            val intent = Intent(this, CustomerProfileActivity::class.java)
            startActivity(intent)
        }
        textProfile.setOnClickListener{
            val intent = Intent(this, CustomerProfileActivity::class.java)
            startActivity(intent)
        }
        btnLogout.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        btnLogout.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }
}