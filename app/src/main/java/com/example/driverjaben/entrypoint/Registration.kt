package com.example.driverjaben.entrypoint

import android.Manifest
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast

import com.example.driverjaben.model.Driver
import com.example.driverjaben.model.ServerResponse
import com.example.driverjaben.retrofit.ApiInterface
import com.example.driverjaben.retrofit.ApiUtils
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.activity_registration.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import p32929.easypermissionlibrary.bitpermission.PermissionListener
import p32929.easypermissionlibrary.p32929.EasyPn
import java.sql.DriverManager.getDriver
import android.location.LocationManager
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.support.v4.content.ContextCompat
import com.example.driverjaben.entrypoint.util.MyLocation
import java.util.*
import kotlin.collections.ArrayList


class Registration : AppCompatActivity() {

    lateinit var apiInterface: ApiInterface
    lateinit var emailPattern: String
    lateinit var phoneNumber: String
    lateinit var driver: Driver
    lateinit var mLocationManager: LocationManager
    var driverLat: Double = 0.0
    var driverLng: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.driverjaben.R.layout.activity_registration)

        locationPermission()


        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val ab = supportActionBar
        ab!!.title = "Registration"

        signup_button.setOnClickListener {

            registerDriver(getDriver1())
        }

    }

    private fun getDriver1(): Driver {
        var name: String = name.text.toString()
        //var phone: String = intent.getStringExtra("phoneNum")
        var phone: String = "num"
        var address: String = address.text.toString()
        var city: String = "ctg"
        var nid: String = nid.text.toString()
        var deviceID: String = ""
        var dateOfBirth: String = date_of_birth.text.toString()
        var drivingLicense: String = driving_license.text.toString()
        var vehicleType: String = "Bike"
        var vehicleName: String = vehicle_brand.text.toString()
        var vehicleModel: String = vehicle_model.text.toString()
        var vehicleColor: String = vehicle_color.text.toString()
        var numberPlate: String = number_plate.text.toString()
        var fitnessCertificate: String = fitness_certificate.text.toString()
        var taxToken: String = tax_token.text.toString()
        var referralMobileNumber: String = referral_mobile_number.text.toString()

        return Driver(name, phone, address, city, nid, deviceID, dateOfBirth, drivingLicense, vehicleType, vehicleName,
                        vehicleModel, vehicleColor, numberPlate, fitnessCertificate, taxToken, referralMobileNumber,
                        driverLat.toString(), driverLng.toString())
    }


    private fun registerDriver(driver: Driver) {

        apiInterface = ApiUtils.getApiService()

        apiInterface.registerDriver(driver).enqueue(object : Callback<ServerResponse> {

            override fun onResponse(call: Call<ServerResponse>, response: Response<ServerResponse>) {

                val res = response.body()

                if (res!!.success == "1") {
                    val intent = Intent(this@Registration, Home::class.java)
                    startActivity(intent)
                    finish()
                } else if (res.success == "0") {
                    Toast.makeText(this@Registration, res.message, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {

            }
        })
    }

    private fun locationPermission() {

        val permissions = ArrayList<Any>()
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)

        EasyPn.getPermission(Manifest.permission.ACCESS_COARSE_LOCATION, this@Registration, object : PermissionListener {
            override fun onPermissionGranted() {
                val locationResult = object : MyLocation.LocationResult() {

                    override fun gotLocation(location: Location?) {

                        driverLat = location!!.latitude
                        driverLng = location.longitude

                        Toast.makeText(this@Registration, "$driverLat --SLocRes-- $driverLat", Toast.LENGTH_SHORT).show()
                    }

                }

                val myLocation = MyLocation()
                myLocation.getLocation(this@Registration, locationResult)
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {

            }
        })

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            this.finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun onProviderDisabled(arg0: String) {}
    fun onProviderEnabled(arg0: String) {}
    fun onStatusChanged(arg0: String, arg1: Int, arg2: Bundle) {}

}
