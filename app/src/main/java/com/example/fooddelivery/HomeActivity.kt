package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.support.v7.widget.LinearLayoutManager
//import android.widget.LinearLayout
import com.example.fooddelivery.`object`.PreviousOrdersClass
import com.example.fooddelivery.`object`.RestaurantClass
import com.example.fooddelivery.checkout.CustomAdapterPreviousOrders
import com.example.fooddelivery.checkout.CustomAdapterRestaurant
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        see_moreButton.setOnClickListener {
            val intent:Intent = Intent(this,PopularInYourAreaActivity::class.java)
            startActivity(intent)
        }

        var arrayListRestau: ArrayList<RestaurantClass> = ArrayList()
        arrayListRestau.add(
            RestaurantClass(R.drawable.square_mint,
            "Shaking Beef Tri-tip",
            "Burger-Chicken-Cake",
            "5",
            "3 km")
        )

        arrayListRestau.add(
            RestaurantClass(R.drawable.square_mint,
            "Shaking Beef Tri-tip",
            "Burger-Chicken-Cake",
            "5",
            "3 km")
        )
        arrayListRestau.add(
            RestaurantClass(R.drawable.square_mint,
            "Shaking Beef Tri-tip",
            "Burger-Chicken-Cake",
            "5",
            "3 km")
        )
        arrayListRestau.add(
            RestaurantClass(R.drawable.square_mint,
            "Shaking Beef Tri-tip",
            "Burger-Chicken-Cake",
            "5",
            "3 km")
        )
        arrayListRestau.add(
            RestaurantClass(R.drawable.square_mint,
            "Shaking Beef Tri-tip",
            "Burger-Chicken-Cake",
            "5",
            "3 km")
        )
        arrayListRestau.add(
            RestaurantClass(R.drawable.square_mint,
            "Shaking Beef Tri-tip",
            "Burger-Chicken-Cake",
            "5",
            "3 km")
        )

        restaurantListView.adapter = CustomAdapterRestaurant(this, arrayListRestau)

        var arrayListOrders: ArrayList<PreviousOrdersClass> = ArrayList()
        arrayListOrders.add(
            PreviousOrdersClass(R.drawable.square_mint,
                "Shaking Beef Tri-tip")
        )
        arrayListOrders.add(
            PreviousOrdersClass(R.drawable.square_mint,
                "Shaking Beef Tri-tip")
        )
        arrayListOrders.add(
            PreviousOrdersClass(R.drawable.square_mint,
                "Shaking Beef Tri-tip")
        )
        arrayListOrders.add(
            PreviousOrdersClass(R.drawable.square_mint,
                "Shaking Beef Tri-tip")
        )
        arrayListOrders.add(
            PreviousOrdersClass(R.drawable.square_mint,
                "Shaking Beef Tri-tip")
        )
//        previousOrdersRecyclerView.layoutManager = LinearLayoutManager(this)
        previousOrdersRecyclerView.adapter = CustomAdapterPreviousOrders(this, arrayListOrders)

        var arrayListRestauFavorite: ArrayList<RestaurantClass> = ArrayList()
        arrayListRestauFavorite.add(
            RestaurantClass(R.drawable.square_mint,
                "Minh",
                "Burger-Chicken-Cake",
                "5",
                "3 km")
        )
        arrayListRestauFavorite.add(
            RestaurantClass(R.drawable.square_mint,
                "Hiếu",
                "Burger-Chicken-Cake",
                "5",
                "3 km")
        )
        arrayListRestauFavorite.add(
            RestaurantClass(R.drawable.square_mint,
                "Nè",
                "Burger-Chicken-Cake",
                "5",
                "3 km")
        )
        restaurantFavoriteListView.adapter =  CustomAdapterRestaurant(this,arrayListRestauFavorite)
    }
}