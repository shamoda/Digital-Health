package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class BMICalculatorActivity extends AppCompatActivity {

    private String txtHeight=null;
    private String txtWeight=null;
    private float height;
    private float weight;
    private float bmi;
    private float temp;

    private TextInputEditText heightInput;
    private TextInputEditText weightInput;
    private Button calculate;
    private TextView BMIvalue;
    private TextView status;
    private TextView closeBtn;
    private TextView suggest;
    private TextView range;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_m_i_calculator);

//        getting reference from the xml layout
        heightInput = findViewById(R.id.sm_bmi_calculator_height_value);
        weightInput = findViewById(R.id.sm_bmi_calculator_weight_value);
        calculate = findViewById(R.id.sm_bmi_calculator_calculate_btn);
        BMIvalue = findViewById(R.id.sm_bmi_calculator_bmi_value);
        status = findViewById(R.id.sm_bmi_calculator_status);
        suggest = findViewById(R.id.sm_bmi_calculator_suggest_msg);
        range = findViewById(R.id.sm_bmi_calculator_range);
        closeBtn = findViewById(R.id.sm_bmi_calculator_close_btn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BMICalculatorActivity.this, QuickHealthCheckupsActivity.class));
                finish();
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

    private void validate() {

        txtHeight = heightInput.getText().toString();
        txtWeight = weightInput.getText().toString();

//        validating input
        if (TextUtils.isEmpty(txtHeight)){
            Toast.makeText(this, "Height cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(txtWeight)){
            Toast.makeText(this, "Weight cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else {
            temp = Float.parseFloat(txtHeight);
            weight = Float.parseFloat(txtWeight);

            bmi = calculateBMI(temp, weight);
            setResults(bmi);
        }

    }


    public float calculateBMI(float temp, float weight) {

//        validating inputs
        if (temp < 30.0 || temp > 250){
            Toast.makeText(this, "Height is not valid.", Toast.LENGTH_SHORT).show();
        }
        else if (weight < 1.0 || weight > 200.0){
            Toast.makeText(this, "Weight is not valid.", Toast.LENGTH_SHORT).show();
        }
        else {

//            calculation
            height = temp / 100;
            bmi = weight / (height * height);
        }
        return bmi;
    }

    private void setResults(float bmi) {
        BMIvalue.setText(String.valueOf(bmi));

//        displaying results according to the bmi value
        if (bmi < 18.5){
            status.setTextColor(Color.parseColor("#FF5C00"));
            status.setText("Underweight");

            BMIvalue.setVisibility(View.VISIBLE);
            status.setVisibility(View.VISIBLE);
            suggest.setVisibility(View.VISIBLE);
            range.setVisibility(View.VISIBLE);
        }
        if(bmi >= 18.5 && bmi <= 25){
            status.setTextColor(Color.parseColor("#00FF06"));
            status.setText("Healthy");

            BMIvalue.setVisibility(View.VISIBLE);
            status.setVisibility(View.VISIBLE);
            suggest.setVisibility(View.VISIBLE);
            range.setVisibility(View.VISIBLE);
        }
        if(bmi > 25){
            status.setTextColor(Color.parseColor("#FF000C"));
            status.setText("Overweight");

            BMIvalue.setVisibility(View.VISIBLE);
            status.setVisibility(View.VISIBLE);
            suggest.setVisibility(View.VISIBLE);
            range.setVisibility(View.VISIBLE);
        }
    }
}