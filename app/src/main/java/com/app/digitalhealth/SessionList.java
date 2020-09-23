package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.app.digitalhealth.model.Sessions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SessionList extends AppCompatActivity {

    DatabaseReference DataRef;
    List SessionList;
    ListView listSessions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_list);

        listSessions = (ListView) findViewById(R.id.sessionList);
        SessionList = new ArrayList<>();

        DataRef = FirebaseDatabase.getInstance().getReference("Sessions");
    }


    @Override
    protected void onStart() {
        super.onStart();

        DataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SessionList.clear();
                for (DataSnapshot sessionsnap : dataSnapshot.getChildren()) {

                    Sessions sessions = sessionsnap.getValue(Sessions.class);
                    SessionList.add(sessions);

            }

                SessionAdapter adapter = new SessionAdapter(SessionList.this, SessionList);
                listSessions.setAdapter(adapter);
        }

            @Override
            public void onCancelled(DatabaseError databaseError) {

           }
       });
   }
}




