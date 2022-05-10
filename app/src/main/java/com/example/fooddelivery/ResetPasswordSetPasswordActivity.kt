package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_reset_password_set_password.*

class ResetPasswordSetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password_set_password)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        btnBack.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        btnResetPassword.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            btnResetPassword.visibility = View.INVISIBLE

            val password = editTextEnterPassword.text.toString().trim()
            val confirmPassword = editTextConfirmPassword.text.toString().trim()

            if(password.isEmpty()){
                editTextEnterPassword.error = "Password required"
                progressBar.visibility = View.GONE
                btnResetPassword.visibility = View.VISIBLE
                return@setOnClickListener
            }
            if(password.length < 6){
                editTextEnterPassword.error = "Password must have atleast 6 characters"
                progressBar.visibility = View.GONE
                btnResetPassword.visibility = View.VISIBLE
                return@setOnClickListener
            }
            if(password != confirmPassword){
                editTextConfirmPassword.error = "Please make sure your password match"
                progressBar.visibility = View.GONE
                btnResetPassword.visibility = View.VISIBLE
                return@setOnClickListener
            }
            SetPassword()
        }
    }

    private fun SetPassword()
    {
        var intent:Intent= intent
        val textPhone:String=intent.getStringExtra("noPhone").toString()

        var fb= FirebaseFirestore.getInstance().collection("Customer")
            .document(""+textPhone)
            .update("password",""+editTextEnterPassword.text)
            .addOnCompleteListener {
                Toast.makeText(this, "Reset password successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
    }
}