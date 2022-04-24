package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_reset_password_enter_code.*

class ResetPasswordEnterCodeActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password_enter_code)

        editTextCodeNumber1.requestFocus()

        auth=FirebaseAuth.getInstance()
        val storedVerificationId=intent.getStringExtra("storedVerificationId")

        btnBack.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

        autoNextCode()

        btnContinue.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            btnContinue.visibility = View.INVISIBLE
//            val intent = Intent(this,ResetPasswordSetPasswordActivity::class.java)
//            startActivity(intent)
            var otp=editTextCodeNumber1.text.toString() + editTextCodeNumber2.text.toString() + editTextCodeNumber3.text.toString() + editTextCodeNumber4.text.toString() + editTextCodeNumber5.text.toString() + editTextCodeNumber6.text.toString()
            if(otp.isNotEmpty()){
                val credential : PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp)
                signInWithPhoneAuthCredential(credential)
            }else{
                Toast.makeText(this,"Enter OTP", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
                btnContinue.visibility = View.VISIBLE
            }
        }
    }

    private fun autoNextCode() {
        editTextCodeNumber1.addTextChangedListener {
            if(editTextCodeNumber1.text.toString().isNotEmpty() && editTextCodeNumber1.text.toString() != ""){
                editTextCodeNumber2.requestFocus()
            }
        }
        editTextCodeNumber2.addTextChangedListener {
            if(editTextCodeNumber1.text.toString().isNotEmpty() && editTextCodeNumber2.text.toString() != ""){
                editTextCodeNumber3.requestFocus()
            }
        }
        editTextCodeNumber3.addTextChangedListener {
            if(editTextCodeNumber1.text.toString().isNotEmpty() && editTextCodeNumber3.text.toString() != ""){
                editTextCodeNumber4.requestFocus()
            }
        }
        editTextCodeNumber4.addTextChangedListener {
            if(editTextCodeNumber1.text.toString().isNotEmpty() && editTextCodeNumber4.text.toString() != ""){
                editTextCodeNumber5.requestFocus()
            }
        }
        editTextCodeNumber5.addTextChangedListener {
            if(editTextCodeNumber1.text.toString().isNotEmpty() && editTextCodeNumber5.text.toString() != ""){
                editTextCodeNumber6.requestFocus()
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        val phoneNumber = intent.getStringExtra("noPhone")

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, ResetPasswordSetPasswordActivity::class.java)
                    intent.putExtra("noPhone", phoneNumber)
                    startActivity(intent)
                    finish()
                } else {
                    progressBar.visibility = View.GONE
                    btnContinue.visibility = View.VISIBLE
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this,"Invalid OTP",Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.GONE
                        btnContinue.visibility = View.VISIBLE
                    }
                }
            }
    }
}