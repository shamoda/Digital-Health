package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class AdminDashboard extends AppCompatActivity {

    private Button manageCustomers;
    private Button manageDoctors;
    private Button manageReports;
    private Button manageSessions;
    private TextView logoutBtn;
    private TextView doctorsCount;
    private TextView customerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        manageDoctors = findViewById(R.id.admin_dashboard_manage_doctors);
        manageReports = findViewById(R.id.btnmanageReports);
        manageCustomers = findViewById(R.id.admin_dashboard_manage_customers);
        manageSessions = findViewById(R.id.btn_manage_sessions);
        logoutBtn = findViewById(R.id.admin_dashboard_logout_btn);
        doctorsCount = findViewById(R.id.admin_dashboard_doctor_count);
        customerCount = findViewById(R.id.admin_dashboard_customer_count);


        manageCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, ManageUsersActivity.class));
            }
        });

        manageDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, ManageDoctorsActivity.class));
            }
        });


        manageReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, ReportSearch.class));
            }
        });

        manageSessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, SessionList.class));
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();

                Intent intent = new Intent(AdminDashboard.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });


        loadInsights();


    }

    private void loadInsights() {

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.child("Doctors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long dCount = dataSnapshot.getChildrenCount();
                doctorsCount.setText(String.valueOf(dCount));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        rootRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long cCount = dataSnapshot.getChildrenCount();
                customerCount.setText(String.valueOf(cCount));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}