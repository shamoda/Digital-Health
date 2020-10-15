package com.app.digitalhealth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.digitalhealth.model.Doctor;
import com.app.digitalhealth.viewholder.DoctorDetailsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ManageDoctorsActivity extends AppCompatActivity {

    TextInputLayout textInputLayout;
    AutoCompleteTextView dropDown;

    private Button addNewDoctor;
    private TextView closeBtn;
    private AutoCompleteTextView specializationSelector;
    private SearchView searchDoctor;

    private DatabaseReference doctorRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_doctors);

//        getting refferences
        doctorRef = FirebaseDatabase.getInstance().getReference().child("Doctors");

        textInputLayout = findViewById(R.id.sm_manage_doctors_specialization);
        dropDown = findViewById(R.id.sm_manage_doctors_specialization_value);

        addNewDoctor = findViewById(R.id.sm_manage_doctors_add_new_doctor);
        closeBtn = findViewById(R.id.sm_manage_doctors_close_btn);
        specializationSelector = findViewById(R.id.sm_manage_doctors_specialization_value);
        searchDoctor = findViewById(R.id.sm_manage_doctors_search_view);
        context = this;

        String[] specialization = new String[]{
                "All",
                "Allergists",
                "Anesthesiologist",
                "Cardiologist",
                "Colon and Rectal Surgeon",
                "Dermatologist",
                "Endocrinologist",
                "Family Physician",
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

        searchDoctor.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchText(s);
                return false;
            }
        });


        specializationSelector.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterDoctor(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


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



    private void searchText(String s) {
        FirebaseRecyclerOptions<Doctor> options;

        if (s == null){
            options = new FirebaseRecyclerOptions.Builder<Doctor>().setQuery(doctorRef, Doctor.class).build();
        }
        else {
            Query firebaseSearchQuery = doctorRef.orderByChild("name").startAt(s).endAt(s + "\uf8ff");
            options = new FirebaseRecyclerOptions.Builder<Doctor>().setQuery(firebaseSearchQuery, Doctor.class).setLifecycleOwner(this).build();
        }


        FirebaseRecyclerAdapter<Doctor, DoctorDetailsViewHolder> adapter = new FirebaseRecyclerAdapter<Doctor, DoctorDetailsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DoctorDetailsViewHolder doctorDetailsViewHolder, int i, @NonNull final Doctor doctor) {
                doctorDetailsViewHolder.specialization.setText(doctor.getSpecialization());
                doctorDetailsViewHolder.name.setText("Dr. " + doctor.getName());
                Picasso.get().load(doctor.getImage()).into(doctorDetailsViewHolder.image);

                doctorDetailsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showAlertDialog(doctor.getPhone(), doctor.getName(), doctor.getImage());
                    }
                });
            }

            @NonNull
            @Override
            public DoctorDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_details_row, parent, false);
                DoctorDetailsViewHolder holder = new DoctorDetailsViewHolder(view);
                return holder;
            }
        };

//        setting adaptor to the recyclerview
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }




    private void filterDoctor(String s) {

        FirebaseRecyclerOptions<Doctor> options;

        if (s.equals("All")){
             options = new FirebaseRecyclerOptions.Builder<Doctor>().setQuery(doctorRef, Doctor.class).build();
        }
        else {
            Query firebaseSearchQuery = doctorRef.orderByChild("specialization").startAt(s).endAt(s + "\uf8ff");
            options = new FirebaseRecyclerOptions.Builder<Doctor>().setQuery(firebaseSearchQuery, Doctor.class).setLifecycleOwner(this).build();
        }


        FirebaseRecyclerAdapter<Doctor, DoctorDetailsViewHolder> adapter = new FirebaseRecyclerAdapter<Doctor, DoctorDetailsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DoctorDetailsViewHolder doctorDetailsViewHolder, int i, @NonNull final Doctor doctor) {
                doctorDetailsViewHolder.specialization.setText(doctor.getSpecialization());
                doctorDetailsViewHolder.name.setText("Dr. " + doctor.getName());
                Picasso.get().load(doctor.getImage()).into(doctorDetailsViewHolder.image);

                doctorDetailsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showAlertDialog(doctor.getPhone(), doctor.getName(), doctor.getImage());
                    }
                });
            }

            @NonNull
            @Override
            public DoctorDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_details_row, parent, false);
                DoctorDetailsViewHolder holder = new DoctorDetailsViewHolder(view);
                return holder;
            }
        };

//        setting adaptor to the recyclerview
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Doctor> options = new FirebaseRecyclerOptions.Builder<Doctor>().setQuery(doctorRef, Doctor.class).build();

        FirebaseRecyclerAdapter<Doctor, DoctorDetailsViewHolder> adapter = new FirebaseRecyclerAdapter<Doctor, DoctorDetailsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DoctorDetailsViewHolder doctorDetailsViewHolder, int i, @NonNull final Doctor doctor) {
                doctorDetailsViewHolder.specialization.setText(doctor.getSpecialization());
                doctorDetailsViewHolder.name.setText("Dr. " + doctor.getName());
                Picasso.get().load(doctor.getImage()).into(doctorDetailsViewHolder.image);

                doctorDetailsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showAlertDialog(doctor.getPhone(), doctor.getName(), doctor.getImage());
                    }
                });
            }

            @NonNull
            @Override
            public DoctorDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_details_row, parent, false);
                DoctorDetailsViewHolder holder = new DoctorDetailsViewHolder(view);
                return holder;
            }
        };

//        setting adaptor to the recyclerview
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    private void showAlertDialog(final String phone, final String name, final String image) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Dr."+name);
        builder.setMessage("What you want to do with Dr."+name);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                final ProgressDialog pd = new ProgressDialog(context);
                pd.setMessage("Deleting...");
                pd.show();

                doctorRef.child(phone).removeValue();
                StorageReference proPicRef = FirebaseStorage.getInstance().getReferenceFromUrl(image);
                proPicRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.dismiss();
                        Toast.makeText(context, "Dr."+name+" related records are deleted successfully.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNeutralButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ManageDoctorsActivity.this, DoctorRegistrationActivity.class);
                intent.putExtra("dId", phone);
                startActivity(intent);
            }
        });
        builder.show();
    }


}
