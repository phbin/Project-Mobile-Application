package com.example.fooddelivery.Customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.Restaurant.RestaurantMenuManagementActivity
import com.example.fooddelivery.model.CartClass
import com.example.fooddelivery.model.DishDetailChoiceSize
import com.example.fooddelivery.model.DishDetailChoiceTopping
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_dish_detail.*

class DishDetailActivity : AppCompatActivity() {
    //    var sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
    //    val phoneNumber = sharedPreferences.getString("ID", "")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_detail)
        LoadData()
        btnPlus.setOnClickListener {
            tvQuantity.text = (tvQuantity.text.toString().toInt() + 1).toString()
            LoadData()
        }
        btnSub.setOnClickListener {
            if(tvQuantity.text!="1"){
            tvQuantity.text = (tvQuantity.text.toString().toInt() - 1).toString()
            LoadData()
            }else{
                return@setOnClickListener
            }
        }
        btnBack.setOnClickListener{
            finish()
        }
        btnAdddish.setOnClickListener{
            var itemPosition = intent.getIntExtra("itemPosition", -1)
            var categoryPosition = intent.getIntExtra("categoryPosition", -1)
            var fb = FirebaseFirestore.getInstance().collection("Restaurant")
                .document("0393751403")
                .collection("categoryMenu")
            fb.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for ((index, i) in task.result.withIndex()) {
                        if (index == categoryPosition) {
                            var idCategory=i.id
                            fb.document("" + i.id)
                                .collection("Item")
                                .get().addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        for ((index, i) in it.result.withIndex()) {
                                            if (index == itemPosition) {
                                                var idItem=i.id
                                                var o=CartClass("039371403",
                                                    ""+idCategory,
                                                    ""+idItem,
                                                    ""+tvName.text,
                                                    ""+tvTotal.text.substring(0,tvTotal.text.length-4).toLong(),
                                                    ""+tvQuantity.text,
                                                    ""+i.data.getValue("image"))
                                                var fbCart=FirebaseFirestore.getInstance().collection("Customer")
                                                    .document("0393751403")
                                                    .collection("Cart")
                                                    .add(o)
                                                finish()
                                            }
                                        }
                                    }
                                }
                        }
                    }
                }
            }
        }
    }

    private fun LoadData() {
        var itemPosition = intent.getIntExtra("itemPosition", -1)
        var categoryPosition = intent.getIntExtra("categoryPosition", -1)
        var fb = FirebaseFirestore.getInstance().collection("Restaurant")
            .document("0393751403")
            .collection("categoryMenu")
        fb.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for ((index, i) in task.result.withIndex()) {
                    if (index == categoryPosition) {
                        tvCategory.text = i.id
                        fb.document("" + i.id)
                            .collection("Item")
                            .get().addOnCompleteListener {
                                if (it.isSuccessful) {
                                    for ((index, i) in it.result.withIndex()) {
                                        if (index == itemPosition) {
                                            tvName.text = i.data.getValue("name").toString()
                                            var price = i.data.getValue("price").toString().toInt()
                                            tvPrice.text = "$price VND"
                                            var total = tvQuantity.text.toString().toInt() * price
                                            tvTotal.text = "$total VND"
                                            Picasso.get().load("" + i.data.getValue("image"))
                                                .into(imageDish)
                                        }
                                    }
                                }
                            }
                    }
                }
            }
        }
    }
}