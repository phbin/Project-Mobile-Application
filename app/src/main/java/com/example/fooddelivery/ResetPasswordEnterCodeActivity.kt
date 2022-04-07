package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_reset_password_enter_code.*

class ResetPasswordEnterCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password_enter_code)

        btnBack.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

        btnContinue.setOnClickListener {
            val intent = Intent(this,ResetPasswordSetPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}