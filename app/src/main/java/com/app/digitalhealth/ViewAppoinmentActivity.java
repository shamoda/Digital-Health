package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ViewAppoinmentActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appoinment);


        String appId = getIntent().getStringExtra("appId");

        Toast.makeText(this, appId, Toast.LENGTH_LONG).show();

    }
}