package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up_enter_code.*

class SignUpEnterCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_enter_code)

        btnBack.setOnClickListener {
            //Toast.makeText(this,"Clicked back", Toast.LENGTH_LONG).show()
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        btnContinue.setOnClickListener {
            //Toast.makeText(this,"Clicked continue", Toast.LENGTH_LONG).show()
            val intent = Intent(this,SignUpSetPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}