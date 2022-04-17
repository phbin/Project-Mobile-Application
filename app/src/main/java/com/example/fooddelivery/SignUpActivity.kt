package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fooddelivery.model.Customer
import com.google.firebase.firestore.FirebaseFirestore
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
            SignUpAccount()
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
                val intent = Intent(this, SignUpSetPasswordActivity::class.java)
                intent.putExtra("noPhone",editTextEnterPhoneNumber.text.toString())
                startActivity(intent)
            }
        }
    }
    private fun SignUpAccount(){
        var fb = FirebaseFirestore.getInstance().collection("Customer")
        var cus=Customer(""+editTextEnterName.text.toString(),""+editTextEnterEmail.text.toString(),"")
        fb.document(editTextEnterPhoneNumber.text.toString()).set(cus).addOnSuccessListener {
                Toast.makeText(this,"Success!",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Fail!",Toast.LENGTH_SHORT).show()
            }
    }
}