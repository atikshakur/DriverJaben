package com.example.driverjaben.driver.loginandsignup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.driverjaben.R
import kotlinx.android.synthetic.main.activity_enter_phone.*

class EnterPhone : AppCompatActivity() {

    lateinit var phoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_phone)

        val actionBar = supportActionBar
        actionBar!!.hide()

        btn_next.setOnClickListener {
            phoneNumber = phone_number.text.toString()

            var intent = Intent(this, EnterOTP::class.java)
            intent.putExtra("phoneNum", "880$phoneNumber")
            startActivity(intent)
        }

    }
}
