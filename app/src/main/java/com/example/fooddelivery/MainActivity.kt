package com.example.fooddelivery

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.fooddelivery.checkout.CheckOutActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        val role = sharedPreferences.getString("ROLE", "")
        var isRemember = sharedPreferences.getBoolean("REMEMBER", false)

        if(isRemember && role == "Customer"){
            Toast.makeText(this, "Đăng nhập bằng Custmoer nè", Toast.LENGTH_SHORT).show()
        }
        else if(isRemember && role == "Shipper"){
            val intent = Intent(this, ShipperActivity::class.java)
            startActivity(intent)
            finish()
        }
        else if(isRemember && role == "Restaurant"){
            Toast.makeText(this, "Đăng nhập bằng Restaurant nè", Toast.LENGTH_SHORT).show()
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