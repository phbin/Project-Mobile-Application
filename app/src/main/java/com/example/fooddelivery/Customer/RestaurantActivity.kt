package com.example.fooddelivery.Customer


import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.Restaurant.RestaurantPromotionDetailActivity
import com.example.fooddelivery.model.DishByCategory
import com.example.fooddelivery.model.RestaurantAppertizer
import com.example.fooddelivery.model.RestaurantMenuList
import com.example.fooddelivery.model.RestaurantPopular
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_restaurant.*
import kotlinx.android.synthetic.main.fragment_shipper_profile.*

class RestaurantActivity : AppCompatActivity() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapterDishByCategory: DishByCategoryAdapter
    private lateinit var adapter: Restaurant_Appertizer_RecyclerView

    //    var sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
//    val phoneNumber = sharedPreferences.getString("ID", "")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)
        var arrayListRestaurantAppertizer: ArrayList<DishByCategory> = ArrayList()

        var menu = FirebaseFirestore.getInstance().collection("Restaurant")
            .document("0393751403")
            .collection("categoryMenu")
        menu.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (i in task.result) {
                    var nameCategory = i.id
                    var a: ArrayList<RestaurantAppertizer> = ArrayList()
                    menu.document("" + i.id)
                        .collection("Item")
                        .addSnapshotListener { value, e ->
                            if (e != null) {
                                Log.w("AAA", "Listen failed.", e)
                                return@addSnapshotListener
                            }
                            var itemList: ArrayList<RestaurantAppertizer> = ArrayList()
                            for (doc in value!!) {

                                var name:String=doc.getString("name").toString()
                                var size=doc.getString("size").toString()
                                var price=doc.getString("price").toString()
                                var image=doc.getString("image").toString()
                                itemList.add(RestaurantAppertizer(""+image,
                                    ""+name,""+size,""+price))


                            }
                            arrayListRestaurantAppertizer.add(
                            DishByCategory(nameCategory , itemList))
                            recyclerview =
                                findViewById(R.id.restaurantAppertizerRecyclerViewGrouped)
                            adapterDishByCategory =
                                DishByCategoryAdapter(this,arrayListRestaurantAppertizer)
                            recyclerview.adapter = adapterDishByCategory
                        }
                }
            }
        }

        LoadInfo()
        btnCheckOut.setOnClickListener{
            val intent = Intent(this, CheckOutActivity::class.java)
            startActivity(intent)
        }
        btnCart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
    }

    private fun LoadInfo() {
        var fb = FirebaseFirestore.getInstance().collection("Restaurant")
        fb.get().addOnCompleteListener {
            for (i in it.result) {
                if (i.id == "0393751403") {
                    tvNameStore.text = i.data.getValue("displayName").toString()
                    tvAddress.text = i.data.getValue("address").toString()
                    textStatus.text = i.data.getValue("status").toString()
                    return@addOnCompleteListener
                }
                continue
            }
        }
    }
}