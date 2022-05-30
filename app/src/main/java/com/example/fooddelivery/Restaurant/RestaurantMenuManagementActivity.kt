package com.example.fooddelivery.Restaurant

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.Customer.CheckOutActivity
import com.example.fooddelivery.Customer.CustomAdapterPromotion
import com.example.fooddelivery.R
import com.example.fooddelivery.model.PromotionClass
import com.example.fooddelivery.model.RestaurantMenuList
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_fragment_promotion.*
import kotlinx.android.synthetic.main.activity_restaurant_menu_management.*
import kotlinx.android.synthetic.main.fragment_shipper_profile.*

class RestaurantMenuManagementActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RestaurantMenuRecyclerAdapter.ViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_menu_management)
        var sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val phoneNumber = sharedPreferences.getString("ID", "")
        btnBack.setOnClickListener {
            val intent = Intent(this, RestaurantMenuActivity::class.java)
            finish()
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        btnAddMenu.setOnClickListener {
            val intent = Intent(this, RestaurantAddingMenuActivity::class.java)
            startActivity(intent)
        }

        var menuItems: ArrayList<RestaurantMenuList> = ArrayList()
        var fb = FirebaseFirestore.getInstance().collection("Restaurant")
            .document("" + phoneNumber)
            .collection("categoryMenu")
        fb.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (i in it.result) {
                    menuItems.add(RestaurantMenuList("" + i.id))
                }
            }

            layoutManager = LinearLayoutManager(this)
            recyclerViewMenu.layoutManager = layoutManager

            adapter = RestaurantMenuRecyclerAdapter(menuItems)
            recyclerViewMenu.adapter = adapter

            (adapter as RestaurantMenuRecyclerAdapter).onItemClick = {
                val intent = Intent(this, RestaurantDishesManagementActivity::class.java)
                intent.putExtra("menuName", it)
                startActivity(intent)
            }

            (adapter as RestaurantMenuRecyclerAdapter).setOnDeleteItemClickListener(object :
                RestaurantMenuRecyclerAdapter.onDeleteItemClickListener {
                override fun onDeleteItemClick(position: Int) {
                    //Toast.makeText(this@RestaurantMenuManagementActivity,""+menuItems[position].menuName.toString(),Toast.LENGTH_SHORT).show()
                    fb.document("" + menuItems[position].menuName).delete()
                }
            })
        }
    }
}



