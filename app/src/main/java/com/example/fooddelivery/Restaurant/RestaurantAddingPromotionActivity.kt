package com.example.fooddelivery.Restaurant

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.fooddelivery.R
import com.example.fooddelivery.model.PromotionClass
import com.example.fooddelivery.model.RestaurantDishesList
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.*
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.btnBack
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.btnContinue
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.btnSelectDate
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.editTextDiscountPercent
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.editTextPromotionName
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.progressBar
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.textViewDescription
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.textViewDiscountPercent
import kotlinx.android.synthetic.main.activity_restaurant_adding_promotion.textViewExpiryDate
import kotlinx.android.synthetic.main.activity_restaurant_promotion_detail.*
import kotlinx.android.synthetic.main.listview_order_history.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class RestaurantAddingPromotionActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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
            myCalendar.set(Calendar.HOUR_OF_DAY, 0)
            myCalendar.set(Calendar.MINUTE, 0)
            updateLable(myCalendar)
        }

        btnBack.setOnClickListener {
            finish()
        }

        val now = LocalDateTime.now()
        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val currentDate = LocalDateTime.parse("${dtf.format(now)}", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        Toast.makeText(this, "${dtf.format(now)}", Toast.LENGTH_SHORT).show()


        btnSelectDate.setOnClickListener {
            DatePickerDialog(this, R.style.MyDatePickerStyle, datePicker, myCalendar.get(
                Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH) + 10).show()
        }

        btnContinue.setOnClickListener {
            if (editTextPromotionName.text.isNotEmpty()
                && editTextDiscountPercent.text.isNotEmpty()
                && textDescription.text.isNotEmpty()
                && textViewExpiryDate.text.isNotEmpty()
            ) {
                val expiryDate = LocalDateTime.parse(textViewExpiryDate.text.toString() + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                if(currentDate > expiryDate){
                    textViewExpiryDate.error = "The expiry must be further now"
                    return@setOnClickListener
                }

                if(editTextDiscountPercent.text.toString().toInt() > 100){
                    editTextDiscountPercent.error = "Please check again"
                    return@setOnClickListener
                }

                progressBar.visibility= View.VISIBLE
                btnContinue.visibility= View.GONE
                var o = PromotionClass(
                    textDescription.text.toString(),
                    textViewExpiryDate.text.toString(),
                    editTextPromotionName.text.toString(),
                    editTextDiscountPercent.text.toString())

                    var fb = FirebaseFirestore.getInstance().collection("Restaurant")
                    .document("" + phoneNumber)
                    .collection("promotion")
                fb.add(o)
                    .addOnCompleteListener{
                        val intent= Intent(this, RestaurantPromotionActivity::class.java)
                        startActivity(intent)
                    }
            }
            else{
                if(editTextPromotionName.text.isEmpty()){
                    editTextPromotionName.error = "Please enter Name"
                }
                if(editTextDiscountPercent.text.isEmpty()){
                    editTextDiscountPercent.error = "Please enter Discount Percent"
                }
                if(textDescription.text.isEmpty()){
                    textDescription.error = "Please enter Description"
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.TAIWAN)
        textViewExpiryDate.text = sdf.format((myCalendar.time))
        Toast.makeText(this, sdf.format(myCalendar.time), Toast.LENGTH_SHORT).show()
    }
}