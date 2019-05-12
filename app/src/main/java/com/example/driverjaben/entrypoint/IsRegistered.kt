package com.example.driverjaben.entrypoint

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.driverjaben.R
import com.example.driverjaben.Registration
import com.example.driverjaben.model.ServerResponse
import com.example.driverjaben.retrofit.ApiInterface
import com.example.driverjaben.retrofit.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IsRegistered : AppCompatActivity() {

    lateinit var phoneNumber: String
    lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_is_registered)

        supportActionBar?.hide()

        phoneNumber = intent.getStringExtra("phoneNum")

        getActivity()
    }

    private fun getActivity() {
        apiInterface = ApiUtils.getApiService()

        apiInterface.driverExist(phoneNumber).enqueue(object : Callback<ServerResponse> {
            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {
                val res = response.body()

                if (res!!.success == "1") {
                    val intent = Intent(this@IsRegistered, Home::class.java)
                    startActivity(intent)
                    finish()
                } else if (res.success == "0") {
                    val intent = Intent(this@IsRegistered, Registration::class.java)
                    intent.putExtra("phoneNum", phoneNumber)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {

            }
        })
    }
}
