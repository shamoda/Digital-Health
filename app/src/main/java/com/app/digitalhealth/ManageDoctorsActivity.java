package com.app.digitalhealth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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


//    //Dropdown
//    Spinner specializationSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_doctors);

        textInputLayout = findViewById(R.id.sm_manage_doctors_specialization);
        dropDown = findViewById(R.id.sm_manage_doctors_specialization_value);

        String[] specialization = new String[]{
                "Anesthesiologist",
                "Cardiologist",
                "Gastroenterologist",
                "Dermatologist"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                ManageDoctorsActivity.this,
                R.layout.drop_down_item,
                specialization
        );

        dropDown.setAdapter(adapter);

//        //Dropdown related code
//        specializationSpinner = findViewById(R.id.sm_manage_doctors_specialization_spinner);
//
//        List<String> specializations = new ArrayList<>();
//        specializations.add("All");
//        specializations.add("Anesthesiologist");
//        specializations.add("Cardiologist");
//        specializations.add("Gastroenterologist");
//        specializations.add("Dermatologist");
//
//        ArrayAdapter<String> specializationAdaptor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, specializations);
//        specializationAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        specializationSpinner.setAdapter(specializationAdaptor);
//
//        specializationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String selectedSpecialization = specializationSpinner.getSelectedItem().toString();
//                if(!selectedSpecialization.equals("All")){
//                    //another if condition
//                }else{
//                    //retrieve all doctors
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

    }
}
