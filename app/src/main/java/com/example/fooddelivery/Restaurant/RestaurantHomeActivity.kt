package com.example.fooddelivery.Restaurant

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.example.fooddelivery.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_restaurant_home.*
import kotlinx.android.synthetic.main.fragment_restaurant_home_processing.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class RestaurantHomeActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_home)

        viewPager.adapter = RestaurantHomeAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

        var preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idRestaurant = preferences.getString("ID","")

        btnMenu.setOnClickListener {
            val intent = Intent(this, RestaurantMenuActivity::class.java)
            startActivity(intent)
        }

        val calendar: Calendar = Calendar.getInstance()
        val day = calendar[Calendar.DAY_OF_WEEK]
        var currentDayOfWeek : String =""

        when (day) {
            Calendar.SUNDAY -> {
                currentDayOfWeek = "Sunday"
            }
            Calendar.MONDAY -> {
                currentDayOfWeek = "Monday"
            }
            Calendar.TUESDAY -> {
                currentDayOfWeek = "Tuesday"
            }
            Calendar.WEDNESDAY -> {
                currentDayOfWeek = "Wednesday"
            }
            Calendar.THURSDAY -> {
                currentDayOfWeek = "Thursday"
            }
            Calendar.FRIDAY -> {
                currentDayOfWeek = "Friday"
            }
            Calendar.SATURDAY -> {
                currentDayOfWeek = "Saturday"
            }
        }

        var fb = FirebaseFirestore.getInstance().collection("Restaurant")
        var fbDay = FirebaseFirestore.getInstance().collection("Restaurant").document("$idRestaurant").collection("Hours")


        fbDay.get().addOnCompleteListener {
            for(i in it.result){
                if(i.id == currentDayOfWeek)
                {
                    val start  = i.data.getValue("Open").toString()
                    val startTime = LocalDateTime.parse("2002-03-23 $start", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                    val end  = i.data.getValue("Close").toString()
                    val endTime = LocalDateTime.parse("2002-03-23 $end", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                    val now = LocalDateTime.now()
                    val dtf = DateTimeFormatter.ofPattern("HH:mm")
                    val time = LocalDateTime.parse("2002-03-23 ${dtf.format(now)}", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))


                    if(i.data.getValue("CloseAllDay").toString().toBoolean() || time < startTime || time > endTime){
                        fb.document("$idRestaurant").update("status", "Closed")
                        btnStatus.setTextColor(Color.parseColor("#828282"))
                        btnStatus.text = "Closed"
                        val icon = applicationContext.resources.getDrawable(R.drawable.ic_status_closed)
                        btnStatus.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
                    }
                    else{
                        btnStatus.text = "Open"
                        btnStatus.setTextColor(Color.parseColor("#FFFFFF"))
                        val icon = applicationContext.resources.getDrawable(R.drawable.ic_shipper_status)
                        btnStatus.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
                    }
                    return@addOnCompleteListener
                }
                continue
            }
        }

        btnStatus.setOnClickListener {
            if(btnStatus.text == "Open")
            {

                btnStatus.text="Closed"
                btnStatus.setTextColor(Color.parseColor("#828282"))
                val icon = applicationContext.resources.getDrawable(R.drawable.ic_status_closed)
                btnStatus.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
                var fb = FirebaseFirestore.getInstance().collection("Restaurant").document("$idRestaurant").update("status", "Closed")
            }
            else{
                btnStatus.text="Open"
                btnStatus.setTextColor(Color.parseColor("#FFFFFF"))
                val icon = applicationContext.resources.getDrawable(R.drawable.ic_shipper_status)
                btnStatus.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null)
                var fb = FirebaseFirestore.getInstance().collection("Restaurant").document("$idRestaurant").update("status", "Open")
            }
        }
    }
}