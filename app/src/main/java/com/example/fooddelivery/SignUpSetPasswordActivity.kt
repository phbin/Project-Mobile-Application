package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up_set_password.*
import kotlinx.android.synthetic.main.activity_sign_up_set_password.btnBack
import kotlinx.android.synthetic.main.activity_sign_up_set_password.editTextConfirmPassword

class SignUpSetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_set_password)

        btnBack.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        btnCreateAccount.setOnClickListener {
            val password = editTextEnterPassword.text.toString().trim()
            val confirmPassword = editTextConfirmPassword.text.toString().trim()

            if(password.isEmpty()){
                editTextEnterPassword.error = "Password required"
                return@setOnClickListener
            }
            else if(password != confirmPassword){
                editTextConfirmPassword.error = "Please make sure your password match"
                return@setOnClickListener
            }
            else{
                editTextEnterPassword.isEnabled = true
                Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }
}