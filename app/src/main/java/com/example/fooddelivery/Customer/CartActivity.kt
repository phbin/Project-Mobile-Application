package com.example.fooddelivery.Customer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.Restaurant.RestaurantDishesManagementActivity
import com.example.fooddelivery.Restaurant.RestaurantMenuRecyclerAdapter
import com.example.fooddelivery.model.CartClass
import com.example.fooddelivery.model.RestaurantMenuList
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_restaurant_menu_management.*

class CartActivity : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CartAdapter.ViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        var preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idCustomer = preferences.getString("ID","")
        var idRestaurant = ""
        var quantity = 0

        //var sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        //val phoneNumber = sharedPreferences.getString("ID", "")
        var cartItems: ArrayList<CartClass> = ArrayList()
        var fb = FirebaseFirestore.getInstance().collection("Customer")
            .document("$idCustomer")
            .collection("Cart")
        fb.get().addOnCompleteListener {
            if (it.isSuccessful) {
                var total : Long = 0
                for (i in it.result) {
                    cartItems.add(CartClass("" + i.data.getValue("idRestaurant"),
                        "" + i.data.getValue("idCategory"),
                        "" + i.data.getValue("idItem"),
                        "" + i.data.getValue("nameFD"),
                        "" + i.data.getValue("price"),
                        "" + i.data.getValue("quantity"),
                        "" + i.data.getValue("imageFD")))
                        total += i.data.getValue("price").toString().toLong()
                        idRestaurant = i.data.getValue("idRestaurant").toString()
                        quantity+=i.data.getValue("quantity").toString().toInt()
                }
                textViewTotal.text = total.toString()
            }
                layoutManager = LinearLayoutManager(this)
                recyclerViewCart.layoutManager = layoutManager

                adapter = CartAdapter( cartItems)
                recyclerViewCart.adapter = adapter
        }
        backButton.setOnClickListener{
            finish()
        }
        btnOrder.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            btnOrder.visibility = View.GONE
            var intent=Intent(this,CheckOutActivity::class.java)
            intent.putExtra("idRestaurant", idRestaurant)
            intent.putExtra("quantity", quantity.toString())
            startActivity(intent)
        }

//


//            (adapter as CartAdapter).setOnItemClickListener(object : CartAdapter.onItemClickListener {
//                //val intent = Intent(this, RestaurantDishesManagementActivity::class.java)
//                //intent.putExtra("menuName", it)
//                //tartActivity(intent)
//            })

//            (adapter as CartAdapter).setOnDeleteItemClickListener(object :
//                CartAdapter.onDeleteItemClickListener {
//                override fun onDeleteItemClick(position: Int) {
//                    //Toast.makeText(this@RestaurantMenuManagementActivity,""+menuItems[position].menuName.toString(),Toast.LENGTH_SHORT).show()
//                    fb.document("" + cartItems[position]).delete()
//                }
//            })
    }
}