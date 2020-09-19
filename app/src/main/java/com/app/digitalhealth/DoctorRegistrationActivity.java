package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

//import com.google.android.gms.common.util.Strings;
import com.google.android.material.textfield.TextInputLayout;

public class DoctorRegistrationActivity extends AppCompatActivity {

    TextInputLayout textInputLayout;
    AutoCompleteTextView dropDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        textInputLayout = findViewById(R.id.sm_doctor_registration_specialization);
        dropDown = findViewById(R.id.sm_doctor_registration_specialization_value);

        String[] specialization = new String[]{
                "Anesthesiologist",
                "Cardiologist",
                "Gastroenterologist",
                "Dermatologist"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                DoctorRegistrationActivity.this,
                R.layout.drop_down_item,
                specialization
        );

        dropDown.setAdapter(adapter);

    }
}