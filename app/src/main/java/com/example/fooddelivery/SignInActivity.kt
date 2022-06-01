package com.example.fooddelivery

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.fooddelivery.Customer.CheckOutActivity
import com.example.fooddelivery.Customer.HomeActivity
import com.example.fooddelivery.Restaurant.RestaurantHomeActivity
import com.example.fooddelivery.Shipper.ShipperActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var sharedPreferences : SharedPreferences
    var isRemember = false

    private var roles = arrayOf("Customer", "Shipper", "Restaurant")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)


        var roles = arrayOf("Customer", "Shipper", "Restaurant")
        var adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, roles)

        autoCompleteTextViewRole.threshold = 0
        autoCompleteTextViewRole.setAdapter(adapter)

        dropdownImageView.setOnClickListener {
            autoCompleteTextViewRole.showDropDown()
        }


        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnResetPassword.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

//        var currentUser = auth.currentUser
//        if(currentUser != null) {
//            Toast.makeText(this, "Hi", Toast.LENGTH_LONG).show()
//            finish()
//        }

        btnContinue.setOnClickListener {
            if (!validPhoneNumber() || !validPassword()) {
//                Toast.makeText(this, "Login fail", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (autoCompleteTextViewRole.text.toString() == "Customer") {
                var fb = FirebaseFirestore.getInstance().collection("Customer")
                fb.get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (i in it.result) {
                            if (editTextEnterPhoneNumber.text.toString() == i.id) {
                                CheckPassword()
                                return@addOnCompleteListener
                            } else continue
                        }
                        editTextEnterPhoneNumber.error = "Please check your phone number"
                        progressBar.visibility = View.GONE
                        btnContinue.visibility = View.VISIBLE
                    }
                }
            }
            if (autoCompleteTextViewRole.text.toString() == "Shipper") {
                var fb = FirebaseFirestore.getInstance().collection("Shipper")
                fb.get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (i in it.result) {
                            if (editTextEnterPhoneNumber.text.toString() == i.id) {
                                CheckShipperPassword()
                                return@addOnCompleteListener
                            } else continue
                        }
                        editTextEnterPhoneNumber.error = "Please check your phone number"
                        progressBar.visibility = View.GONE
                        btnContinue.visibility = View.VISIBLE
                    }
                }
            }
            if (autoCompleteTextViewRole.text.toString() == "Restaurant") {
                var fb = FirebaseFirestore.getInstance().collection("Restaurant")
                fb.get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (i in it.result) {
                            if (editTextEnterPhoneNumber.text.toString() == i.id) {
                                CheckRestaurantPassword()
                                return@addOnCompleteListener
                            } else continue
                        }
                        editTextEnterPhoneNumber.error = "Please check your phone number"
                        progressBar.visibility = View.GONE
                        btnContinue.visibility = View.VISIBLE
                    }
                }
            }
        }

    }

    private fun CheckPassword(){
        val password = editTextEnterPassword.text.toString()
        var fb = FirebaseFirestore.getInstance().collection("Customer")
        fb.get().addOnCompleteListener{
            if(it.isSuccessful)
            {
                for (i in it.result)
                {
                    if ((editTextEnterPhoneNumber.text.toString())==i.id)
                    {
                        val result : BCrypt.Result = BCrypt.verifyer().verify(password.toCharArray(), i.data.getValue("password").toString())
//                        if (editTextEnterPassword.text.toString()==i.data.getValue("password").toString()) {
//                            progressBar.visibility = View.VISIBLE
//                            btnContinue.visibility = View.INVISIBLE
//                            Toast.makeText(this,
//                                "Login successfully",
//                                Toast.LENGTH_SHORT).show()
//                        } else {
//                            Toast.makeText(this,
//                                "Wrong password",
//                                Toast.LENGTH_SHORT).show()
//                        }
                        if (result.verified) {
                            progressBar.visibility = View.VISIBLE
                            btnContinue.visibility = View.INVISIBLE

                            val editor : SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("ID", editTextEnterPhoneNumber.text.toString())
                            editor.putBoolean("REMEMBER", true)
                            editor.putString("ROLE", autoCompleteTextViewRole.text.toString())
                            editor.apply()

//                            val intent = Intent(this, HomeActivity::class.java)
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            val intent = Intent(this, SplashScreenActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this,
                                "Wrong password",
                                Toast.LENGTH_SHORT).show()

                        }
                    } else continue
                }
            }
        }
    }

    private fun CheckShipperPassword(){
        val password = editTextEnterPassword.text.toString()
        var fb = FirebaseFirestore.getInstance().collection("Shipper")
        fb.get().addOnCompleteListener{
            if(it.isSuccessful)
            {
                for (i in it.result)
                {
                    if ((editTextEnterPhoneNumber.text.toString())==i.id)
                    {
                        val result : BCrypt.Result = BCrypt.verifyer().verify(password.toCharArray(), i.data.getValue("password").toString())
                        if (result.verified) {

                            progressBar.visibility = View.VISIBLE
                            btnContinue.visibility = View.INVISIBLE

                            val editor : SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("ID", editTextEnterPhoneNumber.text.toString())
                            editor.putBoolean("REMEMBER", true)
                            editor.putString("ROLE", autoCompleteTextViewRole.text.toString())
                            editor.apply()

                            val intent = Intent(this, SplashScreenActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this,
                                "Wrong password",
                                Toast.LENGTH_SHORT).show()
                        }
                    } else continue
                }
            }
        }
    }

    private fun CheckRestaurantPassword(){
        val password = editTextEnterPassword.text.toString()
        var fb = FirebaseFirestore.getInstance().collection("Restaurant")
        fb.get().addOnCompleteListener{
            if(it.isSuccessful)
            {
                for (i in it.result)
                {
                    if ((editTextEnterPhoneNumber.text.toString())==i.id){
                        val result : BCrypt.Result = BCrypt.verifyer().verify(password.toCharArray(), i.data.getValue("password").toString())
                        if (result.verified) {
                            progressBar.visibility = View.VISIBLE
                            btnContinue.visibility = View.INVISIBLE

                            val editor : SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("ID", editTextEnterPhoneNumber.text.toString())
                            editor.putBoolean("REMEMBER", true)
                            editor.putString("ROLE", autoCompleteTextViewRole.text.toString())
                            editor.apply()

                            val intent = Intent(this, SplashScreenActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this,
                                "Wrong password",
                                Toast.LENGTH_SHORT).show()
                        }
                    } else continue
                }
            }
        }
    }

    private fun validPhoneNumber() : Boolean{
        val phoneNumber = editTextEnterPhoneNumber.text.toString().trim()

        return when {
            phoneNumber.isEmpty() -> {
                editTextEnterPhoneNumber.error = "Phone number required"
                false
            }
            phoneNumber.length != 10 -> {
                editTextEnterPhoneNumber.error = "Please check your phone number"
                false
            }
            else -> {
                true
            }
        }
    }

    private fun validPassword() : Boolean{
        val password = editTextEnterPassword.text.toString().trim()

        return if(password.isEmpty()){
            editTextEnterPassword.error = "Password required"
            false
        }else if(password.length < 6){
            editTextEnterPassword.error = "Password must have atleast 6 characters"
            false
        }
        else{
            true
        }
    }
}

