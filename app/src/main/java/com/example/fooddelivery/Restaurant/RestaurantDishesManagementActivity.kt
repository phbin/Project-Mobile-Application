package com.example.fooddelivery.Restaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.Customer.CheckOutActivity
import com.example.fooddelivery.Customer.CustomAdapterPromotion
import com.example.fooddelivery.R
import com.example.fooddelivery.model.RestaurantDishesList
import com.example.fooddelivery.model.RestaurantMenuList
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_restaurant_dishes_management.*

class RestaurantDishesManagementActivity : AppCompatActivity(){

    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RestaurantDishesAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_dishes_management)
        var sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val phoneNumber = sharedPreferences.getString("ID","")
        val menuTitle = intent.getParcelableExtra<RestaurantMenuList>("menuName")

        if(menuTitle!=null){
            textViewMenuTitle.text = menuTitle.menuName
        }
        else{
            textViewMenuTitle.text = intent.getStringExtra("menuName")
        }

        btnBack.setOnClickListener {
            finish()
        }


        var dishList : ArrayList<RestaurantDishesList> = ArrayList()
        //get item in category
        var fb = FirebaseFirestore.getInstance().collection("Restaurant")
            .document(""+phoneNumber)
            .collection("categoryMenu")
            .document(""+textViewMenuTitle.text)
            .collection("Item")
        fb.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (i in it.result) {
                    dishList.add(RestaurantDishesList("" + i.data.getValue("name").toString(),
                        ""+i.data.getValue("price").toString(),
                        "" + i.data.getValue("size"),
                        ""+i.data.getValue("image")))
                }
            }

            layoutManager = LinearLayoutManager(this)
            recyclerViewDishes.layoutManager = layoutManager

            adapter = RestaurantDishesAdapter(dishList)
            recyclerViewDishes.adapter = adapter

//            (adapter as RestaurantDishesAdapter).onItemClick = {
//                val intent = Intent(this, RestaurantMenuDetailActivity::class.java)
//                intent.putExtra("dishName", it)
//                startActivity(intent)
//            }
            (adapter as RestaurantDishesAdapter).setOnItemClickListener(object :
                RestaurantDishesAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@RestaurantDishesManagementActivity, RestaurantDishDetailActivity::class.java)
                    intent.putExtra("dishName", ""+position)
                    intent.putExtra("categoryName", ""+textViewMenuTitle.text.toString())
                    //Toast.makeText(this@RestaurantDishesManagementActivity,""+position,Toast.LENGTH_LONG).show()
                    startActivity(intent)
                }
            })
            (adapter as RestaurantDishesAdapter).setOnDeleteItemClickListener(object :
                RestaurantDishesAdapter.onDeleteItemClickListener {
                override fun onDeleteItemClick(position: Int) {
                    //Toast.makeText(this@RestaurantMenuManagementActivity,""+menuItems[position].menuName.toString(),Toast.LENGTH_SHORT).show()
                    fb.get().addOnCompleteListener {
                        if (it.isSuccessful) {
                            for ( (index,i) in it.result.withIndex()) {
                                if(index==position) fb.document(i.id).delete()
                            }
                        }
                    }
                }
            })
        }

        btnAddDish.setOnClickListener {
            val intent = Intent(this, RestaurantAddingDishesActivity::class.java)
            intent.putExtra("itemName",textViewMenuTitle.text)
            startActivity(intent)
        }
    }
}