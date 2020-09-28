package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.digitalhealth.model.Sessions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectSessionActivity extends AppCompatActivity {

    String dName;
    String dSpecialization;
    String dPhone;
    String dImage;
    ListView sessionList;
    List<Sessions> arrayList;
    private TextView closeBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_session);

        Intent intent = getIntent();
        dName = intent.getStringExtra("name");
        dSpecialization = intent.getStringExtra("specialization");
        dPhone = intent.getStringExtra("phone");
        dImage = intent.getStringExtra("image");

        sessionList = findViewById(R.id.ak_search_session_list);
        arrayList = new ArrayList<>();
        closeBtn= findViewById(R.id.ak_select_session_close_btn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectSessionActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        DatabaseReference sessionRef = FirebaseDatabase.getInstance().getReference().child("Sessions");
        Query query = sessionRef.orderByChild("name").equalTo(dName);


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    arrayList.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Sessions sessions = ds.getValue(Sessions.class);
                    arrayList.add(sessions);
                    //getting all the sessions with regards to a specific doctor
                    Toast.makeText(SelectSessionActivity.this, sessions.getDate(), Toast.LENGTH_SHORT).show();
                }
                SessionAdapter sessionAdapter = new SessionAdapter(SelectSessionActivity.this,arrayList);
                sessionList.setAdapter(sessionAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sessionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Sessions sessions = (Sessions) arrayList.get(i);

                Toast.makeText(SelectSessionActivity.this, sessions.getSession_Id(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SelectSessionActivity.this, AddAppoinmentActivity.class);
                intent.putExtra("id", sessions.getSession_Id());
                intent.putExtra("name", sessions.getName());
                intent.putExtra("specialization", sessions.getSpecialization());
                intent.putExtra("date",sessions.getDate());
                intent.putExtra("time",sessions.getTime());
                intent.putExtra("noOfPatients",sessions.getNoOfPatients());
                intent.putExtra("check", "check");
                startActivity(intent);
            }
        });

    }
}