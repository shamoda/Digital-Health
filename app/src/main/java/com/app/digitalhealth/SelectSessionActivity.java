package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.app.digitalhealth.model.Sessions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SelectSessionActivity extends AppCompatActivity {

    String dName;
    String dSpecialization;
    String dPhone;
    String dImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_session);

        Intent intent = getIntent();
        dName = intent.getStringExtra("name");
        dSpecialization = intent.getStringExtra("specialization");
        dPhone = intent.getStringExtra("phone");
        dImage = intent.getStringExtra("image");

        DatabaseReference sessionRef = FirebaseDatabase.getInstance().getReference().child("Sessions");
        Query query = sessionRef.orderByChild("name").equalTo(dName);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Sessions sessions = ds.getValue(Sessions.class);
                    //getting all the sessions with regards to a specific doctor
                    Toast.makeText(SelectSessionActivity.this, sessions.getDate(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}