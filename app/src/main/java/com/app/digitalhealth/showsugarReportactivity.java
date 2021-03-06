package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.app.digitalhealth.model.SugarReport;
import com.app.digitalhealth.prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.View;
import android.widget.TextView;

import io.paperdb.Paper;

public class showsugarReportactivity extends AppCompatActivity {

    TextView CustomerID;
    TextView  patientName;
    TextView  glucoseLevelinput;
    DatabaseReference sugarReports;
    TextView closeBtn;
    private String CurrentUser = Prevalent.currentUser.getPhone();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showsugar_reportactivity);

        sugarReports = FirebaseDatabase.getInstance().getReference("sugarReports");

        CustomerID = (TextView) findViewById(R.id.inputCusIDs);
        patientName = (TextView) findViewById(R.id.inputpatientNames);
        glucoseLevelinput = (TextView) findViewById(R.id.glucoseLevelss);
        closeBtn = (TextView) findViewById(R.id.closeSugar);


//        Paper.init(this);
////        closeBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent intent = new Intent(getApplicationContext(),ReportList.class);
////                startActivity(intent);
////
////            }
////        });


        Intent intent = getIntent();
        final String ReportIDs = intent.getStringExtra(ReportList.Report_ID);
        final String cusID = intent.getStringExtra(ReportList.CUS_ID);
        String patName= intent.getStringExtra(ReportList.patientName);
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


