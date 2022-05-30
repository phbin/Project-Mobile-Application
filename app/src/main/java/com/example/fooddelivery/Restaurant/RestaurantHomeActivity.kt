package com.example.fooddelivery.Restaurant

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fooddelivery.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_restaurant_home.*
import kotlinx.android.synthetic.main.activity_restaurant_home.btnStatus
import kotlinx.android.synthetic.main.activity_restaurant_home.tabLayout
import kotlinx.android.synthetic.main.activity_restaurant_home.viewPager
import kotlinx.android.synthetic.main.activity_shipper.*

class RestaurantHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_home)

        viewPager.adapter = RestaurantHomeAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

        var preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idRestaurant = preferences.getString("ID","")

        btnMenu.setOnClickListener {
            val intent = Intent(this, RestaurantMenuActivity::class.java)
            startActivity(intent)
        }

        var fb = FirebaseFirestore.getInstance().collection("Restaurant")
        fb.get().addOnCompleteListener {
            for(i in it.result){
                if(i.id == preferences.getString("ID", ""))
                {
                    if(i.data.getValue("status").toString() == "Closed"){
                        btnStatus.setTextColor(Color.parseColor("#828282"))
                        btnStatus.text = i.data.getValue("status").toString()
                        val icon = applicationContext.resources.getDrawable(R.drawable.ic_status_closed)
                        btnStatus.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
                    }
                    else
                    {
                        btnStatus.text = i.data.getValue("status").toString()
                        btnStatus.setTextColor(Color.parseColor("#FFFFFF"))
                        val icon = applicationContext.resources.getDrawable(R.drawable.ic_shipper_status)
                        btnStatus.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
                    }
                    return@addOnCompleteListener
                }
                continue
            }
        }

        btnStatus.setOnClickListener {
//            val id = preferences.getString("ID", "")
            if(btnStatus.text == "Open")
            {

                btnStatus.text="Closed"
                btnStatus.setTextColor(Color.parseColor("#828282"))
                val icon = applicationContext.resources.getDrawable(R.drawable.ic_status_closed)
                btnStatus.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
                var fb = FirebaseFirestore.getInstance().collection("Restaurant").document("$idRestaurant").update("status", "Closed")
            }
            else{
                btnStatus.text="Open"
//                preferences.getString("STATUS", btnStatus.text.toString())
                btnStatus.setTextColor(Color.parseColor("#FFFFFF"))
                val icon = applicationContext.resources.getDrawable(R.drawable.ic_shipper_status)
                btnStatus.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
                var fb = FirebaseFirestore.getInstance().collection("Restaurant").document("$idRestaurant").update("status", "Open")
            }
        }
    }
}