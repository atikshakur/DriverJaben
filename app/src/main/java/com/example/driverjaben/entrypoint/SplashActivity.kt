package com.example.driverjaben.entrypoint

import android.content.Intent
import android.os.Handler
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.driverjaben.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar!!.hide()

        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, IsLoggedIn::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}
