package com.example.driverjaben.entrypoint

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View

import com.example.driverjaben.R
import kotlinx.android.synthetic.main.activity_enter_phone.*

class EnterPhone : AppCompatActivity() {

    lateinit var phoneNumber: String

    private var prog: Int = 0

    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_phone)

        val actionBar = supportActionBar
        actionBar!!.hide()

        btn_next.setOnClickListener {

            validation()
        }

    }

    private fun validation() {
        phoneNumber = phone_number.text.toString()

        if (phoneNumber.isEmpty()) {

            txt_wrong.visibility = View.VISIBLE

            txt_wrong.text = "The phone number can't be empty"

        } else if (phoneNumber.length < 10 || phoneNumber.length > 11) {
            txt_wrong.visibility = View.VISIBLE
            txt_wrong.text = "Please input 10 number Phone"
        } else {
            txt_wrong.visibility = View.GONE


            phoneProgress.visibility = View.VISIBLE

            Thread(Runnable {
                for (i in 0..4) {
                    prog += 20
                    handler.post(Runnable {
                        phoneProgress.setProgress(prog)
                        if (prog == phoneProgress.getMax()) {

                            phoneNumber = phone_number.text.toString()

                            val intent = Intent(this, EnterOTP::class.java)
                            intent.putExtra("phoneNum", "880$phoneNumber")
                            startActivity(intent)
                            finish()
                        }
                    })

                    try {

                        Thread.sleep(1200)

                    } catch (e: InterruptedException) {

                    }

                }
            }).start()

        }

    }
}
