package com.app.digitalhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


//import com.app.digitalhealth.model.Sessions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddSessionActivity extends AppCompatActivity {

    TextInputLayout textInputLayout;
    AutoCompleteTextView dropDown;
    TextInputLayout textInputLayout2;
    AutoCompleteTextView nameDropdown;

    private AutoCompleteTextView InputName;
    private AutoCompleteTextView InputSpecialization;
    private TextInputEditText InputDate;
    private TextInputEditText InputPatients;
    private TextInputEditText InputTime;
    private Button AddSessionBtn;
    private Button DeleteSessionBtn;
    long maxID;
    String check;
    String updateId;

    List SessionList;
    ListView listSessions;
    DatabaseReference DataRef;

    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_session);

        DataRef = FirebaseDatabase.getInstance().getReference("Sessions");


        AddSessionBtn = (Button) findViewById(R.id.ak_add_session_btn);
        DeleteSessionBtn = (Button) findViewById(R.id.ak_session_cancel_btn);
        InputName = findViewById(R.id.ak_add_session_doctor_name_value);
        InputSpecialization = findViewById(R.id.ak_add_session_specialization_value);
        InputDate = findViewById(R.id.ak_add_session_date_value);
        InputPatients = findViewById(R.id.ak_add_session_no_of_patients_value);
        InputTime = findViewById(R.id.ak_add_session_time_value);
        listSessions = findViewById(R.id.sessionList);
        loadingBar = new ProgressDialog(this);
        SessionList = new ArrayList();

        Intent intent = getIntent();
         updateId = intent.getStringExtra("id");
         String name = intent.getStringExtra("name");
         String specialization = intent.getStringExtra("specialization");
         String date = intent.getStringExtra("date");
         String time = intent.getStringExtra("time");
         String noOfPatients = intent.getStringExtra("noOfPatients");
         check = intent.getStringExtra("check");

        Toast.makeText(this, updateId, Toast.LENGTH_SHORT).show();

        InputName.setText(name);
        InputSpecialization.setText(specialization);
        InputTime.setText(time);
        InputDate.setText(date);
        InputPatients.setText(noOfPatients);



        textInputLayout = findViewById(R.id.ak_add_session_specialization);
        dropDown = findViewById(R.id.ak_add_session_specialization_value);

        String[] specializationSelector = new String[]{
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
                AddSessionActivity.this,
                R.layout.drop_down_item,
                specializationSelector
        );
        dropDown.setAdapter(adapter);


        textInputLayout2 = findViewById(R.id.ak_add_session_doctor_name);
        nameDropdown = findViewById(R.id.ak_add_session_doctor_name_value);

        final ArrayAdapter<String> autocomplete = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);

        DatabaseReference nameRef = FirebaseDatabase.getInstance().getReference().child("Doctors");
        Query query1 = nameRef.orderByChild("name");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String dName = ds.child("name").getValue(String.class);

                    autocomplete.add(dName);
                    Toast.makeText(AddSessionActivity.this,  dName, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        nameDropdown.setAdapter(autocomplete);



        DeleteSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSession(updateId);
            }
        });



        if (updateId != null){
            AddSessionBtn.setText("Update");
            DeleteSessionBtn.setVisibility(View.VISIBLE);
        }

        if (updateId == null) {
            DeleteSessionBtn.setVisibility(View.INVISIBLE);
        }


        AddSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSession();
            }
        });

        DatabaseReference db =FirebaseDatabase.getInstance().getReference().child("Sessions");
        Query query = db.orderByKey().limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    maxID = Integer.parseInt(child.getKey()) ;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void deleteSession(String updateId) {
        DataRef.child(updateId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AddSessionActivity.this, "Session Deleted Successfully.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddSessionActivity.this, SessionList.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddSessionActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void AddSession() {
        String name= InputName.getText().toString().trim();
        String specialization = InputSpecialization.getText().toString().trim();
        String date = InputDate.getText().toString().trim();
        String patients = InputPatients.getText().toString().trim();
        String time = InputTime.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Name cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(specialization)){
            Toast.makeText(this, "Specialization cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(date)){
            Toast.makeText(this, "Date cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(patients)){
            Toast.makeText(this, "Number of patients cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(time)){
            Toast.makeText(this, "Time cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Add Session");
            loadingBar.setMessage("Adding new session");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            insertData(name, specialization, date, patients, time);
        }

    }

    private void insertData(final String name, final String specialization, final String date, final String patients, final String time) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        if(check == null){
            RootRef.child("Sessions").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

//                long count =  dataSnapshot.getChildrenCount();
                    String  id = String.valueOf( maxID+1);


                    HashMap<String, Object> dataMap = new HashMap<>();
                    dataMap.put("id", id);
                    dataMap.put("name", name);
                    dataMap.put("specialization", specialization);
                    dataMap.put("date", date);
                    dataMap.put("noOfPatients", patients);
                    dataMap.put("time", time);

                    //check the child
                    RootRef.child("Sessions").child(String.valueOf(id)).updateChildren(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AddSessionActivity.this, "Session successfully added.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(AddSessionActivity.this , SessionList.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(AddSessionActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        else if(check != null){
            RootRef.child("Sessions").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

//                long count =  dataSnapshot.getChildrenCount();
//                    String  id = String.valueOf( maxID+1);


                    HashMap<String, Object> dataMap = new HashMap<>();
                    dataMap.put("name", name);
                    dataMap.put("specialization", specialization);
                    dataMap.put("date", date);
                    dataMap.put("noOfPatients", patients);
                    dataMap.put("time", time);

                    //check the child
                    RootRef.child("Sessions").child(updateId).updateChildren(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AddSessionActivity.this, "Session successfully updated.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(AddSessionActivity.this , SessionList.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(AddSessionActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }





    }

}