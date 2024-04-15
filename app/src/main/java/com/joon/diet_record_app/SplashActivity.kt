package com.joon.diet_record_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = Firebase.auth

        try {

            Log.d("SPLASH", auth.currentUser!!.uid)

            Toast.makeText(this, "You're already signed up", Toast.LENGTH_LONG).show()

            Handler().postDelayed({

                startActivity(Intent(this, MainActivity::class.java))
                finish()

            }, 3000)

        } catch (e : Exception) {

            Log.d("SPLASH", "You need to sign in!")

            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(this, "Sign up as a non-member!🤞", Toast.LENGTH_LONG).show()

                        Handler().postDelayed({

                            startActivity(Intent(this, MainActivity::class.java))
                            finish()

                        }, 3000)

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "Sign up failed😥", Toast.LENGTH_LONG).show()
                    }
                }
        }

    }
}