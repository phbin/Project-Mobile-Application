package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.base.Verify
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.util.concurrent.TimeUnit


class SignInActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth=FirebaseAuth.getInstance()

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
//            if(validPhoneNumber() && validPassword()){
//                Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show()
//            }
//            else{
//                return@setOnClickListener
//            }
            login()
        }

        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG","onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token

                var intent = Intent(applicationContext,SignInEnterCode::class.java)
                intent.putExtra("storedVerificationId",storedVerificationId)
                startActivity(intent)
            }
        }
    }

    private fun login() {
        var number = editTextEnterPhoneNumber.text.toString().trim()

        if (number.isNotEmpty()) {
            number = "+84" + number
            sendVerificationcode(number)
        } else {
            Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show()
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

    private fun validPhoneNumber() : Boolean{
        val phoneNumber = editTextEnterPhoneNumber.text.toString().trim()

        return when {
            phoneNumber.isEmpty() -> {
                editTextEnterPhoneNumber.error = "Phone number required"
                false
            }
            phoneNumber.length != 10 -> {
                editTextEnterPhoneNumber.error = "Phone number required 10 character"
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
        } else{
            true
        }
    }
}
