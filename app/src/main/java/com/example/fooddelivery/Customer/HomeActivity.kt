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
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.fooddelivery.R
import com.example.fooddelivery.SignInActivity
import com.example.fooddelivery.model.CustomerCategory
import com.example.fooddelivery.model.PreviousOrdersClass
import com.example.fooddelivery.model.RestaurantClass
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*
import kotlin.collections.ArrayList


class HomeActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var recyclerviewExplore: RecyclerView
    private lateinit var recyclerviewPrevious: RecyclerView
    private lateinit var adapterCategory: Category_RecyclerView
    private lateinit var adapterPreviousOrders: Previous_Orders_RecyclerView
    private lateinit var adapterFavoriteRestaurant: Favorite_Restaurant_RecyclerView
    private lateinit var adapterExploreMore: Explore_More_RecyclerView

    lateinit var fusedLocationProviderClient:FusedLocationProviderClient

    private lateinit var arrayListRestau: ArrayList<RestaurantClass>
    var latitude=0.0
    var longitude=0.0

    lateinit var imageSlider : ImageSlider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idCustomer = preferences.getString("ID","")

        imageSlider = findViewById(R.id.imageSlider)
        var slideModels : ArrayList<SlideModel> = ArrayList()
            slideModels.add(SlideModel("https://yt3.ggpht.com/ytc/AKedOLRswKCygRWK-eecd1Gt-DrlEhv79jYXlNMx535E=s900-c-k-c0x00ffffff-no-rj", ScaleTypes.FIT))
        slideModels.add(SlideModel("https://younetmedia.com/wp-content/uploads/2019/08/younet-media-baemin-vietnam-xanh-mint-1024x683.png", ScaleTypes.FIT))
        slideModels.add(SlideModel("https://rider.baemin.vn/wp-content/uploads/2020/08/the-le-do-vui-chuyen-gia-baemin-1127.jpg", ScaleTypes.FIT))
        slideModels.add(SlideModel("https://vietnambusinessinsider.vn/uploads/images/2021/11/04/a33-baemin-2-1635146759-1636010488.jpeg", ScaleTypes.FIT))
        slideModels.add(SlideModel("https://static.mservice.io/blogscontents/momo-upload-api-200416115749-637226350699346507.jpg", ScaleTypes.FIT))

        imageSlider.setImageList(slideModels, ScaleTypes.FIT)

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


        var fbRes = FirebaseFirestore.getInstance().collection("Restaurant")
        var fbBill = FirebaseFirestore.getInstance().collection("Bill")

        var arrayCategory : ArrayList<CustomerCategory> = ArrayList()
        var arrayExplore : ArrayList<RestaurantClass> = ArrayList()
        var arrayPreviousOrders : ArrayList<RestaurantClass> = ArrayList()

        fbRes.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for(i in it.result){
                    arrayCategory.add(CustomerCategory("" + i.data.getValue("image"), i.id))
                    arrayExplore.add(RestaurantClass(i.id, ""+i.data.getValue("image"), ""+i.data.getValue("displayName"), "2km"))
                }
                recyclerview = findViewById(R.id.categoryRecyclerView)
                adapterCategory = Category_RecyclerView(this, arrayCategory)
                recyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
                recyclerview.adapter = adapterCategory

                adapterCategory.setOnIntemClickListener(object : Category_RecyclerView.onIntemClickListener{
                    override fun onClickItem(position: Int) {
                        val intent = Intent(this@HomeActivity, RestaurantActivity::class.java)
                        intent.putExtra("idRes", arrayCategory[position].idRestaurant)
                        startActivity(intent)
                    }
                })


                recyclerviewExplore = findViewById(R.id.exploreMoreRecyclerView)
                adapterExploreMore = Explore_More_RecyclerView(this, arrayExplore)
                recyclerviewExplore.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                recyclerviewExplore.adapter = adapterExploreMore

                adapterExploreMore.setOnIntemClickListener(object : Explore_More_RecyclerView.onIntemClickListener{
                    override fun onClickItem(position: Int) {
                        val intent = Intent(this@HomeActivity, RestaurantActivity::class.java)
                        intent.putExtra("idRes", arrayExplore[position].idRes)
                        startActivity(intent)
                    }
                })
            }
        }


        fbBill.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (i in it.result) {
                    if(i.data.getValue("idCustomer").toString() == idCustomer){
                        fbRes.get().addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                for (j in task.result) {
                                    if(i.data.getValue("idRestaurant") == j.id){
                                        arrayPreviousOrders.add(RestaurantClass(""+i.data.getValue("idRestaurant"), ""+j.data.getValue("image"), ""+j.data.getValue("displayName"), ""+j.data.getValue("address")))
                                    }
                                }
                                recyclerviewPrevious = findViewById(R.id.previousOrdersRecyclerView)
                                adapterPreviousOrders = Previous_Orders_RecyclerView(this, arrayPreviousOrders)
                                recyclerviewPrevious.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
                                recyclerviewPrevious.adapter = adapterPreviousOrders

                                adapterPreviousOrders.setOnIntemClickListener(object : Previous_Orders_RecyclerView.onIntemClickListener{
                                    override fun onClickItem(position: Int) {
                                        val intent = Intent(this@HomeActivity, RestaurantActivity::class.java)
                                        intent.putExtra("idRes", arrayPreviousOrders[position].idRes)
                                        startActivity(intent)
                                    }
                                })
                            }
                        }
                    }
                }
            }
        }


//        recyclerview = findViewById(R.id.favoriteRestaurantRecyclerView)
//        adapterFavoriteRestaurant = Favorite_Restaurant_RecyclerView(this, arrayListRestauFavorite)
//        recyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
//        recyclerview.adapter = adapterFavoriteRestaurant

        see_moreButton.setOnClickListener {
            val intent = Intent(this,PopularInYourAreaActivity::class.java)
            startActivity(intent)
        }

        searchView.setOnClickListener {
            val intent= Intent(this,SearchRestaurantActivity::class.java)
            startActivity(intent)
        }



        btnCart.setOnClickListener{
            val intent= Intent(this,CartActivity::class.java)
            startActivity(intent)
        }

        btnMenu.setOnClickListener{
            val intent= Intent(this,CustomerMenu::class.java)
            startActivity(intent)
        }

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