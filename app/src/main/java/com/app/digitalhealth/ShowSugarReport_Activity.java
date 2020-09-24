package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.app.digitalhealth.model.SugarReport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.TextView;

public class ShowSugarReport_Activity extends AppCompatActivity {


    TextView CustomerID;
    TextView  patientName;
    TextView  glucoseLevelinput;
    DatabaseReference sugarReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sugar_report_);

        sugarReports = FirebaseDatabase.getInstance().getReference("sugarReports");

        CustomerID = (TextView) findViewById(R.id.inputCusIDs);
        patientName = (TextView) findViewById(R.id.inputpatientNames);
        glucoseLevelinput = (TextView) findViewById(R.id.glucoseLevelss);

        Intent intent = getIntent();
        final String ReportIDs = intent.getStringExtra(ReportList.Report_IDS);
        final String cusID = intent.getStringExtra(ReportList.CUS_IDS);
        String patName= intent.getStringExtra(ReportList.patientNameS);
        String Clicked = intent.getStringExtra("clicked");

        if(ReportIDs != null){
            loadData(ReportIDs);
        }


        CustomerID.setText(cusID);
        patientName.setText(patName);
    }

    private void loadData(String id) {

        sugarReports.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SugarReport rep = dataSnapshot.getValue(SugarReport.class);
                glucoseLevelinput.setText(rep.getGlucoseLevel());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}




