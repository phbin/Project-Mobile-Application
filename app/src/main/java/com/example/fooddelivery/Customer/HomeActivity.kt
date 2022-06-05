package com.example.fooddelivery.Customer

//import android.support.v7.widget.LinearLayoutManager
//import android.widget.LinearLayout
import Favorite_Restaurant_RecyclerView
import Previous_Orders_RecyclerView
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.SignInActivity
import com.example.fooddelivery.model.PreviousOrdersClass
import com.example.fooddelivery.model.RestaurantClass
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*
import kotlin.collections.ArrayList


class HomeActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapterCategory: Category_RecyclerView
    private lateinit var adapterPreviousOrders: Previous_Orders_RecyclerView
    private lateinit var adapterFavoriteRestaurant: Favorite_Restaurant_RecyclerView
    private lateinit var adapterExploreMore: Explore_More_RecyclerView

    lateinit var fusedLocationProviderClient:FusedLocationProviderClient

    private lateinit var arrayListRestau: ArrayList<RestaurantClass>
    var latitude=0.0
    var longitude=0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        latitude=intent.getDoubleExtra("lat",0.0)
        longitude=intent.getDoubleExtra("long",0.0)
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
        if(latitude==0.0&&longitude==0.0) {
            CheckLocationPermission()
        }else{
            var address=Geocoder(this,Locale.getDefault()).getFromLocation(latitude,longitude,2).get(0).featureName+" "+
                    Geocoder(this,Locale.getDefault()).getFromLocation(latitude,longitude,2).get(0).thoroughfare
            findLocation.setText("$address")
        }
        findLocation.setOnClickListener{
            var intent=Intent(this,SearchLocationActivity::class.java)
            intent.putExtra("lat",latitude)
            intent.putExtra("long",longitude)
            startActivity(intent)
        }
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
            val intent = Intent(this,PopularInYourAreaActivity::class.java)
            startActivity(intent)
        }

        searchView.setOnClickListener {
            val intent= Intent(this,SearchRestaurantActivity::class.java)
            startActivity(intent)
        }


        arrayListRestau = ArrayList()
        arrayListRestau.add(
            RestaurantClass(R.drawable.square_mint,
                "Shaking Beef Tri-tip",
                "Hủ tiếu gà",
                "3 km")
        )

        arrayListRestau.add(
            RestaurantClass(R.drawable.square_mint,
                "Shaking Beef Tri-tip",
                "Bánh canh sườn heo chua ngọt sốt cay",
                "3 km")
        )
        arrayListRestau.add(
            RestaurantClass(R.drawable.square_mint,
                "Shaking Beef Tri-tip",
                "Vịt tiềm thuốc ngủ",
                "3 km")
        )
        arrayListRestau.add(
            RestaurantClass(R.drawable.square_mint,
                "Shaking Beef Tri-tip",
                "Chân gà nấu phô trương",

                "3 km")
        )
        arrayListRestau.add(
            RestaurantClass(R.drawable.square_mint,
                "Shaking Beef Tri-tip",
                "Ngỗng hấp hối",

                "3 km")
        )
        arrayListRestau.add(
            RestaurantClass(R.drawable.square_mint,
                "Shaking Beef Tri-tip",
                "Nhện chiên giòn giã nhuyễn nấu với nước cốt chanh dây",

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
        //previousOrdersRecyclerView.layoutManager = LinearLayoutManager(this)

        btnCart.setOnClickListener{
            val intent= Intent(this,CartActivity::class.java)
            startActivity(intent)
        }

        btnMenu.setOnClickListener{
            val intent= Intent(this,CustomerMenu::class.java)
            startActivity(intent)
        }
        var arrayListRestauFavorite: ArrayList<RestaurantClass> = ArrayList()
        arrayListRestauFavorite.add(
            RestaurantClass(R.drawable.square_mint,
                "Minh",
                "Burger-Chicken-Cake",
                "3 km")
        )
        arrayListRestauFavorite.add(
            RestaurantClass(R.drawable.square_mint,
                "Hiếu",
                "Burger-Chicken-Cake",

                "3 km")
        )
        arrayListRestauFavorite.add(
            RestaurantClass(R.drawable.square_mint,
                "Nè",
                "Burger-Chicken-Cake",
                "3 km")
        )

    }

    @SuppressLint("SetTextI18n")
    private fun CheckLocationPermission(){
    val task=fusedLocationProviderClient.lastLocation
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED
            &&ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),101)
            return
        }
        task.addOnSuccessListener {
            if(it!=null){
                latitude=it.latitude
                longitude=it.longitude

                var address=Geocoder(this,Locale.getDefault()).getFromLocation(latitude,longitude,2).get(0).featureName+" "+
                        Geocoder(this,Locale.getDefault()).getFromLocation(latitude,longitude,2).get(0).thoroughfare
                findLocation.setText("$address")
//                Log.d("AAAA","hello"+it.latitude+it.longitude)
//
//                Toast.makeText(this,"hello"+it.latitude+it.longitude,Toast.LENGTH_LONG).show()
            }
            else{
                latitude=10.856220
                longitude=106.767600

                var address=Geocoder(this,Locale.getDefault()).getFromLocation(latitude,longitude,2).get(0).featureName+" "+
                        Geocoder(this,Locale.getDefault()).getFromLocation(latitude,longitude,2).get(0).thoroughfare
                findLocation.setText("$address")
            }

        }
    }
}