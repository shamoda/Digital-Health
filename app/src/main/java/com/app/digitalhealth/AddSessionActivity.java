package com.app.digitalhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.app.digitalhealth.model.Sessions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddSessionActivity extends AppCompatActivity {

    private TextInputEditText InputName;
    private TextInputEditText InputSpecialization;
    private TextInputEditText InputDate;
    private TextInputEditText InputPatients;
    private TextInputEditText InputTime;
    private Button AddSessionBtn;
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
        InputName = findViewById(R.id.ak_add_session_doctor_name_value);
        InputSpecialization = findViewById(R.id.ak_add_session_specialization_value);
        InputDate = findViewById(R.id.ak_add_session_date_value);
        InputPatients = findViewById(R.id.ak_add_session_no_of_patients_value);
        InputTime = findViewById(R.id.ak_add_session_time_value);
        listSessions = findViewById(R.id.sessionList);
        loadingBar = new ProgressDialog(this);
        SessionList = new ArrayList();

        AddSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSession();
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

        RootRef.child("Sessions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long count =  dataSnapshot.getChildrenCount();
                long id = count+1;

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

//                            Intent intent = new Intent(AddSessionActivity.this , .class);
//                            startActivity(intent);
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