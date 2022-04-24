package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
<<<<<<< Updated upstream
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.base.Verify
=======
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_reset_password.*
>>>>>>> Stashed changes
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.btnBack
import kotlinx.android.synthetic.main.activity_sign_in.btnContinue
import kotlinx.android.synthetic.main.activity_sign_in.editTextEnterPhoneNumber
import kotlinx.android.synthetic.main.activity_sign_in.progressBar
import java.util.concurrent.TimeUnit


class SignInActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

<<<<<<< Updated upstream
        auth=FirebaseAuth.getInstance()

=======
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======
            if(!validPhoneNumber() || !validPassword()){
                Toast.makeText(this, "Login fail", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var fb = FirebaseFirestore.getInstance().collection("Customer")
            fb.get().addOnCompleteListener {
                if (it.isSuccessful) {
                    for (i in it.result) {
                        if (editTextEnterPhoneNumber.text.toString() == i.id) {
                            CheckPassword()
                            return@addOnCompleteListener
                        }
                        else continue
                    }
                    editTextEnterPhoneNumber.error = "Please check your phone number"
                    progressBar.visibility = View.GONE
                    btnContinue.visibility = View.VISIBLE
                }
            }
//            CheckPassword()
//            auth = FirebaseAuth.getInstance()
>>>>>>> Stashed changes
//            if(validPhoneNumber() && validPassword()){
//                Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show()
//            }
//            else{
//                return@setOnClickListener
//            }
<<<<<<< Updated upstream
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
=======
//           login()
        }

        // Callback function for Phone Auth
//        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
//                finish()
//            }
//
//            override fun onVerificationFailed(e: FirebaseException) {
//                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
//            }
//
//            override fun onCodeSent(
//                verificationId: String,
//                token: PhoneAuthProvider.ForceResendingToken
//            ) {
//
//                Log.d("TAG", "onCodeSent:$verificationId")
//                storedVerificationId = verificationId
//                resendToken = token
//
//                var intent = Intent(applicationContext, SignInEnterCode::class.java)
//                intent.putExtra("storedVerificationId", storedVerificationId)
//                startActivity(intent)
//            }
//        }
    }

//    private fun login() {
//
//        var number = editTextEnterPhoneNumber.text.toString().trim()
//
//        if (number.isNotEmpty()) {
//           // if(CheckPassword()) {
//                number = "+84" + number
//                sendVerificationcode(number)
//           // }
//        } else {
//            Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show()
//        }
//
//    }

    private fun CheckPassword(){
        var fb = FirebaseFirestore.getInstance().collection("Customer")
        fb.get().addOnCompleteListener{
            if(it.isSuccessful)
            {
                for (i in it.result)
                {
                    if ((editTextEnterPhoneNumber.text.toString())==i.id)
                    {
                        if (editTextEnterPassword.text.toString()==i.data.getValue("password").toString()) {
                            progressBar.visibility = View.VISIBLE
                            btnContinue.visibility = View.INVISIBLE
                            Toast.makeText(this,
                                "Login successfully",
                                Toast.LENGTH_SHORT).show()
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
//    private fun sendVerificationcode(number: String) {
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(number) // Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(this) // Activity (for callback binding)
//            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }
>>>>>>> Stashed changes

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
