package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.app.digitalhealth.model.Appoinments;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AppoinmentHistoryActivity extends AppCompatActivity {

    ListView applist;
    DatabaseReference rootRef;
    List<Appoinments> listAppoinment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoinment_history);

        applist = findViewById(R.id.ar_appoinment_history_list);
        listAppoinment = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        loadData();
    }

    private void loadData() {
        rootRef= FirebaseDatabase.getInstance().getReference("Appoinments");
        rootRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listAppoinment.clear();
                for (DataSnapshot appoinmentSnapshot: dataSnapshot.getChildren()){
                    Appoinments appoinments = appoinmentSnapshot.getValue(Appoinments.class);
                    listAppoinment.add(appoinments);
                }

                AppoinmentAdapter adapter = new AppoinmentAdapter(AppoinmentHistoryActivity.this,listAppoinment);
                applist.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}


