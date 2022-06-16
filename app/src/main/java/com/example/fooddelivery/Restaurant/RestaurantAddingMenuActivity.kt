package com.example.fooddelivery.Restaurant

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_restaurant_adding_menu.*
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.example.fooddelivery.R
import com.example.fooddelivery.model.RestaurantMenuList
import com.google.firebase.firestore.FirebaseFirestore

class RestaurantAddingMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_adding_menu)
        var sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val phoneNumber = sharedPreferences.getString("ID","")

        btnContinue.setOnClickListener {
            if (editTextMenuName.text.toString() != "") {
                btnContinue.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                var o: MutableMap<String, Any> = HashMap()
                o["name"] = editTextMenuName.text.toString()
                var fb = FirebaseFirestore.getInstance().collection("Restaurant")
                    .document("" + phoneNumber)
                    .collection("categoryMenu")
                fb.document("" + editTextMenuName.text).set(o)
                    .addOnSuccessListener {
                        val intent = Intent(this, RestaurantMenuManagementActivity::class.java)
                        finish()
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Please try again!!", Toast.LENGTH_SHORT).show()
                    }
            } else {
                btnContinue.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                editTextMenuName.error = "Please enter Name"
            }
        }
        btnBack.setOnClickListener {
            finish()
        }
    }
}