package com.app.digitalhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.digitalhealth.prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddAppoinmentActivity extends AppCompatActivity {

    private TextInputEditText name, email, phone, note;
    private Button add;
//    int count;
    int temp;
    String appId;
    String updateId;
    private MaterialRadioButton male, female;
    private String gender, txtNote = "nothing";
    private TextView closeBtn;
    String dName, dDate, dSessionId;

    private DatabaseReference rootRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appoinment);

        rootRef = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        dName = intent.getStringExtra("name");
        dDate = intent.getStringExtra("date");
        dSessionId = intent.getStringExtra("id");


        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Appoinments");
        Query query = db.orderByKey().limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    temp = Integer.parseInt(child.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        name = findViewById(R.id.ar_add_appoinment_name);
        email = findViewById(R.id.ar_add_appoinment_email);
        phone = findViewById(R.id.ar_add_appoinment_phone);
        note = findViewById(R.id.ar_add_appoinment_note);
        add = findViewById(R.id.ar_add_appoinment_btn);
        male = findViewById(R.id.ar_male);
        female = findViewById(R.id.ar_female);
        closeBtn= findViewById(R.id.ar_add_appoinment_close_btn);


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (updateId != null){
                    startActivity(new Intent(AddAppoinmentActivity.this, MyAppoinmentsActivity.class));
                    finish();
                }
                else if (updateId == null){
                    startActivity(new Intent(AddAppoinmentActivity.this, HomeActivity.class));
                    finish();
                }
            }
        });


        updateId = getIntent().getStringExtra("appId");

        if (updateId != null){
            add.setText("Update");
            name.setEnabled(false);
            name.setClickable(false);
            name.setFocusable(false);
            male.setEnabled(false);
            female.setEnabled(false);
            retrieveData(email,phone,note,name,male,female);

        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    private void retrieveData(final TextInputEditText email, final TextInputEditText phone, final TextInputEditText note, final TextInputEditText name, final MaterialRadioButton male, final MaterialRadioButton female) {
        rootRef.child("Appoinments").child(updateId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("Name").getValue().toString());
                email.setText(dataSnapshot.child("Email").getValue().toString());
                note.setText(dataSnapshot.child("Note").getValue().toString());
                phone.setText(dataSnapshot.child("Phone").getValue().toString());
                if(dataSnapshot.child("Gender").getValue().toString().equals("female")){
                    female.setChecked(true);
                }
                if(dataSnapshot.child("Gender").getValue().toString().equals("male")){
                    male.setChecked(true);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void validateData() {

        if (male.isChecked()){
            gender = "male";
        }
        else if (female.isChecked()){
            gender = "female";
        }

        String txtName = name.getText().toString();
        String txtEmail = email.getText().toString();
        String txtPhone = phone.getText().toString();
        txtNote = note.getText().toString();

        if(TextUtils.isEmpty(txtName)){
            Toast.makeText(this, "Add your name to place an appointment", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(txtEmail)){
            Toast.makeText(this, "Add your email to place an appointment", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(txtPhone)){
            Toast.makeText(this, "Add your contact number to place an appointment", Toast.LENGTH_SHORT).show();
        }
        else if (!male.isChecked() && !female.isChecked()){
            Toast.makeText(this, "select your gender to place an appointment", Toast.LENGTH_SHORT).show();
        }
        else {
            sendData(txtName,txtEmail,txtPhone,txtNote, gender);
        }
    }

    private void sendData(String name, String email, String phone, String note, String gender) {

        appId = String.valueOf(temp+1);

        if(updateId == null){
            HashMap<String, Object> map = new HashMap<>();
            map.put("userId", Prevalent.currentUser.getPhone());
            map.put("id", appId);
            map.put("Name",name);
            map.put("Gender", gender);
            map.put("Email",email);
            map.put("Phone",phone);
            map.put("Note",note);
            map.put("session", dSessionId);
            map.put("doctor", dName);
            map.put("Date", dDate);

            rootRef.child("Appoinments").child(String.valueOf(appId)).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
//                        Toast.makeText(AddAppoinmentActivity.this, "successfully added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AddAppoinmentActivity.this, ViewAppoinmentActivity.class);
                        intent.putExtra("appId", String.valueOf(appId));
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
        else if (updateId != null){
            HashMap<String, Object> map = new HashMap<>();
            map.put("Email",email);
            map.put("Phone",phone);
            map.put("Note",note);

            rootRef.child("Appoinments").child(String.valueOf(updateId)).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(AddAppoinmentActivity.this, "successfully Updated", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AddAppoinmentActivity.this, MyAppoinmentsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK );
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }

    }

}