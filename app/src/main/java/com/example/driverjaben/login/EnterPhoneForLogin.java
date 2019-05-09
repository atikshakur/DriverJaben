package com.example.driverjaben.login;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.driverjaben.R;

public class EnterPhoneForLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_phone_for_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
