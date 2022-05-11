package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_sign_in_enter_code.*

class SignInEnterCode : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_enter_code)
        editTextCodeNumber1.requestFocus()

        auth=FirebaseAuth.getInstance()

        val storedVerificationId=intent.getStringExtra("storedVerificationId")

        btnBack.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        btnContinue.setOnClickListener {
            var otp = editTextCodeNumber1.text.toString() + editTextCodeNumber2.text.toString() + editTextCodeNumber3.text.toString() + editTextCodeNumber4.text.toString() + editTextCodeNumber5.text.toString() + editTextCodeNumber6.text.toString()
            //var otp=otpGiven.text.toString().trim()
            if(otp.isNotEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }else{
                Toast.makeText(this,"Enter OTP",Toast.LENGTH_SHORT).show()
            }
        }

        editTextCodeNumber1.addTextChangedListener {
            if(editTextCodeNumber1.text.toString().isNotEmpty()){
                editTextCodeNumber2.requestFocus()
            }
        }
        editTextCodeNumber2.addTextChangedListener {
            if(editTextCodeNumber1.text.toString().isNotEmpty()){
                editTextCodeNumber2.requestFocus()
            }
        }
        editTextCodeNumber3.addTextChangedListener {
            if(editTextCodeNumber1.text.toString().isNotEmpty()){
                editTextCodeNumber2.requestFocus()
            }
        }
        editTextCodeNumber4.addTextChangedListener {
            if(editTextCodeNumber1.text.toString().isNotEmpty()){
                editTextCodeNumber2.requestFocus()
            }
        }
        editTextCodeNumber5.addTextChangedListener {
            if(editTextCodeNumber1.text.toString().isNotEmpty()){
                editTextCodeNumber2.requestFocus()
            }
        }
        editTextCodeNumber6.addTextChangedListener {
            if(editTextCodeNumber1.text.toString().isNotEmpty()){
                editTextCodeNumber2.requestFocus()
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,"Login success ne",Toast.LENGTH_SHORT).show()
                    //startActivity(Intent(applicationContext, Home::class.java))
                    finish()
// ...
                } else {
// Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
// The verification code entered was invalid
                        Toast.makeText(this,"Invalid OTP",Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}