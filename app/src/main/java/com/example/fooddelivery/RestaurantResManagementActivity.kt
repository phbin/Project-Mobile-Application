package com.example.fooddelivery

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_restaurant_res_management.*
import java.text.SimpleDateFormat
import java.util.*

class RestaurantResManagementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_res_management)

        btnBack.setOnClickListener {
            val intent = Intent(this, RestaurantHomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }

        mondayStartTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                mondayStartTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, R.style.TimePickerTheme, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        mondayEndTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                mondayEndTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, R.style.TimePickerTheme, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


        tuesdayStartTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                tuesdayStartTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        tuesdayEndTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                tuesdayEndTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


        wednesdayStartTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                wednesdayStartTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        wednesdayEndTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                wednesdayEndTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


        thursdayStartTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                thursdayStartTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        thursdayEndTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                thursdayEndTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


        fridayStartTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                fridayStartTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        fridayEndTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                fridayEndTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


        saturdayStartTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                saturdayStartTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        saturdayEndTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                saturdayEndTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


        sundayStartTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                sundayStartTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        sundayEndTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                sundayEndTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        closedRestaurantMonday.setOnClickListener {
            if(mondayStartTime.text.toString() != "Close"){
                mondayStartTime.text = "Close"
                mondayLine.visibility = View.INVISIBLE
                mondayEndTime.visibility = View.INVISIBLE
            }
            else{
                mondayStartTime.text = "08:00"
                mondayLine.visibility = View.VISIBLE
                mondayEndTime.visibility = View.VISIBLE
            }
        }

        closedRestaurantTuesday.setOnClickListener {
            if(tuesdayStartTime.text.toString() != "Close"){
                tuesdayStartTime.text = "Close"
                tuesdayLine.visibility = View.INVISIBLE
                tuesdayEndTime.visibility = View.INVISIBLE
            }
            else{
                tuesdayStartTime.text = "08:00"
                tuesdayLine.visibility = View.VISIBLE
                tuesdayEndTime.visibility = View.VISIBLE
            }
        }

        closedRestaurantWednesday.setOnClickListener {
            if(wednesdayStartTime.text.toString() != "Close"){
                wednesdayStartTime.text = "Close"
                wednesdayLine.visibility = View.INVISIBLE
                wednesdayEndTime.visibility = View.INVISIBLE
            }
            else{
                wednesdayStartTime.text = "08:00"
                wednesdayLine.visibility = View.VISIBLE
                wednesdayEndTime.visibility = View.VISIBLE
            }
        }

        closedRestaurantThursday.setOnClickListener {
            if(thursdayStartTime.text.toString() != "Close"){
                thursdayStartTime.text = "Close"
                thursdayLine.visibility = View.INVISIBLE
                thursdayEndTime.visibility = View.INVISIBLE
            }
            else{
                thursdayStartTime.text = "08:00"
                thursdayLine.visibility = View.VISIBLE
                thursdayEndTime.visibility = View.VISIBLE
            }
        }

        closedRestaurantFriday.setOnClickListener {
            if(fridayStartTime.text.toString() != "Close"){
                fridayStartTime.text = "Close"
                fridayLine.visibility = View.INVISIBLE
                fridayEndTime.visibility = View.INVISIBLE
            }
            else{
                fridayStartTime.text = "08:00"
                fridayLine.visibility = View.VISIBLE
                fridayEndTime.visibility = View.VISIBLE
            }
        }

        closedRestaurantSaturday.setOnClickListener {
            if(saturdayStartTime.text.toString() != "Close"){
                saturdayStartTime.text = "Close"
                saturdayLine.visibility = View.INVISIBLE
                saturdayEndTime.visibility = View.INVISIBLE
            }
            else{
                saturdayStartTime.text = "08:00"
                saturdayLine.visibility = View.VISIBLE
                saturdayEndTime.visibility = View.VISIBLE
            }
        }

        closedRestaurantSunday.setOnClickListener {
            if(sundayStartTime.text.toString() != "Close"){
                sundayStartTime.text = "Close"
                sundayLine.visibility = View.INVISIBLE
                sundayEndTime.visibility = View.INVISIBLE
            }
            else{
                sundayStartTime.text = "08:00"
                sundayLine.visibility = View.VISIBLE
                sundayEndTime.visibility = View.VISIBLE
            }
        }
    }
}