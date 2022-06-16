package com.example.fooddelivery

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fooddelivery.Customer.HomeActivity
import com.example.fooddelivery.Restaurant.RestaurantHomeActivity
import com.example.fooddelivery.Shipper.ShipperActivity

class SplashScreenActivity : AppCompatActivity() {
    lateinit var sharedPreferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        var role = sharedPreferences.getString("ROLE","")


        if(role == "Customer"){
            Handler().postDelayed({
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }
        else if(role == "Shipper"){
            Handler().postDelayed({
                val intent = Intent(this, ShipperActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }
        else if(role == "Restaurant"){
            Handler().postDelayed({
                val intent = Intent(this, RestaurantHomeActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }

    }
}