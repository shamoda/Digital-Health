package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.AutoText;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CalorieCalculatorActivity extends AppCompatActivity {

    private Button CalculateBtn;
    TextInputLayout textInputLayout;
    AutoCompleteTextView dropDown;

    private TextInputEditText age;
    private MaterialRadioButton male,female;
    private TextInputEditText height;
    private TextInputEditText weight;

    private TextView status;
    private TextView msg;
    private TextView result;
    private String gender;
    private String activity;


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
        msg = findViewById(R.id.ak_calorie_calculator_suggest_msg);
        result = findViewById(R.id.ak_calorie_calculator_status);
        CalculateBtn = findViewById(R.id.ak_calorie_calculator_calculate_btn);

        textInputLayout = findViewById(R.id.ak_calorie_calculator_activity);
        dropDown = findViewById(R.id.ak_calorie_calculator_activity_value);

        String[] data = new String[]{
                "little or no exercise",
                "light exercise (sports 1-3 days/week)",
                "moderate exercise (sports 3-5 days/week)",
                "very active (athletic)",
                "extra active (sports 6-7 days/week)"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                CalorieCalculatorActivity.this,
                R.layout.drop_down_item,
                data
        );

        dropDown.setAdapter(adapter);



        CalculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCalories();

            }
        });

    }

    public static float calcCalories(float tage, float tweight, float theight,String tgen, String tactivity) {
        float calories = 0;
        if (tgen.equals("female")) {
            if (tactivity.equals("little or no exercise")) {
                 calories = (float) (((10.0 * tweight) + (6.25 * theight) - (5.0 * tage) - 161.0) * 1.2);
               
            } else if (tactivity.equals("light exercise (sports 1-3 days/week)")) {
                 calories = (float) (((10.0 * tweight) + (6.25 * theight) - (5.0 * tage) - 161.0) * 1.375);
                
            } else if (tactivity.equals("moderate exercise (sports 3-5 days/week)")) {
                calories = (float) (((10.0 * tweight) + (6.25 * theight) - (5.0 * tage) - 161.0) * 1.55);
                
            } else if (tactivity.equals("very active (athletic)")) {
                 calories = (float) (((10.0 * tweight) + (6.25 * theight) - (5.0 * tage) - 161.0) * 1.725);
              
            } else if (tactivity.equals("extra active (sports 6-7 days/week)")) {
                calories = (float) (((10.0 * tweight) + (6.25 * theight) - (5.0 * tage) - 161.0) * 1.9);
                
            }

        } else {
            if (tactivity.equals("little or no exercise")) {
                calories = (float) (((10.0 * tweight) + (6.25 * theight) - (5.0 * tage) + 5.0) * 1.2);
                
            } else if (tactivity.equals("light exercise (sports 1-3 days/week)")) {
                calories = (float) (((10.0 * tweight) + (6.25 * theight) - (5.0 * tage) + 5.0) * 1.375);
                
            } else if (tactivity.equals("moderate exercise (sports 3-5 days/week)")) {
                calories = (float) (((10.0 * tweight) + (6.25 * theight) - (5.0 * tage) + 5.0) * 1.55);
                
            } else if (tactivity.equals("very active (athletic)")) {
                calories = (float) (((10.0 * tweight) + (6.25 * theight) - (5.0 * tage) + 5.0) * 1.725);
                
            } else if (tactivity.equals("extra active (sports 6-7 days/week)")) {
                calories = (float) (((10.0 * tweight) + (6.25 * theight) - (5.0 * tage) + 5.0) * 1.9);


            }

        }
        return calories;
    }

    private void setCalories() {
        String txtAge = age.getText().toString();
        String txtHeight = height.getText().toString();
        String txtWeight = weight.getText().toString();
        activity = dropDown.getText().toString();

        //activity
        if (male.isChecked()) {
            gender = "male";
        } else if (female.isChecked()) {
            gender = "female";
        }

        if (TextUtils.isEmpty(txtAge)) {
            Toast.makeText(this, "Some Fields are empty", Toast.LENGTH_SHORT).show();
        }else if( Float.parseFloat(txtAge)<15 || Float.parseFloat(txtAge)>80){
            Toast.makeText(this, "Please Enter value between 15 to 80", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(txtHeight)) {
            Toast.makeText(this, "Some Fields are empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(txtWeight)) {
            Toast.makeText(this, "Some Fields are empty", Toast.LENGTH_SHORT).show();
        } else if (!male.isChecked() && !female.isChecked()) {
            Toast.makeText(this, "Gender Error", Toast.LENGTH_SHORT).show();
        } else {
            float ageValue = Float.parseFloat(txtAge);
            float heightValue = Float.parseFloat(txtHeight);
            float weightValue = Float.parseFloat(txtWeight);


                float answer = calcCalories(ageValue,weightValue,heightValue,gender,activity);
                status.setText(String.valueOf(answer));
            }
            msg.setVisibility(View.VISIBLE);
            result.setVisibility(View.VISIBLE);
        }
    }

