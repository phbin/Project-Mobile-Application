package com.example.fooddelivery.Customer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddelivery.R
import com.example.fooddelivery.Restaurant.RestaurantPromotionDetailActivity
import com.example.fooddelivery.model.PromotionClass
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_check_out.*
import kotlinx.android.synthetic.main.activity_fragment_promotion.*
import kotlinx.android.synthetic.main.activity_restaurant_promotion.*
import kotlinx.android.synthetic.main.activity_sign_in.*


class FragmentPromotion : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CustomAdapterPromotion.ViewHolder>? = null
    var latitude=0.0
    var longitude=0.0
    var quantity=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_promotion)
        latitude=intent.getDoubleExtra("lat",0.0)
        longitude=intent.getDoubleExtra("long",0.0)
        quantity = intent.getStringExtra("quantity").toString()
        var arrayListProm: ArrayList<PromotionClass> = ArrayList()
        val idRestaurant = intent.getStringExtra("idRestaurant").toString()
        btnBackPromotion.setOnClickListener {
            var intent = Intent(this, CheckOutActivity::class.java)
            intent.putExtra("idRestaurant", idRestaurant)
            intent.putExtra("lat",latitude)
            intent.putExtra("long",longitude)
            intent.putExtra("quantity",quantity)
            startActivity(intent)
        }


        var fb = FirebaseFirestore.getInstance().collection("Restaurant")
            .document("$idRestaurant")
            .collection("promotion")

        fb.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (i in it.result) {
                    arrayListProm.add(PromotionClass(
                        "" + i.data.getValue("description").toString(),
                        "" + i.data.getValue("expiryDate").toString(),
                        "" + i.data.getValue("name").toString(),
                        "" + i.data.getValue("value").toString()))
                }
            }
            layoutManager = LinearLayoutManager(this)
            promotionListView.layoutManager = layoutManager

            adapter = CustomAdapterPromotion(arrayListProm)
            promotionListView.adapter = adapter

            (adapter as CustomAdapterPromotion).setOnItemClickListener(object :
                CustomAdapterPromotion.onItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(this@FragmentPromotion, CheckOutActivity::class.java)
                    intent.putExtra("promotionPosition", ""+position)
                    intent.putExtra("idRestaurant", idRestaurant)
                    intent.putExtra("lat",latitude)
                    intent.putExtra("long",longitude)
                    intent.putExtra("quantity",quantity)
                    startActivity(intent)
                }
            })
        }
    }
}


