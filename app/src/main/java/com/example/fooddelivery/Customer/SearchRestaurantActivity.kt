package com.example.fooddelivery.Customer
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.Customer.Search_RecyclerView
import com.example.fooddelivery.R

import com.example.fooddelivery.model.RestaurantClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_search_restaurant.*
import kotlin.collections.ArrayList

class SearchRestaurantActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var editTextSearch: EditText
    private lateinit var adapterExploreMore: Search_RecyclerView
    var arrayListRestau: ArrayList<RestaurantClass> = ArrayList()
    var latitude=0.0
    var longitude=0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_restaurant)
        latitude=intent.getDoubleExtra("lat",0.0)
        longitude=intent.getDoubleExtra("long",0.0)
        var fb = FirebaseFirestore.getInstance().collection("Restaurant")
        var result=FloatArray(10)

        fb.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (i in task.result) {
                    Location.distanceBetween(i.data.getValue("latitude").toString().toDouble(),
                        i.data.getValue("longitude").toString().toDouble(),
                        latitude,
                        longitude,result)
                    arrayListRestau.add(RestaurantClass(""+i.id, ""+i.data.getValue("image"), ""+i.data.getValue("displayName"), String.format("%.1f", result[0]/1000)+"km"))

                }
                recyclerview = findViewById(R.id.search_recyclerview)
                adapterExploreMore = Search_RecyclerView(arrayListRestau)
                recyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                recyclerview.adapter = adapterExploreMore

                adapterExploreMore.setOnIntemClickListener(object : Search_RecyclerView.onIntemClickListener{
                    override fun onClickItem(position: Int) {
                        val intent = Intent(this@SearchRestaurantActivity, RestaurantActivity::class.java)
                        intent.putExtra("lat",latitude)
                        intent.putExtra("long",longitude)
                        intent.putExtra("idRes", arrayListRestau[position].idRes)
                        startActivity(intent)
                    }
                })
            }
        }

        backButton.setOnClickListener {

            val intent: Intent = Intent(this,HomeActivity::class.java)
            intent.putExtra("lat",latitude)
            intent.putExtra("long",longitude)
            startActivity(intent)
        }
        editTextSearch = findViewById(R.id.editTextSearch)
        editTextSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                filterList(p0.toString())
            }

        })
}

    private fun filterList(filterItem: String) {
        val tempList = arrayListRestau
        var newList: MutableList<RestaurantClass> = ArrayList()
        for (d in tempList){
//            if(filterItem in d.name ||
//                filterItem in d.location){
//                newList.add(d)
//                Toast.makeText(this@SearchRestaurantActivity, d.name + d.location, Toast.LENGTH_SHORT).show()
//            }
            if(d.name.toLowerCase().contains(filterItem.toLowerCase())||
                d.location.toLowerCase().contains(filterItem.toLowerCase())){
                newList.add(d)
            }
        }
        adapterExploreMore = Search_RecyclerView(newList)
        adapterExploreMore.notifyDataSetChanged()
        recyclerview.adapter = adapterExploreMore

        adapterExploreMore.setOnIntemClickListener(object : Search_RecyclerView.onIntemClickListener{
            override fun onClickItem(position: Int) {
                val intent = Intent(this@SearchRestaurantActivity, RestaurantActivity::class.java)
                intent.putExtra("lat",latitude)
                intent.putExtra("long",longitude)
                intent.putExtra("idRes", newList[position].idRes)
                startActivity(intent)
            }
        })
    }
}



