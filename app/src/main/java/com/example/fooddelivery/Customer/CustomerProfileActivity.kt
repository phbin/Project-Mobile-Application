package com.example.fooddelivery.Customer

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fooddelivery.MainActivity
import com.example.fooddelivery.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_customer_profile.*

class CustomerProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_profile)
        val sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val phoneNumber = sharedPreferences.getString("ID", "")
        textViewPhoneNumber.text=phoneNumber

        var fb = FirebaseFirestore.getInstance().collection("Customer")
        fb.get().addOnCompleteListener {
            for(i in it.result){
                if(i.id == phoneNumber)
                {
                    textViewAddress.text = i.data.getValue("address").toString()
                    textViewEmail.text = i.data.getValue("email").toString()
                    textName.text = i.data.getValue("displayName").toString()
                    return@addOnCompleteListener
                }
                continue
            }
        }
        backButton.setOnClickListener{
            finish()
        }
    }
}