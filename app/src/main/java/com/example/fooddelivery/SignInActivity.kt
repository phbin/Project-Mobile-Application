package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class SignInActivity : AppCompatActivity() {

    lateinit var btnBack : ImageButton
    lateinit var btnResetPassword : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btnBack=findViewById(R.id.btnBack)
        btnResetPassword=findViewById(R.id.btnResetPassword)

        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnResetPassword.setOnClickListener {
            val intent = Intent(this,ResetPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}