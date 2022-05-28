package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_reset_password.*
import java.util.concurrent.TimeUnit

class ResetPasswordActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        btnBack.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        btnContinue.setOnClickListener {
//            Toast.makeText(this,"Clicked", Toast.LENGTH_LONG).show()
//            val intent = Intent(this,ResetPasswordEnterCodeActivity::class.java)
//            startActivity(intent)
            progressBar.visibility = View.VISIBLE
            btnContinue.visibility = View.INVISIBLE

            auth = FirebaseAuth.getInstance()

            val phoneNumber = editTextEnterPhoneNumber.text.toString().trim()
//            Toast.makeText(this, phoneNumber, Toast.LENGTH_SHORT).show()

            if (phoneNumber.isEmpty()) {
                editTextEnterPhoneNumber.error = "Phone number required"
                progressBar.visibility = View.GONE
                btnContinue.visibility = View.VISIBLE
                return@setOnClickListener
            } else if (phoneNumber.length != 10) {
                editTextEnterPhoneNumber.error = "Please check your phone number"
                progressBar.visibility = View.GONE
                btnContinue.visibility = View.VISIBLE
                return@setOnClickListener
            } else {
                var fb = FirebaseFirestore.getInstance().collection("Customer")
                fb.get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (i in it.result) {
                            if (editTextEnterPhoneNumber.text.toString() == i.id) {
                                AuthPhoneNumber()
                                return@addOnCompleteListener
                            }
                            else continue
                        }
                        editTextEnterPhoneNumber.error = "Please check your phone number"
                        progressBar.visibility = View.GONE
                        btnContinue.visibility = View.VISIBLE
                    }
                }
            }
        }

        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
                progressBar.visibility = View.GONE
                btnContinue.visibility = View.VISIBLE
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG", "onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token

                var intent = Intent(applicationContext, ResetPasswordEnterCodeActivity::class.java)
                intent.putExtra("storedVerificationId", storedVerificationId)
                intent.putExtra("noPhone", editTextEnterPhoneNumber.text.toString())
                startActivity(intent)
            }
        }
    }

    private fun AuthPhoneNumber() {

        var number = editTextEnterPhoneNumber.text.toString().trim()

        if (number.isNotEmpty()) {
            // if(CheckPassword()) {
            number = "+84$number"
            sendVerificationcode(number)
            // }
        } else {
            Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.GONE
            btnContinue.visibility = View.VISIBLE
        }

    }

    private fun sendVerificationcode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}