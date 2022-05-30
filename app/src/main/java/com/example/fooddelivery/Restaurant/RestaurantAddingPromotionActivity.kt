package com.example.fooddelivery.Restaurant

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fooddelivery.R
import com.example.fooddelivery.model.PromotionClass
import com.example.fooddelivery.model.RestaurantDishesList
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_restaurant_adding_dishes.*
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.*
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.btnBack
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.btnContinue
import java.text.SimpleDateFormat
import java.util.*

class RestaurantAddingPromotionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_adding_promotion)
        var sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val phoneNumber = sharedPreferences.getString("ID", "")
        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }

        btnBack.setOnClickListener {
            finish()
        }

        btnSelectDate.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePickerStyle, datePicker, myCalendar.get(
                Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH) + 10).show()
        }

        btnContinue.setOnClickListener {
            if (editTextPromotionName.text.toString() != ""
                && editTextDiscountPercent.text.toString() != ""
                && textDescription.text.toString() != ""
                && textViewExpiryDate.text.toString() != ""
            ) {
                var o = PromotionClass(
                    textDescription.text.toString(),
                    textViewExpiryDate.text.toString(),
                    editTextPromotionName.text.toString(),
                    editTextDiscountPercent.text.toString())

                    var fb = FirebaseFirestore.getInstance().collection("Restaurant")
                    .document("" + phoneNumber)
                    .collection("promotion")
                fb.add(o)
            }
        }

    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.TAIWAN)
        textViewExpiryDate.text = sdf.format((myCalendar.time))
    }
}