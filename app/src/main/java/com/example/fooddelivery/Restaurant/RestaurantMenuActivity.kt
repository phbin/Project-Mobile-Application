package com.example.fooddelivery.Restaurant

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fooddelivery.Customer.HomeActivity
import com.example.fooddelivery.MainActivity
import com.example.fooddelivery.R
import kotlinx.android.synthetic.main.activity_restaurant_menu.*

class RestaurantMenuActivity : AppCompatActivity() {

    lateinit var preferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_menu)

        btnBack.setOnClickListener {
            val intent = Intent(this, RestaurantHomeActivity::class.java)
            finish()
            startActivity(intent)
        }

        textViewResManagement.setOnClickListener {
            val intent = Intent(this, RestaurantResManagementActivity::class.java)
            startActivity(intent)
        }
        textViewMenuManagement.setOnClickListener {
            val intent = Intent(this, RestaurantMenuManagementActivity::class.java)
            startActivity(intent)
        }
        textViewPromotion.setOnClickListener {
            val intent = Intent(this, RestaurantPromotionActivity::class.java)
            startActivity(intent)
        }
        textViewStatistics.setOnClickListener {
            val intent = Intent(this, RestaurantStatisticsActivity::class.java)
            startActivity(intent)
        }

        btnResManagement.setOnClickListener {
            val intent = Intent(this, RestaurantResManagementActivity::class.java)
            startActivity(intent)
        }
        btnMenuManagement.setOnClickListener {
            val intent = Intent(this, RestaurantMenuManagementActivity::class.java)
            startActivity(intent)
        }
        btnPromotion.setOnClickListener {
            val intent = Intent(this, RestaurantPromotionActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        btnStatistic.setOnClickListener {
            val intent = Intent(this, RestaurantStatisticsActivity::class.java)
            startActivity(intent)
        }
        btnLogout.setOnClickListener {
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.clear()
            editor.commit()

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
        textViewLogout.setOnClickListener {
            val sharedPreferences = this.getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()
            editor.commit()

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }
}