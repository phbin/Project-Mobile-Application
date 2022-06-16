package com.example.fooddelivery.Customer

import Favorite_Restaurant_RecyclerView
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.model.CustomerCategory
import com.example.fooddelivery.model.RestaurantClass
import com.example.fooddelivery.model.RestaurantPopularClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_popular_in_your_area.*

class PopularInYourAreaActivity : AppCompatActivity() {
    var latitude = 0.0
    var longitude = 0.0

    private lateinit var recyclerviewNearRes: RecyclerView
    private lateinit var adapterFavoriteRestaurant: Favorite_Restaurant_RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_in_your_area)
        backButton.setOnClickListener {
            val intent: Intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("lat",latitude)
            intent.putExtra("long",longitude)
            startActivity(intent)
        }
        var result = FloatArray(10)
        latitude=intent.getDoubleExtra("lat",0.0)
        longitude=intent.getDoubleExtra("long",0.0)
        var fbRes = FirebaseFirestore.getInstance().collection("Restaurant")
        var arrayNearRes: ArrayList<RestaurantClass> = ArrayList()

        fbRes.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (i in it.result) {
                    Location.distanceBetween(i.data.getValue("latitude").toString().toDouble(),
                        i.data.getValue("longitude").toString().toDouble(),
                        latitude,
                        longitude, result)
                    if(result[0]/1000<5){
                        arrayNearRes.add(RestaurantClass(i.id, ""+i.data.getValue("image"), ""+i.data.getValue("displayName"), String.format("%.1f", result[0]/1000)+"km"))
                    }
                }
                recyclerviewNearRes = findViewById(R.id.restaurantPopularGridView)
                adapterFavoriteRestaurant = Favorite_Restaurant_RecyclerView(this, arrayNearRes)
                recyclerviewNearRes.layoutManager = GridLayoutManager(this, 2)
                recyclerviewNearRes.adapter = adapterFavoriteRestaurant

                adapterFavoriteRestaurant.setOnIntemClickListener(object :
                    Favorite_Restaurant_RecyclerView.onIntemClickListener {
                    override fun onClickItem(position: Int) {
                        val intent = Intent(this@PopularInYourAreaActivity,
                            RestaurantActivity::class.java)
                        intent.putExtra("idRes", arrayNearRes[position].idRes)
                        intent.putExtra("lat", latitude)
                        intent.putExtra("long", longitude)
                        startActivity(intent)
                    }
                })
            }
        }
    }
}