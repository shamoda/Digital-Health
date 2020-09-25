package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuickHealthCheckupsActivity extends AppCompatActivity {

    private Button BMI;
    private Button BMR;
    private Button calorie;
    private Button bodyFat;
    private TextView closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_health_checkups);

        BMI = findViewById(R.id.sm_quick_health_checkups_bmi);
        BMR = findViewById(R.id.sm_quick_health_checkups_bmr);
        calorie = findViewById(R.id.sm_quick_health_checkups_calorie);
        bodyFat = findViewById(R.id.sm_quick_health_checkups_body_fat);
        closeBtn = findViewById(R.id.sm_quick_health_checkups_close_btn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuickHealthCheckupsActivity.this, HomeActivity.class));
                finish();
            }
        });

        BMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuickHealthCheckupsActivity.this, BMICalculatorActivity.class));
            }
        });

        BMR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuickHealthCheckupsActivity.this, BMRcalculatorActivity.class));
            }
        });
        calorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuickHealthCheckupsActivity.this, CalorieCalculatorActivity.class));
            }
        });
        bodyFat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuickHealthCheckupsActivity.this, BodyFatCal.class));
            }
        });

    }
}