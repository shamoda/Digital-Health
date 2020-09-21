package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.app.digitalhealth.model.SugarReport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReportSearch extends AppCompatActivity {

    ListView listViewsugarReports;
    DatabaseReference dataBaseSugarReports;
    List<SugarReport> sugarList;
    Button GenTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_search);

        dataBaseSugarReports = FirebaseDatabase.getInstance().getReference("sugarReports");

        listViewsugarReports = (ListView) findViewById(R.id.myList);
        sugarList = new ArrayList<>();
        GenTest = findViewById(R.id.GenReportTest);


        GenTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportSearch.this, SugarReportActivity.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        dataBaseSugarReports.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sugarList.clear();
                for(DataSnapshot sugarSnapshot : dataSnapshot.getChildren()) {

                    SugarReport sugarReport = sugarSnapshot.getValue(SugarReport.class);
                     sugarList.add(sugarReport);
                }

//               SugarReportList adapter = new SugarReportList(ReportSearch.this,sugarList);
//                listViewsugarReports.setAdapter(adapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}