package com.example.fooddelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
<<<<<<< Updated upstream
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
=======
import com.example.fooddelivery.checkout.CheckOutActivity
import com.google.android.gms.auth.api.credentials.IdToken
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
>>>>>>> Stashed changes
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        private const val RC_SIGN_IN = 120
    }

    private lateinit var mAuth : FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCreateAccount.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        btnSignIn.setOnClickListener {
            val intent = Intent(this,SignInActivity::class.java)
             startActivity(intent)
        }

        btnLoginWithGoogle.setOnClickListener {
<<<<<<< Updated upstream
            Toast.makeText(this, "Google clicked", Toast.LENGTH_SHORT).show()
=======
//            val intent = Intent(this,CheckOutActivity::class.java)
//            startActivity(intent)
//            Toast.makeText(this, "Google clicked", Toast.LENGTH_SHORT).show()
            signIn()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("700807142483-jjmh8dr1jlchvlg0s22tjo9luf9gm2qa.apps.googleusercontent.com")
            . requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        mAuth = FirebaseAuth.getInstance()



    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Result returned from launching the Intent form GoogleSignInApi.getSignInIntent(...);
        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception

            if(task.isSuccessful){
                try {
                    //Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                }
                catch(e: ApiException){
                    //Google Sign In fail, update UI appropriately
                    Log.d("SignInActivity", "Google sign in fail", e)
                }
            }
            else{
                Log.d("SignInActivity", exception.toString())
            }
>>>>>>> Stashed changes
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    //Sign in success
                    Toast.makeText(this, "Login with Google successfully", Toast.LENGTH_SHORT).show()
                    Log.d("SignInActivity", "SignInWithCredential:success")
                    finish()
                }else{
                    //Fail
                    Toast.makeText(this, "Login with Google failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}