package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.app.digitalhealth.model.Sessions;
import com.app.digitalhealth.model.SugarReport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SessionList extends AppCompatActivity {

    private Button createSessionBtn;
    DatabaseReference DataRef;
    List SessionList;
    ListView listSessions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_list);

        listSessions = (ListView) findViewById(R.id.sessionList);
        SessionList = new ArrayList<>();
        createSessionBtn = (Button) findViewById(R.id.ak_session_list_btn);

        createSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SessionList.this, AddSessionActivity.class);
                startActivity(intent);
            }
        });

        DataRef = FirebaseDatabase.getInstance().getReference("Sessions");


    }


    @Override
    protected void onStart() {
        super.onStart();

        loadData();
    }

    private void loadData() {

        DataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SessionList.clear();
                for (DataSnapshot sessionsnap : dataSnapshot.getChildren()) {

                    Sessions sessions = sessionsnap.getValue(Sessions.class);
                    sessions.setSession_Id(sessionsnap.getKey());
                    SessionList.add(sessions);

                }

                SessionAdapter adapter = new SessionAdapter(SessionList.this, SessionList);
                listSessions.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listSessions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Sessions sessions = (Sessions) SessionList.get(i);

                Toast.makeText(SessionList.this, sessions.getSession_Id(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), AddSessionActivity.class);
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





