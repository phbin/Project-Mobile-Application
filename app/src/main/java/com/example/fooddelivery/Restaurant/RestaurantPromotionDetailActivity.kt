package com.example.fooddelivery.Restaurant

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.fooddelivery.R
import com.example.fooddelivery.model.PromotionClass
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_restaurant_promotion_detail.btnBack
import kotlinx.android.synthetic.main.activity_restaurant_promotion_detail.btnContinue
import kotlinx.android.synthetic.main.activity_restaurant_promotion_detail.progressBar
import kotlinx.android.synthetic.main.activity_restaurant_promotion_detail.btnSelectDate
import kotlinx.android.synthetic.main.activity_restaurant_promotion_detail.editTextDiscountPercent
import kotlinx.android.synthetic.main.activity_restaurant_promotion_detail.editTextPromotionName
import kotlinx.android.synthetic.main.activity_restaurant_promotion_detail.textViewExpiryDate
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
        var key=""

        var fb= FirebaseFirestore.getInstance().collection("Restaurant")
            .document(""+phoneNumber)
            .collection("promotion")
            .get().addOnCompleteListener{
                if (it.isSuccessful) {
                    for ((index, i) in it.result.withIndex()) {
                        if (index.toString() == position) {
                            key=i.id
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
        btnContinue.setOnClickListener{
            progressBar.visibility= View.VISIBLE
            btnContinue.visibility=View.GONE
            //Toast.makeText(this,""+key,Toast.LENGTH_LONG).show()
//            if (editTextPromotionName.text.toString() != ""
//                && editTextDiscountPercent.text.toString() != ""
//                && textDescription.text.toString() != ""
//                && textViewExpiryDate.text.toString() != ""
//            ) {
                var fb = FirebaseFirestore.getInstance().collection("Restaurant")
                    .document("" + phoneNumber)
                    .collection("promotion")
                fb.document(""+key).update("description",""+editTextDescription.text.toString())
                fb.document(""+key).update("expiryDate",""+ textViewExpiryDate.text.toString() )
                fb.document(""+key).update("name",""+ editTextPromotionName.text.toString())
                fb.document(""+key).update("value",""+editTextDiscountPercent.text.toString())
                    .addOnCompleteListener{
                        val intent= Intent(this, RestaurantPromotionActivity::class.java)
                        startActivity(intent)
                    }
        }
        btnBack.setOnClickListener {
            val intent= Intent(this, RestaurantPromotionActivity::class.java)
            startActivity(intent)
        }
    }
    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.TAIWAN)
        textViewExpiryDate.text = sdf.format((myCalendar.time))
    }
}