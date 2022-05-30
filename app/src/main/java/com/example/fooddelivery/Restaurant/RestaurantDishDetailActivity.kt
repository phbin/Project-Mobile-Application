package com.example.fooddelivery.Restaurant

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fooddelivery.R
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_restaurant_dish_detail.*
import kotlinx.android.synthetic.main.activity_restaurant_dish_detail.btnBack

class RestaurantDishDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_dish_detail)

        var sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val phoneNumber = sharedPreferences.getString("ID", "")
        btnBack.setOnClickListener {
            finish()
        }
        val position = intent.getStringExtra("dishName")
        val categoryName = intent.getStringExtra("categoryName")

        var fb = FirebaseFirestore.getInstance().collection("Restaurant")
            .document("" + phoneNumber)
            .collection("categoryMenu")
            .document("" + categoryName)
            .collection("Item")
        fb.get().addOnCompleteListener {
            if (it.isSuccessful) {
                it.result.count()
                for ((index, i) in it.result.withIndex()) {
                    if (index.toString() == position) {
                        textName.setText(i.data.getValue("name").toString())
                        textPrice.setText(i.data.getValue("price").toString())
                        textSize.setText(i.data.getValue("size").toString())
                        //Toast.makeText(this,""+i.data.getValue("image"),Toast.LENGTH_LONG).show()
                        var url = "" + i.data.getValue("image")
                        Picasso.get()
                            .load(url)
                            .into(img)
                    }
                }
            }
        }
    }
}