package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;

public class BodyFatCal extends AppCompatActivity {

    private TextInputEditText age;
    private TextInputEditText BMI;
    private Button calculate;
    private String gender ="male";
    private MaterialRadioButton male,female;
    private double Age;
    private double Bmi;
    private  String txtAge= null;
    private String txtBMI = null;
    private TextView BFCvalue;
    private TextView status;
    private CardView card;
    private  TextView close;
    double BFP = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_fat_cal);


        age = (TextInputEditText) findViewById(R.id.ageText);
        BMI = (TextInputEditText) findViewById(R.id.BMI);
        calculate = (Button) findViewById(R.id.calFat);
        male = findViewById(R.id.ak_male);
        female = findViewById(R.id.ak_female);
        BFCvalue = findViewById(R.id.percent);
        status = findViewById(R.id.status);
        card = findViewById(R.id.card);
        close = findViewById(R.id.closebtn);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BodyFatCal.this, QuickHealthCheckupsActivity.class));
                finish();
            }
        });


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
                setResults( BFP );
            }
        });
    }
    private void validate() {

         txtAge = age.getText().toString();
         txtBMI = BMI.getText().toString();


        if (male.isChecked()) {
            gender = "male";
        } else if (female.isChecked()) {
            gender = "female";
        }

        if (TextUtils.isEmpty(txtAge)){
            Toast.makeText(this, "Age cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(txtBMI)){
            Toast.makeText(this, "BMI cannot be empty.", Toast.LENGTH_SHORT).show();
        }else if( !male.isChecked() && !female.isChecked()){

            Toast.makeText(this, "Gender must be checked", Toast.LENGTH_SHORT).show();
        }
        else {
            Age = Double.parseDouble(txtAge);
            Bmi = Double.parseDouble(txtBMI);

            calculateFAT(Age,Bmi,gender);
        }

    }


    public double calculateFAT(double Age, double Bmi,String gender) {


        if (Age < 19 && gender.equals("male")) {

            BFP = (Double) (1.51 * Bmi - 0.70 * Age - 2.2);

        }else if(Age < 19 && gender.equals("female")){

              BFP = (Double) ((1.51 * Bmi) - 0.70 * Age + 1.4);

        }
        else if (Age > 19 && gender.equals("male")){

            BFP = (Double) ((1.20 * Bmi) + 0.23 * Age - 16.2);

        }else if(Age > 19 && gender.equals("female")){

            BFP = (Double) ((1.51 * Bmi) - 0.70 * Age + 1.4);


        }
        else {
            Toast.makeText(this, "Please check the inputs and enter again", Toast.LENGTH_SHORT).show();
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        Double twoDigitsF = Double.valueOf(decimalFormat.format(BFP));
        return twoDigitsF;
    }
//    Classification	Women (% Fat)	Men (% Fat)
//    Essential Fat	10-12%	2-4%
//    Athletes	14-20%	6-13%
//    Fitness	21-24%	14-17%
//    Acceptable	25-31%	18-25%
//    Obese	32% +	25% +

    public void setResults(double bfc) {

        bfc = BFP;

        String formattedValue = String.format("%.2f",bfc);

        BFCvalue.setText((formattedValue+"%"));
        if(gender.equals("male")){

            if(bfc < 10){

                status.setTextColor(Color.parseColor("#B22222"));
                status.setText("Renter the values again");
                BFCvalue.setVisibility(View.INVISIBLE);
                status.setVisibility(View.VISIBLE);
            }

             else if (bfc >= 10 && bfc < 13 ){
                card.setCardBackgroundColor(Color.parseColor("#7FFFD4"));
                status.setTextColor(Color.parseColor("#008000"));
                status.setText("Your fat level is essential");
                BFCvalue.setVisibility(View.VISIBLE);
                status.setVisibility(View.VISIBLE);

              } else if (bfc >= 13 && bfc < 21 ){
                card.setCardBackgroundColor(Color.parseColor("#7FFFD4"));
                status.setTextColor(Color.parseColor("#00008B"));
                status.setText("Your fat level is Athletic");

                BFCvalue.setVisibility(View.VISIBLE);
                status.setVisibility(View.VISIBLE);

            } else if(bfc >= 21 && bfc < 25 ){
                card.setCardBackgroundColor(Color.parseColor("#7FFFD4"));
                status.setTextColor(Color.parseColor("#008000"));
                status.setText("Your fat level is Athletic");

                BFCvalue.setVisibility(View.VISIBLE);
                status.setVisibility(View.VISIBLE);

        } else if (bfc >= 25 && bfc < 32){
                 card.setCardBackgroundColor(Color.parseColor("#7FFFD4"));
                status.setTextColor(Color.parseColor("#008000"));
                status.setText("Your fat level is Acceptable");

               BFCvalue.setVisibility(View.VISIBLE);
               status.setVisibility(View.VISIBLE);

        }

        else if(bfc >= 32  && bfc< 100 ){
            card.setCardBackgroundColor(Color.parseColor("#F08080"));
            status.setTextColor(Color.parseColor("#FF000C"));
            status.setText("Your fat level is Obese");

            BFCvalue.setVisibility(View.VISIBLE);
            status.setVisibility(View.VISIBLE);

        }else{

            status.setTextColor(Color.parseColor("#B22222"));
            status.setText("Enter correct values");
            BFCvalue.setVisibility(View.INVISIBLE);

            status.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Enter correct values", Toast.LENGTH_SHORT).show();
        }
    }

        if(gender.equals("female")){

            if(bfc < 2){

                status.setTextColor(Color.parseColor("#B22222"));
                status.setText("Renter the values");
                BFCvalue.setVisibility(View.INVISIBLE);
                status.setVisibility(View.VISIBLE);
            } else if (bfc >= 2 && bfc < 5 ){
                card.setCardBackgroundColor(Color.parseColor("#7FFFD4"));
                status.setTextColor(Color.parseColor("#008000"));
                status.setText("Your fat level is essential");
                BFCvalue.setVisibility(View.VISIBLE);
                status.setVisibility(View.VISIBLE);

            }
           else if (bfc >= 5 && bfc < 14){
                card.setCardBackgroundColor(Color.parseColor("#7FFFD4"));
                status.setTextColor(Color.parseColor("#008000"));
                status.setText("Your fat level is Athletic");

                BFCvalue.setVisibility(View.VISIBLE);
                status.setVisibility(View.VISIBLE);

            }
            else if (bfc >= 14 && bfc < 18 ){
                card.setCardBackgroundColor(Color.parseColor("#7FFFD4"));
                status.setTextColor(Color.parseColor("#008000"));
                status.setText("Your fat level is Fitness");

                BFCvalue.setVisibility(View.VISIBLE);
                status.setVisibility(View.VISIBLE);

            }
           else if (bfc >= 18 && bfc < 25){
                card.setCardBackgroundColor(Color.parseColor("#7FFFD4"));
                status.setTextColor(Color.parseColor("#008000"));
                status.setText("Your fat level is Acceptable");

                BFCvalue.setVisibility(View.VISIBLE);
                status.setVisibility(View.VISIBLE);

            }

           else if(bfc > 25 && bfc< 100){
                card.setCardBackgroundColor(Color.parseColor("#F08080"));
                status.setTextColor(Color.parseColor("#FF000C"));
                status.setText("Your fat level is Obese");

                BFCvalue.setVisibility(View.VISIBLE);
                status.setVisibility(View.VISIBLE);

            }else{
                status.setTextColor(Color.parseColor("#B22222"));
                status.setText("Enter correct values");
                BFCvalue.setVisibility(View.INVISIBLE);

                status.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Enter correct values", Toast.LENGTH_SHORT).show();

            }



        }
    }


}