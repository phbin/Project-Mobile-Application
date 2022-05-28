package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.`object`.RestaurantMenuList
import kotlinx.android.synthetic.main.activity_restaurant_menu_management.*

class RestaurantMenuManagementActivity : AppCompatActivity() {

    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RestaurantMenuRecyclerAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_menu_management)

        btnBack.setOnClickListener {
            val intent = Intent(this, RestaurantHomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }

        btnAddMenu.setOnClickListener {
            val intent = Intent(this, RestaurantAddingMenuActivity::class.java)
            startActivity(intent)
        }

        var menuItems : ArrayList<RestaurantMenuList> = ArrayList()

        menuItems.add(RestaurantMenuList("Trà sữa"))
        menuItems.add(RestaurantMenuList("Trà sữa"))
        menuItems.add(RestaurantMenuList("Trà trà"))
        menuItems.add(RestaurantMenuList("Trà dâu"))
        menuItems.add(RestaurantMenuList("Trà"))


        layoutManager = LinearLayoutManager(this)
        recyclerViewMenu.layoutManager = layoutManager

        adapter = RestaurantMenuRecyclerAdapter( menuItems)
        recyclerViewMenu.adapter = adapter

        (adapter as RestaurantMenuRecyclerAdapter).onItemClick = {
            val intent = Intent(this, RestaurantMenuDetailActivity::class.java)
            intent.putExtra("menuName", it)
            startActivity(intent)
        }

//        adapter.notifyDataSetChanged()

    }
}