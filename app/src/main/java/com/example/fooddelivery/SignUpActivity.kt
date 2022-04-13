package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.btnBack
import kotlinx.android.synthetic.main.activity_sign_up.btnContinue
import kotlinx.android.synthetic.main.activity_sign_up.editTextEnterPhoneNumber


class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btnBack.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        btnContinue.setOnClickListener {
            val phoneNumber = editTextEnterPhoneNumber.text.toString().trim()

            if(phoneNumber.isEmpty()){
                editTextEnterPhoneNumber.error = "Phone number required"
                return@setOnClickListener
            }
            else if(phoneNumber.length != 10){
                editTextEnterPhoneNumber.error = "Phone number required 10 character"
                return@setOnClickListener
            }
            else{
                val intent = Intent(this, SignUpEnterCodeActivity::class.java)
                startActivity(intent)
            }
        }
    }
}