package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        btnBack.setOnClickListener {
            Toast.makeText(this,"Clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }

        btnContinue.setOnClickListener {
            Toast.makeText(this,"Clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(this,ResetPasswordEnterCodeActivity::class.java)
            startActivity(intent)
        }

    }
}