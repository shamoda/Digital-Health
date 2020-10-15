package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.digitalhealth.Inflators.SearchAdapter;
import com.app.digitalhealth.model.Sessions;
import com.app.digitalhealth.model.SugarReport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SessionList extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Button createSessionBtn;
    DatabaseReference DataRef;
    SearchView searchView;
//    List SessionList;
    ListView listSessions;;
    private TextView closeBtn;


    public static ArrayList<Sessions> SessionList = new ArrayList<Sessions>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_list);

        listSessions = (ListView) findViewById(R.id.sessionList);
        SessionList = new ArrayList<>();
        createSessionBtn = (Button) findViewById(R.id.ak_session_list_btn);
        searchView = (SearchView) findViewById(R.id.searchDoctorName);
        searchView.setOnQueryTextListener(this);
        closeBtn= findViewById(R.id.ak_session_list_close_btn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SessionList.this, AdminDashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

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

                SearchAdapter adapter = new SearchAdapter(SessionList.this, SessionList);
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


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        SearchAdapter searchAdapter = new SearchAdapter(SessionList.this,SessionList);
        listSessions.setAdapter(searchAdapter);
        String text = s;
        searchAdapter.filter(text);

        if(s.isEmpty()){

            loadData();
        }
        return  false;
    }
}





