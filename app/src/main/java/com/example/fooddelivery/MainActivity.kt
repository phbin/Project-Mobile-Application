package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fooddelivery.checkout.CheckOutActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCreateAccount.setOnClickListener {
<<<<<<< Updated upstream
            val intent = Intent(this, CheckOutActivity::class.java)
=======
            val intent = Intent(this, SignUpActivity::class.java)
>>>>>>> Stashed changes
            startActivity(intent)
        }

        btnSignIn.setOnClickListener {
            val intent = Intent(this,SignInActivity::class.java)
             startActivity(intent)
        }

        btnLoginWithGoogle.setOnClickListener {
            val intent = Intent(this,CheckOutActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Google clicked", Toast.LENGTH_SHORT).show()
        }
    }
}