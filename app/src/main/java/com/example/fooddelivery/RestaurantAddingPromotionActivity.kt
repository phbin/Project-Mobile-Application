package com.example.fooddelivery

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.*
import java.text.SimpleDateFormat
import java.util.*

class RestaurantAddingPromotionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_adding_promotion)

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
    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.TAIWAN)
        textViewExpiryDate.text = sdf.format((myCalendar.time))
    }
}