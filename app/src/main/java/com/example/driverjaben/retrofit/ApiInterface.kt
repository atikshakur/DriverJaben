package com.example.driverjaben.retrofit

import com.example.driverjaben.model.Driver
import com.example.driverjaben.model.Phone
import com.example.driverjaben.model.ServerResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface ApiInterface {

    /*
     * Driver Registration
     * POST driver information
     */
    @POST("driver/register")
    fun registerDriver(@Body driver: Driver): Call<ServerResponse>


    /*
     * Driver Exist or not
     */
    @POST("driver/login")
    fun driverExist(@Body phone: Phone): Call<ServerResponse>
}