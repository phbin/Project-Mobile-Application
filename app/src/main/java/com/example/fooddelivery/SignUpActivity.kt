package com.example.fooddelivery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern


class SignUpActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnContinue.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            btnContinue.visibility = View.INVISIBLE

            auth = FirebaseAuth.getInstance()
            val phoneNumber = editTextEnterPhoneNumber.text.toString().trim()
            val email = editTextEnterEmail.text.toString().trim()
            val name = editTextEnterName.text.toString()

            if (name.isEmpty()) {
                editTextEnterName.error = "Name required!"
                progressBar.visibility = View.GONE
                btnContinue.visibility = View.VISIBLE
                return@setOnClickListener
            } else if (phoneNumber.isEmpty()) {
                editTextEnterPhoneNumber.error = "Phone number required"
                progressBar.visibility = View.GONE
                btnContinue.visibility = View.VISIBLE
                return@setOnClickListener
            } else if (phoneNumber.length != 10) {
                editTextEnterPhoneNumber.error = "Please check your phone number again"
                progressBar.visibility = View.GONE
                btnContinue.visibility = View.VISIBLE
                return@setOnClickListener
            } else if (!validateEmail.matcher(email).matches()) {
                editTextEnterEmail.error = "Please check your email"
                progressBar.visibility = View.GONE
                btnContinue.visibility = View.VISIBLE
                return@setOnClickListener
            } else {
                var fb = FirebaseFirestore.getInstance().collection("Customer")
                fb.get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (i in it.result) {
                            if ((editTextEnterPhoneNumber.text.toString()) == i.id) {
                                editTextEnterPhoneNumber.error = "This phone number existed"
                                progressBar.visibility = View.GONE
                                btnContinue.visibility = View.VISIBLE
                                return@addOnCompleteListener
                            } else continue
                        }
                        AuthPhoneNumber()
                    }
                }
            }

//            if(checkExistedPhoneNumber()){
//                return@setOnClickListener
//                progressBar.visibility = View.GONE
//                btnContinue.visibility = View.VISIBLE
//            }
//            else{
//                AuthPhoneNumber()
//            }


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

                var intent = Intent(applicationContext, SignUpEnterCodeActivity::class.java)
                intent.putExtra("storedVerificationId", storedVerificationId)
                intent.putExtra("noPhone", editTextEnterPhoneNumber.text.toString())
                intent.putExtra("email", editTextEnterEmail.text.toString())
                intent.putExtra("name", editTextEnterName.text.toString())
                startActivity(intent)
            }
        }
    }

//    private fun SignUpAccount(){
//        var fb = FirebaseFirestore.getInstance().collection("Customer")
//        var cus=Customer(""+editTextEnterName.text.toString(),""+editTextEnterEmail.text.toString(),"")
//        fb.document(editTextEnterPhoneNumber.text.toString()).set(cus).addOnSuccessListener {
//            val intent = Intent(this, SignInActivity::class.java)
//            startActivity(intent)
//            }.addOnFailureListener{
//                Toast.makeText(this,"Fail!",Toast.LENGTH_SHORT).show()
//            }
//    }

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

    private val validateEmail: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    private fun checkExistedPhoneNumber(): Boolean {
        var existed = false
        var fb = FirebaseFirestore.getInstance().collection("Customer")
        fb.get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (i in it.result) {
                    if ((editTextEnterPhoneNumber.text.toString()) == i.id) {
                        editTextEnterPhoneNumber.error = "This phone number existed"
                        existed = true
                    } else continue
                }
            }
        }

        return existed
    }
}