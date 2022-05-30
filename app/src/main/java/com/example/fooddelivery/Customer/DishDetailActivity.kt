package com.example.fooddelivery.Customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.Restaurant.RestaurantMenuManagementActivity
import com.example.fooddelivery.model.DishDetailChoiceSize
import com.example.fooddelivery.model.DishDetailChoiceTopping
import kotlinx.android.synthetic.main.activity_dish_detail.*

class DishDetailActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapterDishDetailChoiceSize: DishDetail_Choice_Size_RecyclerView
    private lateinit var adapterDishDetailChoiceTopping: DishDetail_Choice_Topping_RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_detail)

        recyclerview = findViewById(R.id.dishdetailChoiceSizeRecyclerView)
        adapterDishDetailChoiceSize = DishDetail_Choice_Size_RecyclerView()
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recyclerview.adapter = adapterDishDetailChoiceSize

        recyclerview = findViewById(R.id.dishdetailChoiceToppingRecyclerView)
        adapterDishDetailChoiceTopping = DishDetail_Choice_Topping_RecyclerView()
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recyclerview.adapter = adapterDishDetailChoiceTopping

        var arrayListDishDetailChoiceSize: ArrayList<DishDetailChoiceSize> = ArrayList()
        arrayListDishDetailChoiceSize.add(
            DishDetailChoiceSize("Size S", 0)
        )
        arrayListDishDetailChoiceSize.add(
            DishDetailChoiceSize("Size M", 5000)
        )
        arrayListDishDetailChoiceSize.add(
            DishDetailChoiceSize("Size L", 7000)
        )

        btnBack.setOnClickListener{
            val intent = Intent(this, RestaurantActivity::class.java)
            startActivity(intent)
        }
        var arrayListDishDetailChoiceTopping: ArrayList<DishDetailChoiceTopping> = ArrayList()
        arrayListDishDetailChoiceTopping.add(
            DishDetailChoiceTopping("Siliaca", 0)
        )
        arrayListDishDetailChoiceTopping.add(
            DishDetailChoiceTopping("Siliaca", 0)
        )
        arrayListDishDetailChoiceTopping.add(
            DishDetailChoiceTopping("Siliaca", 0)
        )
    }
}