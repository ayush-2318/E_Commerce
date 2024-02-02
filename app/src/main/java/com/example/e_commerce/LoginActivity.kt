package com.example.e_commerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var edtEmail:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnLogin:Button
    private lateinit var btnSignup:Button
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        edtEmail=findViewById(R.id.et_email)
        edtPassword=findViewById(R.id.et_password)
        btnLogin=findViewById(R.id.btn_login)
        btnSignup=findViewById(R.id.btn_signup)
        auth= Firebase.auth

        btnLogin.setOnClickListener {
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()
            logIn(email,password)
        }
        btnSignup.setOnClickListener {
            val email=edtEmail.text.toString()
            val password=edtPassword.text.toString()
            signUp(email,password)
        }
    }
    // login function
    private fun logIn(email:String,password:String){
        // logic for sign in
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                   // show product upload screen
                    startActivity(
                        Intent(this,UploadProductActivity::class.java)
                    )
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "some error occured", Toast.LENGTH_SHORT).show()

                }
            }

    }
    // signup function
    private fun signUp(email: String,password: String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // show product upload screen
                    startActivity(
                        Intent(this,UploadProductActivity::class.java)
                    )
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "some error occured", Toast.LENGTH_SHORT).show()

                }
            }
    }
}