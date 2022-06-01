package com.example.fooddelivery.Customer
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.Customer.Search_RecyclerView
import com.example.fooddelivery.R

import com.example.fooddelivery.model.RestaurantClass
import kotlinx.android.synthetic.main.activity_search_restaurant.*
import kotlin.collections.ArrayList

class SearchRestaurantActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var editTextSearch: EditText
    private lateinit var adapterExploreMore: Search_RecyclerView
    var arrayListRestau: ArrayList<RestaurantClass> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_restaurant)

        backButton.setOnClickListener {

            val intent: Intent = Intent(this,HomeActivity::class.java)
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


        arrayListRestau.add(
            RestaurantClass(
                R.drawable.square_mint,
                "I miss you",
                "I just wanna apologize to you for my impatience",
                "3"
            )
        )

        arrayListRestau.add(
            RestaurantClass(
                R.drawable.square_mint,
                "So far so good",
                "Please forgive me",
                "3"
            )
        )
        arrayListRestau.add(
            RestaurantClass(
                R.drawable.square_mint,
                "Love me like you do",
                "Are you ready for it",
                "4"
            )
        )
        arrayListRestau.add(
            RestaurantClass(
                R.drawable.square_mint,
                "I am exhausted",
                "Where are you now?",
                "3"
            )
        )
        arrayListRestau.add(
            RestaurantClass(
                R.drawable.square_mint,
                "I need your accompany",
                "I wish there hadn't been that day",
                "3"
            )
        )
        arrayListRestau.add(
            RestaurantClass(
                R.drawable.square_mint,
                "I hate you",
                "Wish we hadn't met.",
                "3"
            )
        )
        arrayListRestau.add(
            RestaurantClass(
                R.drawable.square_mint,
                "Cooking",
                "So tired",
                "3"
            )
        )
        arrayListRestau.add(
            RestaurantClass(
                R.drawable.square_mint,
                "Calm down",
                "I",
                "5"
            )
        )
        recyclerview = findViewById(R.id.search_recyclerview)
        adapterExploreMore = Search_RecyclerView(arrayListRestau)
        recyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerview.adapter = adapterExploreMore
}

    private fun filterList(filterItem: String) {
        val tempList = arrayListRestau
        var newList: MutableList<RestaurantClass> = ArrayList()
        for (d in tempList){
            if(filterItem in d.name.toString() ||
                filterItem in d.dishes.toString()||
                filterItem in d.location.toString()){
                newList.add(d)
            }
        }
        adapterExploreMore = Search_RecyclerView(newList)
        adapterExploreMore.notifyDataSetChanged()
        recyclerview.adapter = adapterExploreMore
    }
}



