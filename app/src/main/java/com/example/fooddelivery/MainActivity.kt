package com.example.fooddelivery

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fooddelivery.Customer.CheckOutActivity
import com.example.fooddelivery.Customer.HomeActivity
import com.example.fooddelivery.Restaurant.RestaurantHomeActivity
import com.example.fooddelivery.Shipper.ShipperActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE)

        val role = sharedPreferences.getString("ROLE", "")
        var isRemember = sharedPreferences.getBoolean("REMEMBER", false)

        if(isRemember && role == "Customer"){
            val intent = Intent(this, SplashScreenActivity::class.java)
            startActivity(intent)
            finish()
        }
        else if(isRemember && role == "Shipper"){
            val intent = Intent(this, SplashScreenActivity::class.java)
            startActivity(intent)
            finish()
        }
        else if(isRemember && role == "Restaurant"){
            val intent = Intent(this, SplashScreenActivity::class.java)
            startActivity(intent)
            finish()
        }
        btnCreateAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
//            val intent = Intent(this, RestaurantHomeActivity::class.java)
//            startActivity(intent)
        }

        btnSignIn.setOnClickListener {
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }
    }
}