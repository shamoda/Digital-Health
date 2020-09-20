package com.app.digitalhealth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.digitalhealth.model.Doctor;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class ManageDoctorsActivity extends AppCompatActivity {

    TextInputLayout textInputLayout;
    AutoCompleteTextView dropDown;

    private Button addNewDoctor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_doctors);

        textInputLayout = findViewById(R.id.sm_manage_doctors_specialization);
        dropDown = findViewById(R.id.sm_manage_doctors_specialization_value);

        addNewDoctor = findViewById(R.id.sm_manage_doctors_add_new_doctor);

        String[] specialization = new String[]{
                "Allergists",
                "Anesthesiologist",
                "Cardiologist",
                "Colon and Rectal Surgeon",
                "Dermatologist",
                "Endocrinologist",
                "Gastroenterologist",
                "Hematologist",
                "Infectious Disease",
                "Internist",
                "Nephrologist",
                "Neurologist",
                "Oncologist",
                "Pathologist",
                "Psychiatrist",
                "Radiologist",
                "Rheumatologist",
                "Sports Medicine",
                "Urologist"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                ManageDoctorsActivity.this,
                R.layout.drop_down_item,
                specialization
        );

        dropDown.setAdapter(adapter);


        addNewDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageDoctorsActivity.this, DoctorRegistrationActivity.class));
            }
        });



    }
}
