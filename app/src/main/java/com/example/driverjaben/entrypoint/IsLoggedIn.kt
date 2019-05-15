package com.example.driverjaben.entrypoint

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.driverjaben.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class IsLoggedIn : AppCompatActivity() {

    private var firebaseUser: FirebaseUser? = null

    override fun onStart() {

        firebaseUser = FirebaseAuth.getInstance().currentUser

        if (firebaseUser != null) {

            startActivity(Intent(this, Home::class.java))
            finish()

        } else {
            startActivity(Intent(this, EnterPhone::class.java))
            finish()
        }

        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_is_logged_in)

        val actionBar = supportActionBar
        actionBar!!.hide()
    }
}
