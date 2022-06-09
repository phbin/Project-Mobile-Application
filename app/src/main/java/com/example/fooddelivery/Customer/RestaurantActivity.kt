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

        val idRestaurant = intent.getStringExtra("idRes")
        var preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idCustomer = preferences.getString("ID","")

        var arrayListRestaurantAppertizer: ArrayList<DishByCategory> = ArrayList()

        var menu = FirebaseFirestore.getInstance().collection("Restaurant")
            .document("$idRestaurant")
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
                            recyclerview = findViewById(R.id.restaurantAppertizerRecyclerViewGrouped)
                            adapterDishByCategory = DishByCategoryAdapter(this,arrayListRestaurantAppertizer)
                            recyclerview.adapter = adapterDishByCategory
                        }
                }
            }
        }

        LoadInfo()
        btnCheckOut.setOnClickListener{
            if(textStatus.text.toString() == "Closed"){
                Toast.makeText(this, "This restaurant is closed", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else {
                val intent = Intent(this, CheckOutActivity::class.java)
                startActivity(intent)
            }
        }
        btnCart.setOnClickListener {
            if(textStatus.text.toString() == "Closed"){
                Toast.makeText(this, "This restaurant is closed", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            }
        }
        btnBack.setOnClickListener {
            var fb = FirebaseFirestore.getInstance().collection("Customer")
                .document("$idCustomer")
                .collection("Cart")
            fb.get().addOnCompleteListener {
                if (it.isSuccessful) {
                    for (i in it.result) {
                        fb.document("" + i.id).delete()
                    }
                }
            }
            finish()
        }
    }

    private fun LoadInfo() {
        var fb = FirebaseFirestore.getInstance().collection("Restaurant")
        val idRestaurant = intent.getStringExtra("idRes")
        fb.get().addOnCompleteListener {
            for (i in it.result) {
                if (i.id == "$idRestaurant") {
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