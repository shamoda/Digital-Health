package com.app.digitalhealth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.app.digitalhealth.viewholder.DoctorDetailsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ManageDoctorsActivity extends AppCompatActivity {

    TextInputLayout textInputLayout;
    AutoCompleteTextView dropDown;

    private Button addNewDoctor;
    private TextView closeBtn;

    private DatabaseReference doctorRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_doctors);

        doctorRef = FirebaseDatabase.getInstance().getReference().child("Doctors");

        textInputLayout = findViewById(R.id.sm_manage_doctors_specialization);
        dropDown = findViewById(R.id.sm_manage_doctors_specialization_value);

        addNewDoctor = findViewById(R.id.sm_manage_doctors_add_new_doctor);
        closeBtn = findViewById(R.id.sm_manage_doctors_close_btn);

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

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageDoctorsActivity.this, AdminDashboard.class));
                finish();
            }
        });


        recyclerView = findViewById(R.id.sm_manage_doctors_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Doctor> options = new FirebaseRecyclerOptions.Builder<Doctor>().setQuery(doctorRef, Doctor.class).build();

        FirebaseRecyclerAdapter<Doctor, DoctorDetailsViewHolder> adapter = new FirebaseRecyclerAdapter<Doctor, DoctorDetailsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DoctorDetailsViewHolder doctorDetailsViewHolder, int i, @NonNull Doctor doctor) {
                doctorDetailsViewHolder.specialization.setText(doctor.getSpecialization());
                doctorDetailsViewHolder.name.setText("Dr. " + doctor.getName());
                Picasso.get().load(doctor.getImage()).into(doctorDetailsViewHolder.image);
            }

            @NonNull
            @Override
            public DoctorDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_details_row, parent, false);
                DoctorDetailsViewHolder holder = new DoctorDetailsViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }


}
