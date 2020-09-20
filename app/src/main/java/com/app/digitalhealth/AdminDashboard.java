package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminDashboard extends AppCompatActivity {

    private Button manageDoctors;
    private Button manageReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        manageDoctors = findViewById(R.id.admin_dashboard_manage_doctors);
        manageReports = findViewById(R.id.btnmanageReports);

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


    }
}