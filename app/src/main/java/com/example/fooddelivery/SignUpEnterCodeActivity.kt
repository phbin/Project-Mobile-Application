package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_sign_up_enter_code.*

class SignUpEnterCodeActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    private val storedVerificationId=intent.getStringExtra("storedVerificationId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_enter_code)

        auth=FirebaseAuth.getInstance()

        btnBack.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        btnContinue.setOnClickListener {
//            val codeNumber1 = editTextCodeNumber1.text.toString()
//            val codeNumber2 = editTextCodeNumber2.text.toString()
//            val codeNumber3 = editTextCodeNumber3.text.toString()
//            val codeNumber4 = editTextCodeNumber4.text.toString()
//
//            if(codeNumber1.isEmpty() || codeNumber2.isEmpty() || codeNumber3.isEmpty() || codeNumber4.isEmpty()){
//                return@setOnClickListener
//            }
//            else{
//                val intent = Intent(this,SignUpSetPasswordActivity::class.java)
//                startActivity(intent)
//            }
            var otp=editTextCodeNumber1.text.toString() + editTextCodeNumber2.text.toString() + editTextCodeNumber3.text.toString() + editTextCodeNumber4.text.toString() + editTextCodeNumber1.text.toString() + editTextCodeNumber2.text.toString()
            if(otp.isNotEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }else{
                Toast.makeText(this,"Enter OTP",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
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