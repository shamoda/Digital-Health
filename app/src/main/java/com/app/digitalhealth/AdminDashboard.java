package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminDashboard extends AppCompatActivity {

    private Button manageCustomers;
    private Button manageDoctors;
    private Button manageReports;
    private Button manageSessions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        manageDoctors = findViewById(R.id.admin_dashboard_manage_doctors);
        manageReports = findViewById(R.id.btnmanageReports);
        manageCustomers = findViewById(R.id.admin_dashboard_manage_customers);
        manageSessions = findViewById(R.id.btn_manage_sessions);

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


    }
}