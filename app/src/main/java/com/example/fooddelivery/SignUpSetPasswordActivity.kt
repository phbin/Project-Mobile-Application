package com.example.fooddelivery

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.fooddelivery.model.Customer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up_set_password.*

class SignUpSetPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_set_password)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        btnBack.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        btnCreateAccount.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            btnCreateAccount.visibility = View.INVISIBLE

            val password = editTextEnterPassword.text.toString().trim()
            val confirmPassword = editTextConfirmPassword.text.toString().trim()

            if(password.isEmpty()){
                editTextEnterPassword.error = "Password required"
                progressBar.visibility = View.GONE
                btnCreateAccount.visibility = View.VISIBLE
                return@setOnClickListener
            }
            if(password.length < 6){
                editTextEnterPassword.error = "Password must have atleast 6 characters"
                progressBar.visibility = View.GONE
                btnCreateAccount.visibility = View.VISIBLE
                return@setOnClickListener
            }
            if(password != confirmPassword){
                editTextConfirmPassword.error = "Please make sure your password match"
                progressBar.visibility = View.GONE
                btnCreateAccount.visibility = View.VISIBLE
                return@setOnClickListener
            }
            SignUpAccount()
        }
    }

//    private fun SetPassword()
//    {
//        var intent:Intent= intent
//        val textPhone:String=intent.getStringExtra("noPhone").toString()
//
//        var fb=FirebaseFirestore.getInstance().collection("Customer")
//            .document(""+textPhone)
//            .update("password",""+editTextEnterPassword.text)
//    }

    private fun SignUpAccount(){

        val name = intent.getStringExtra("name")
        val phoneNumber = intent.getStringExtra("noPhone")
        val email = intent.getStringExtra("email")
        val password = editTextEnterPassword.text.toString()

        val passwordHashed = BCrypt.withDefaults().hashToString(12, password.toCharArray())

        var fb = FirebaseFirestore.getInstance().collection("Customer")
        var cus=Customer(""+name,""+email,""+passwordHashed)
        if (phoneNumber != null) {
            fb.document(phoneNumber).set(cus).addOnSuccessListener {
                Toast.makeText(this, "Create account successfully",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener{
                Toast.makeText(this,"Fail!",Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
                btnCreateAccount.visibility = View.VISIBLE
            }
//            SetPassword()
        }
        else{
            progressBar.visibility = View.GONE
            btnCreateAccount.visibility = View.VISIBLE

        }
    }
}