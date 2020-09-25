package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewAppoinmentActivity extends AppCompatActivity {

    private TextView id, name, doctor, session, date, phone;
    private Button updateBtn,myAppoinmentsBtn;
    private DatabaseReference rootRef;
    String appId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appoinment);

        rootRef = FirebaseDatabase.getInstance().getReference().child("Appoinments");

        id = (TextView) findViewById(R.id.ar_view_appoinment_vid);
        name = (TextView) findViewById(R.id.ar_view_appoinment_vname);
        doctor = (TextView) findViewById(R.id.ar_view_appoinment_vdrname);
        date = (TextView) findViewById(R.id.ar_view_appoinment_vdate);
        session = (TextView) findViewById(R.id.ar_view_appoinment_vsession);
        phone = (TextView) findViewById(R.id.ar_view_appoinment_vphone);
        updateBtn =(Button) findViewById(R.id.ar_view_appoinment_update);
        myAppoinmentsBtn = (Button) findViewById(R.id.ar_view_appoinment_data);

        appId = getIntent().getStringExtra("appId");

        getData(id, name, doctor, session, date, phone);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewAppoinmentActivity.this, AddAppoinmentActivity.class);
                intent .putExtra("appId", appId);
                startActivity(intent);
            }
        });

        myAppoinmentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewAppoinmentActivity.this, MyAppoinmentsActivity.class);
                startActivity(intent);
            }
        });

    }



    private void getData(final TextView id, final TextView name, final TextView doctor, final TextView session, final TextView date, final TextView phone) {
        rootRef.child(appId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                id.setText(dataSnapshot.child("id").getValue().toString());
                name.setText(dataSnapshot.child("Name").getValue().toString());
                doctor.setText(dataSnapshot.child("doctor").getValue().toString());
                session.setText(dataSnapshot.child("session").getValue().toString());
                date.setText(dataSnapshot.child("Date").getValue().toString());
                phone.setText(dataSnapshot.child("Phone").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}