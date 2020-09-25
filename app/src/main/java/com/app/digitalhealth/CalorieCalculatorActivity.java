package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;

public class CalorieCalculatorActivity extends AppCompatActivity {

    private Button CalculateBtn;

    private TextInputEditText age;
    private MaterialRadioButton male,female;
    private TextInputEditText height;
    private TextInputEditText weight;


    //activity drop down
    private TextView status;

    private String gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_calculator);

        age = findViewById(R.id.ak_calorie_calculator_age_value);
        height = findViewById(R.id.ak_calorie_calculator_height_value);
        weight = findViewById(R.id.ak_calorie_calculator_weight_value);
        male = findViewById(R.id.ak_male);
        female = findViewById(R.id.ak_female);
        status = findViewById(R.id.ak_calorie_calculator_status);
        CalculateBtn = findViewById(R.id.ak_calorie_calculator_calculate_btn);

        CalculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();

                Intent intent = new Intent(CalorieCalculatorActivity.this , QuickHealthCheckupsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void validate() {

    }
}