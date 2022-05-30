package com.example.fooddelivery.Restaurant

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fooddelivery.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.*
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.btnBack
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.btnSelectDate
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.editTextDiscountPercent
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.editTextPromotionName
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.textViewExpiryDate
import kotlinx.android.synthetic.main.activity_restaurant_promotion_detail.*
import java.text.SimpleDateFormat
import java.util.*

class RestaurantPromotionDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_promotion_detail)
        var sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val phoneNumber = sharedPreferences.getString("ID", "")

        val myCalendar = Calendar.getInstance()
        val datePickerDialog= DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }
        btnSelectDate.setOnClickListener{
            DatePickerDialog(this, R.style.MyDatePickerStyle, datePickerDialog, myCalendar.get(
                Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH) + 10).show()
        }
        var position = intent.getStringExtra("promotionPosition")

        var fb= FirebaseFirestore.getInstance().collection("Restaurant")
            .document(""+phoneNumber)
            .collection("promotion")
            .get().addOnCompleteListener{
                if (it.isSuccessful) {
                    for ((index, i) in it.result.withIndex()) {
                        if (index.toString() == position) {
                            editTextPromotionName.setText(i.data.getValue("name").toString())
                            editTextDiscountPercent.setText(i.data.getValue("value").toString())
                            textViewExpiryDate.setText(i.data.getValue("expiryDate").toString())
                            editTextDescription.setText(i.data.getValue("description").toString())
                        }
                    }
                }
            }
//        if(position!=null){
//            val editTextPromotionName : EditText = findViewById(R.id.editTextPromotionName)
//
//            //editTextPromotionName.setText(position.name)
//        }

        btnBack.setOnClickListener {
            finish()
        }
    }
    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.TAIWAN)
        textViewExpiryDate.text = sdf.format((myCalendar.time))
    }
}