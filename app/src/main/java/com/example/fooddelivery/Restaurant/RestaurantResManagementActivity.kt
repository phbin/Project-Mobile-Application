package com.example.fooddelivery.Restaurant

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.fooddelivery.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.DateTime
import kotlinx.android.synthetic.main.activity_restaurant_res_management.*
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class RestaurantResManagementActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_res_management)

        var sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val idRestaurant = sharedPreferences.getString("ID", "")


        var fb = FirebaseFirestore.getInstance().collection("Restaurant").document("$idRestaurant").collection("Hours")

        fb.get().addOnCompleteListener {
            for(i in it.result){
                if(i.id == "Monday"){
                    if(i.data.getValue("CloseAllDay").toString().toBoolean()){
                        mondayClose.visibility = View.VISIBLE
                        mondayStartTime.visibility = View.GONE
                        mondayLine.visibility = View.INVISIBLE
                        mondayEndTime.visibility = View.INVISIBLE
                        mondayStartTime.text = i.data.getValue("Open").toString()
                        mondayEndTime.text = i.data.getValue("Close").toString()
                    }
                    else{
                        mondayClose.visibility = View.GONE
                        mondayStartTime.visibility = View.VISIBLE
                        mondayLine.visibility = View.VISIBLE
                        mondayEndTime.visibility = View.VISIBLE
                        mondayStartTime.text = i.data.getValue("Open").toString()
                        mondayEndTime.text = i.data.getValue("Close").toString()
                    }
                }
                else if(i.id == "Tuesday"){
                    if(i.data.getValue("CloseAllDay").toString().toBoolean()){
                        tuesdayClose.visibility = View.VISIBLE
                        tuesdayStartTime.visibility = View.GONE
                        tuesdayLine.visibility = View.INVISIBLE
                        tuesdayEndTime.visibility = View.INVISIBLE
                        tuesdayStartTime.text = i.data.getValue("Open").toString()
                        tuesdayEndTime.text = i.data.getValue("Close").toString()
                    }
                    else{
                        tuesdayClose.visibility = View.GONE
                        tuesdayStartTime.visibility = View.VISIBLE
                        tuesdayLine.visibility = View.VISIBLE
                        tuesdayEndTime.visibility = View.VISIBLE
                        tuesdayStartTime.text = i.data.getValue("Open").toString()
                        tuesdayEndTime.text = i.data.getValue("Close").toString()
                    }
                }
                else if(i.id == "Wednesday"){
                    if(i.data.getValue("CloseAllDay").toString().toBoolean()){
                        wednesdayClose.visibility = View.VISIBLE
                        wednesdayStartTime.visibility = View.GONE
                        wednesdayLine.visibility = View.INVISIBLE
                        wednesdayEndTime.visibility = View.INVISIBLE
                        wednesdayStartTime.text = i.data.getValue("Open").toString()
                        wednesdayEndTime.text = i.data.getValue("Close").toString()
                    }
                    else{
                        wednesdayClose.visibility = View.GONE
                        wednesdayStartTime.visibility = View.VISIBLE
                        wednesdayLine.visibility = View.VISIBLE
                        wednesdayEndTime.visibility = View.VISIBLE
                        wednesdayStartTime.text = i.data.getValue("Open").toString()
                        wednesdayEndTime.text = i.data.getValue("Close").toString()
                    }
                }
                else if(i.id == "Thursday"){
                    if(i.data.getValue("CloseAllDay").toString().toBoolean()){
                        thursdayClose.visibility = View.VISIBLE
                        thursdayStartTime.visibility = View.GONE
                        thursdayLine.visibility = View.INVISIBLE
                        thursdayEndTime.visibility = View.INVISIBLE
                        thursdayStartTime.text = i.data.getValue("Open").toString()
                        thursdayEndTime.text = i.data.getValue("Close").toString()
                    }
                    else{
                        thursdayClose.visibility = View.GONE
                        thursdayStartTime.visibility = View.VISIBLE
                        thursdayLine.visibility = View.VISIBLE
                        thursdayEndTime.visibility = View.VISIBLE
                        thursdayStartTime.text = i.data.getValue("Open").toString()
                        thursdayEndTime.text = i.data.getValue("Close").toString()
                    }
                }
                else if(i.id == "Friday"){
                    if(i.data.getValue("CloseAllDay").toString().toBoolean()){
                        fridayClose.visibility = View.VISIBLE
                        fridayStartTime.visibility = View.GONE
                        fridayLine.visibility = View.INVISIBLE
                        fridayEndTime.visibility = View.INVISIBLE
                        fridayStartTime.text = i.data.getValue("Open").toString()
                        fridayEndTime.text = i.data.getValue("Close").toString()
                    }
                    else{
                        fridayClose.visibility = View.GONE
                        fridayStartTime.visibility = View.VISIBLE
                        fridayLine.visibility = View.VISIBLE
                        fridayEndTime.visibility = View.VISIBLE
                        fridayStartTime.text = i.data.getValue("Open").toString()
                        fridayEndTime.text = i.data.getValue("Close").toString()
                    }
                }
                else if(i.id == "Saturday"){
                    if(i.data.getValue("CloseAllDay").toString().toBoolean()){
                        saturdayClose.visibility = View.VISIBLE
                        saturdayStartTime.visibility = View.GONE
                        saturdayLine.visibility = View.INVISIBLE
                        saturdayEndTime.visibility = View.INVISIBLE
                        saturdayStartTime.text = i.data.getValue("Open").toString()
                        saturdayEndTime.text = i.data.getValue("Close").toString()
                    }
                    else{
                        saturdayClose.visibility = View.GONE
                        saturdayStartTime.visibility = View.VISIBLE
                        saturdayLine.visibility = View.VISIBLE
                        saturdayEndTime.visibility = View.VISIBLE
                        saturdayStartTime.text = i.data.getValue("Open").toString()
                        saturdayEndTime.text = i.data.getValue("Close").toString()
                    }
                }
                else if(i.id == "Sunday"){
                    if(i.data.getValue("CloseAllDay").toString().toBoolean()){
                        sundayClose.visibility = View.VISIBLE
                        sundayStartTime.visibility = View.GONE
                        sundayLine.visibility = View.INVISIBLE
                        sundayEndTime.visibility = View.INVISIBLE
                        sundayStartTime.text = i.data.getValue("Open").toString()
                        sundayEndTime.text = i.data.getValue("Close").toString()
                    }
                    else{
                        sundayClose.visibility = View.GONE
                        sundayStartTime.visibility = View.VISIBLE
                        sundayLine.visibility = View.VISIBLE
                        sundayEndTime.visibility = View.VISIBLE
                        sundayStartTime.text = i.data.getValue("Open").toString()
                        sundayEndTime.text = i.data.getValue("Close").toString()
                    }
                }
            }
        }

        btnBack.setOnClickListener {
//            val intent = Intent(this, RestaurantHomeActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            startActivity(intent)
            finish()
        }

        mondayStartTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                val start = SimpleDateFormat("HH:mm").format(cal.time)
                val startTime = LocalDateTime.parse("2002-03-23 $start", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                fb.get().addOnCompleteListener {
                    for (i in it.result) {
                        if(i.id == "Monday"){
                            val end  = i.data.getValue("Close").toString()
                            val endTime = LocalDateTime.parse("2002-03-23 $end", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                            if(endTime < startTime){
                                Toast.makeText(this, "Open time is incorrect", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                mondayStartTime.text = start
                                fb.document("Monday").update("Open", start)
                            }
                        }
                    }
                }
            }
            TimePickerDialog(this,
                R.style.TimePickerTheme, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        mondayEndTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                var end = SimpleDateFormat("HH:mm").format(cal.time)
                var endTime = LocalDateTime.parse("2002-03-23 $end", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                fb.get().addOnCompleteListener {
                    for (i in it.result) {
                        if(i.id == "Monday"){
                            val start  = i.data.getValue("Open").toString()
                            val startTime = LocalDateTime.parse("2002-03-23 $start", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                            if(endTime < startTime){
                                Toast.makeText(this, "Close time is incorrect", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                mondayEndTime.text = end
                                fb.document("Monday").update("Close", end)
                            }
                        }
                    }
                }
            }
            TimePickerDialog(this,
                R.style.TimePickerTheme, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


        tuesdayStartTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                val start = SimpleDateFormat("HH:mm").format(cal.time)
                val startTime = LocalDateTime.parse("2002-03-23 $start", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                fb.get().addOnCompleteListener {
                    for (i in it.result) {
                        if(i.id == "Tuesday"){
                            val end  = i.data.getValue("Close").toString()
                            val endTime = LocalDateTime.parse("2002-03-23 $end", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                            if(endTime < startTime){
                                Toast.makeText(this, "Open time is incorrect", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                tuesdayStartTime.text = start
                                fb.document("Tuesday").update("Open", start)
                            }
                        }
                    }
                }
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        tuesdayEndTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                var end = SimpleDateFormat("HH:mm").format(cal.time)
                var endTime = LocalDateTime.parse("2002-03-23 $end", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                fb.get().addOnCompleteListener {
                    for (i in it.result) {
                        if(i.id == "Tuesday"){
                            val start  = i.data.getValue("Open").toString()
                            val startTime = LocalDateTime.parse("2002-03-23 $start", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                            if(endTime < startTime){
                                Toast.makeText(this, "Close time is incorrect", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                tuesdayEndTime.text = end
                                fb.document("Tuesday").update("Close", end)
                            }
                        }
                    }
                }
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


        wednesdayStartTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                val start = SimpleDateFormat("HH:mm").format(cal.time)
                val startTime = LocalDateTime.parse("2002-03-23 $start", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                fb.get().addOnCompleteListener {
                    for (i in it.result) {
                        if (i.id == "Wednesday") {
                            val end = i.data.getValue("Close").toString()
                            val endTime = LocalDateTime.parse(
                                "2002-03-23 $end",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                            )

                            if (endTime < startTime) {
                                Toast.makeText(this, "Open time is incorrect", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                wednesdayStartTime.text = start
                                fb.document("Wednesday").update("Open", start)
                            }
                        }
                    }
                }
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        wednesdayEndTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                var end = SimpleDateFormat("HH:mm").format(cal.time)
                var endTime = LocalDateTime.parse("2002-03-23 $end", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                fb.get().addOnCompleteListener {
                    for (i in it.result) {
                        if(i.id == "Wednesday"){
                            val start  = i.data.getValue("Open").toString()
                            val startTime = LocalDateTime.parse("2002-03-23 $start", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                            if(endTime < startTime){
                                Toast.makeText(this, "Close time is incorrect", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                wednesdayEndTime.text = end
                                fb.document("Wednesday").update("Close", end)
                            }
                        }
                    }
                }
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


        thursdayStartTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                val start = SimpleDateFormat("HH:mm").format(cal.time)
                val startTime = LocalDateTime.parse("2002-03-23 $start", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                fb.get().addOnCompleteListener {
                    for (i in it.result) {
                        if (i.id == "Thursday") {
                            val end = i.data.getValue("Close").toString()
                            val endTime = LocalDateTime.parse(
                                "2002-03-23 $end",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                            )

                            if (endTime < startTime) {
                                Toast.makeText(this, "Open time is incorrect", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                thursdayStartTime.text = start
                                fb.document("Thursday").update("Open", start)
                            }
                        }
                    }
                }
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        thursdayEndTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                var end = SimpleDateFormat("HH:mm").format(cal.time)
                var endTime = LocalDateTime.parse("2002-03-23 $end", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                fb.get().addOnCompleteListener {
                    for (i in it.result) {
                        if(i.id == "Thursday"){
                            val start  = i.data.getValue("Open").toString()
                            val startTime = LocalDateTime.parse("2002-03-23 $start", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                            if(endTime < startTime){
                                Toast.makeText(this, "Close time is incorrect", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                thursdayEndTime.text = end
                                fb.document("Thursday").update("Close", end)
                            }
                        }
                    }
                }
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


        fridayStartTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                val start = SimpleDateFormat("HH:mm").format(cal.time)
                val startTime = LocalDateTime.parse("2002-03-23 $start", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                fb.get().addOnCompleteListener {
                    for (i in it.result) {
                        if (i.id == "Friday") {
                            val end = i.data.getValue("Close").toString()
                            val endTime = LocalDateTime.parse(
                                "2002-03-23 $end",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                            )

                            if (endTime < startTime) {
                                Toast.makeText(this, "Open time is incorrect", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                fridayStartTime.text = start
                                fb.document("Friday").update("Open", start)
                            }
                        }
                    }
                }
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        fridayEndTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                var end = SimpleDateFormat("HH:mm").format(cal.time)
                var endTime = LocalDateTime.parse("2002-03-23 $end", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                fb.get().addOnCompleteListener {
                    for (i in it.result) {
                        if(i.id == "Friday"){
                            val start  = i.data.getValue("Open").toString()
                            val startTime = LocalDateTime.parse("2002-03-23 $start", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                            if(endTime < startTime){
                                Toast.makeText(this, "Close time is incorrect", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                fridayEndTime.text = end
                                fb.document("Friday").update("Close", end)
                            }
                        }
                    }
                }
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


        saturdayStartTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                val start = SimpleDateFormat("HH:mm").format(cal.time)
                val startTime = LocalDateTime.parse("2002-03-23 $start", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                fb.get().addOnCompleteListener {
                    for (i in it.result) {
                        if (i.id == "Saturday") {
                            val end = i.data.getValue("Close").toString()
                            val endTime = LocalDateTime.parse(
                                "2002-03-23 $end",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                            )

                            if (endTime < startTime) {
                                Toast.makeText(this, "Open time is incorrect", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                saturdayStartTime.text = start
                                fb.document("Saturday").update("Open", start)
                            }
                        }
                    }
                }
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        saturdayEndTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                var end = SimpleDateFormat("HH:mm").format(cal.time)
                var endTime = LocalDateTime.parse("2002-03-23 $end", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                fb.get().addOnCompleteListener {
                    for (i in it.result) {
                        if(i.id == "Saturday"){
                            val start  = i.data.getValue("Open").toString()
                            val startTime = LocalDateTime.parse("2002-03-23 $start", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                            if(endTime < startTime){
                                Toast.makeText(this, "Close time is incorrect", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                saturdayEndTime.text = end
                                fb.document("Saturday").update("Close", end)
                            }
                        }
                    }
                }
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


        sundayStartTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                val start = SimpleDateFormat("HH:mm").format(cal.time)
                val startTime = LocalDateTime.parse("2002-03-23 $start", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                fb.get().addOnCompleteListener {
                    for (i in it.result) {
                        if (i.id == "Sunday") {
                            val end = i.data.getValue("Close").toString()
                            val endTime = LocalDateTime.parse(
                                "2002-03-23 $end",
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                            )

                            if (endTime < startTime) {
                                Toast.makeText(this, "Open time is incorrect", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                sundayStartTime.text = start
                                fb.document("Sunday").update("Open", start)
                            }
                        }
                    }
                }
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        sundayEndTime.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                var end = SimpleDateFormat("HH:mm").format(cal.time)
                var endTime = LocalDateTime.parse("2002-03-23 $end", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                fb.get().addOnCompleteListener {
                    for (i in it.result) {
                        if(i.id == "Sunday"){
                            val start  = i.data.getValue("Open").toString()
                            val startTime = LocalDateTime.parse("2002-03-23 $start", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                            if(endTime < startTime){
                                Toast.makeText(this, "Close time is incorrect", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                sundayEndTime.text = end
                                fb.document("Sunday").update("Close", end)
                            }
                        }
                    }
                }
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        closedRestaurantMonday.setOnClickListener {
            if(mondayClose.visibility != View.VISIBLE){
                mondayStartTime.visibility = View.GONE
                mondayClose.visibility = View.VISIBLE
                mondayEndTime.visibility = View.INVISIBLE
                mondayLine.visibility = View.INVISIBLE
                fb.document("Monday").update("CloseAllDay", true)
            }
            else{
                fb.document("Monday").update("CloseAllDay", false)
                mondayClose.visibility = View.GONE
                mondayLine.visibility = View.VISIBLE
                mondayEndTime.visibility = View.VISIBLE
                mondayStartTime.visibility = View.VISIBLE
            }
        }

        closedRestaurantTuesday.setOnClickListener {
            if(tuesdayClose.visibility != View.VISIBLE){
                tuesdayStartTime.visibility = View.GONE
                tuesdayClose.visibility = View.VISIBLE
                tuesdayEndTime.visibility = View.INVISIBLE
                tuesdayLine.visibility = View.INVISIBLE
                fb.document("Tuesday").update("CloseAllDay", true)
            }
            else{
                fb.document("Tuesday").update("CloseAllDay", false)
                tuesdayClose.visibility = View.GONE
                tuesdayLine.visibility = View.VISIBLE
                tuesdayEndTime.visibility = View.VISIBLE
                tuesdayStartTime.visibility = View.VISIBLE
            }
        }

        closedRestaurantWednesday.setOnClickListener {
            if(wednesdayClose.visibility != View.VISIBLE){
                wednesdayStartTime.visibility = View.GONE
                wednesdayClose.visibility = View.VISIBLE
                wednesdayEndTime.visibility = View.INVISIBLE
                wednesdayLine.visibility = View.INVISIBLE
                fb.document("Wednesday").update("CloseAllDay", true)
            }
            else{
                fb.document("Wednesday").update("CloseAllDay", false)
                wednesdayClose.visibility = View.GONE
                wednesdayLine.visibility = View.VISIBLE
                wednesdayEndTime.visibility = View.VISIBLE
                wednesdayStartTime.visibility = View.VISIBLE
            }
        }

        closedRestaurantThursday.setOnClickListener {
            if(thursdayClose.visibility != View.VISIBLE){
                thursdayStartTime.visibility = View.GONE
                thursdayClose.visibility = View.VISIBLE
                thursdayEndTime.visibility = View.INVISIBLE
                thursdayLine.visibility = View.INVISIBLE
                fb.document("Thursday").update("CloseAllDay", true)
            }
            else{
                fb.document("Thursday").update("CloseAllDay", false)
                thursdayClose.visibility = View.GONE
                thursdayLine.visibility = View.VISIBLE
                thursdayEndTime.visibility = View.VISIBLE
                thursdayStartTime.visibility = View.VISIBLE
            }
        }

        closedRestaurantFriday.setOnClickListener {
            if(fridayClose.visibility != View.VISIBLE){
                fridayStartTime.visibility = View.GONE
                fridayClose.visibility = View.VISIBLE
                fridayEndTime.visibility = View.INVISIBLE
                fridayLine.visibility = View.INVISIBLE
                fb.document("Friday").update("CloseAllDay", true)
            }
            else{
                fb.document("Friday").update("CloseAllDay", false)
                fridayClose.visibility = View.GONE
                fridayLine.visibility = View.VISIBLE
                fridayEndTime.visibility = View.VISIBLE
                fridayStartTime.visibility = View.VISIBLE
            }
        }

        closedRestaurantSaturday.setOnClickListener {
            if(saturdayClose.visibility != View.VISIBLE){
                saturdayStartTime.visibility = View.GONE
                saturdayClose.visibility = View.VISIBLE
                saturdayEndTime.visibility = View.INVISIBLE
                saturdayLine.visibility = View.INVISIBLE
                fb.document("Saturday").update("CloseAllDay", true)
            }
            else{
                fb.document("Saturday").update("CloseAllDay", false)
                saturdayClose.visibility = View.GONE
                saturdayLine.visibility = View.VISIBLE
                saturdayEndTime.visibility = View.VISIBLE
                saturdayStartTime.visibility = View.VISIBLE
            }
        }

        closedRestaurantSunday.setOnClickListener {
            if(sundayClose.visibility != View.VISIBLE){
                sundayStartTime.visibility = View.GONE
                sundayClose.visibility = View.VISIBLE
                sundayEndTime.visibility = View.INVISIBLE
                sundayLine.visibility = View.INVISIBLE
                fb.document("Sunday").update("CloseAllDay", true)
            }
            else{
                fb.document("Sunday").update("CloseAllDay", false)
                sundayClose.visibility = View.GONE
                sundayLine.visibility = View.VISIBLE
                sundayEndTime.visibility = View.VISIBLE
                sundayStartTime.visibility = View.VISIBLE
            }
        }
    }
}