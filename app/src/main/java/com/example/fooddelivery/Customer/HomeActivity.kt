package com.example.fooddelivery.Customer

//import android.support.v7.widget.LinearLayoutManager
//import android.widget.LinearLayout
import Favorite_Restaurant_RecyclerView
import Previous_Orders_RecyclerView
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.SignInActivity
import com.example.fooddelivery.model.PreviousOrdersClass
import com.example.fooddelivery.model.RestaurantClass
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapterCategory: Category_RecyclerView
    private lateinit var adapterPreviousOrders: Previous_Orders_RecyclerView
    private lateinit var adapterFavoriteRestaurant: Favorite_Restaurant_RecyclerView
    private lateinit var adapterExploreMore: Explore_More_RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerview = findViewById(R.id.categoryRecyclerView)
        adapterCategory = Category_RecyclerView()
        recyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerview.adapter = adapterCategory

        recyclerview = findViewById(R.id.previousOrdersRecyclerView)
        adapterPreviousOrders = Previous_Orders_RecyclerView()
        recyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerview.adapter = adapterPreviousOrders

        recyclerview = findViewById(R.id.favoriteRestaurantRecyclerView)
        adapterFavoriteRestaurant = Favorite_Restaurant_RecyclerView()
        recyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerview.adapter = adapterFavoriteRestaurant

        recyclerview = findViewById(R.id.exploreMoreRecyclerView)
        adapterExploreMore = Explore_More_RecyclerView()
        recyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerview.adapter = adapterExploreMore

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

        btnMenu.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

    }
}