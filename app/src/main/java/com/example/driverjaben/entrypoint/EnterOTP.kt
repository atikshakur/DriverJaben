package com.example.driverjaben.entrypoint

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.driverjaben.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_enter_otp.*
import java.util.concurrent.TimeUnit

class EnterOTP : AppCompatActivity() {

    private lateinit var phoneNumber: String
    private lateinit var otpSentFromFirebase: String
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_otp)

        mAuth = FirebaseAuth.getInstance()

        supportActionBar!!.hide()

        phoneNumber = intent.getStringExtra("phoneNum")

        phone_tv.text = phoneNumber

        sendVerificationCode()

        btn_continue.setOnClickListener {
            verifyOTP()
        }

        did_not_get_code.setOnClickListener {
            sendVerificationCode()
        }
    }

    private fun verifyOTP() {
        var enteredOTP = enter_otp.text.toString()

        var credential = PhoneAuthProvider.getCredential(otpSentFromFirebase, enteredOTP);
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->

                    if (task.isSuccessful) {

                        val intent = Intent(this, IsRegistered::class.java)
                        intent.putExtra("phoneNum", phoneNumber)
                        startActivity(intent)
                        finish()

                    } else {

                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(applicationContext, "Incorrect Given Code", Toast.LENGTH_LONG).show()
                        }
                    }
                })
    }

    private fun sendVerificationCode() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+$phoneNumber",      // Phone number to verify
                60,               // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                this,             // Activity (for callback binding)
                callbacks)
    }

    private var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            //signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {

        }

        override fun onCodeSent(
                verificationId: String?,
                token: PhoneAuthProvider.ForceResendingToken
        ) {

            otpSentFromFirebase = verificationId.toString()
            //resendToken = token

        }
    }

}
