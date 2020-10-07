package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;

public class BMRcalculatorActivity extends AppCompatActivity {

    private TextInputEditText age , height, weight;
    private Button calcbtn;
    private MaterialRadioButton male, female;
    private String gender;
    private TextView result, closeBtn, clearBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_m_rcalculator);

        age = findViewById(R.id.ar_bmr_age);
        weight = findViewById(R.id.ar_bmr_calculator_weight_value);
        height = findViewById(R.id.ar_bmr_calculator_height_value);
        male = findViewById(R.id.ar_bmr_male);
        female = findViewById(R.id.ar_bmr_female);
        calcbtn = findViewById(R.id.ar_bmr_calculate);
        closeBtn = findViewById(R.id.ar_bmr_calculator_close_btn);
        result = findViewById(R.id.ar_bmr_tresults);
        clearBtn = findViewById(R.id.ar_bmr_clear);

        calcbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBMR();
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               age.setText("");
               weight.setText("");
               height.setText("");
               result.setText("");

               if(male.isChecked()){
                   male.setChecked(false);
               }

               else if(female.isChecked()){
                   female.setChecked(false);
               }

            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BMRcalculatorActivity.this,QuickHealthCheckupsActivity.class);
                startActivity(intent);
            }
        });
    }

    public static float calculateBMR(float tweight, float theight, float tage ,String tgender){
        if(tgender.equals("female")) {
            float BMR = (float) ((10.0 * tweight) + (6.25 * theight) - (5.0 * tage) - 161.0);
            return BMR;
        } else {
            float BMR = (float) ((10.0 * tweight) + (6.25 * theight) - (5.0 * tage) + 5.0);
            return BMR;
        }

    }

    private void setBMR() {
        String txtage = age.getText().toString();
        String txtheight = height.getText().toString();
        String txtweight = weight.getText().toString();
        if (male.isChecked()){
            gender = "male";
        }
        else if (female.isChecked()){
            gender = "female";
        }

        if(TextUtils.isEmpty(txtage)){
            Toast.makeText(this, "Some Fields are empty", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(txtheight)){
            Toast.makeText(this, "Some Fields are empty", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(txtweight)){
            Toast.makeText(this, "Some Fields are empty", Toast.LENGTH_SHORT).show();
        }
        else if (!male.isChecked() && !female.isChecked()){
            Toast.makeText(this, "Gender Error", Toast.LENGTH_SHORT).show();
        }
        else {
            float ageValue = Float.parseFloat(txtage);
            float heightValue = Float.parseFloat(txtheight);
            float weightValue = Float.parseFloat(txtweight);


                float bmr = calculateBMR(weightValue,heightValue,ageValue,gender);
                result.setText(String.valueOf(bmr));


        }
    }


}