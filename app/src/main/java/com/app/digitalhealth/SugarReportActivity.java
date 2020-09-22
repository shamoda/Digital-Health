package com.app.digitalhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.digitalhealth.model.SugarReport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SugarReportActivity extends AppCompatActivity {

    long maxID = 0;
    EditText CustomerID;
    EditText patientName;
    EditText glucoseLevelinput;
    Button addReport,UpdateReport,DeleteReport;
    DatabaseReference sugarReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugar_report);



        sugarReports = FirebaseDatabase.getInstance().getReference("sugarReports");

        CustomerID = (EditText) findViewById(R.id.inputcusID);
        patientName = (EditText) findViewById(R.id.inputPatientName);
        glucoseLevelinput = (EditText) findViewById(R.id.EditGlucoseLevel);
        addReport = (Button) findViewById(R.id.GenReport);
        UpdateReport = (Button) findViewById(R.id.Update);
        DeleteReport = (Button) findViewById(R.id.Delete);


        Intent intent = getIntent();
        final String ReportIDs = intent.getStringExtra(ReportSearch.Report_ID);
        final String cusID = intent.getStringExtra(ReportSearch.CUS_ID);
        String patName= intent.getStringExtra(ReportSearch.patientName);
        String glucoseLevel = intent.getStringExtra(ReportSearch.glucoseLevel);
        String Clicked = intent.getStringExtra("clicked");


        if (Clicked != null){
            addReport.setVisibility(View.INVISIBLE);
            UpdateReport.setVisibility(View.VISIBLE);
            DeleteReport.setVisibility(View.VISIBLE);

        }

        if (Clicked == null){
            addReport.setVisibility(View.VISIBLE);
            UpdateReport.setVisibility(View.INVISIBLE);
            DeleteReport.setVisibility(View.INVISIBLE);

        }






        CustomerID.setText(cusID);
        patientName.setText(patName);
        glucoseLevelinput.setText(glucoseLevel);


        sugarReports.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxID = (dataSnapshot.getChildrenCount());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



//       sugarReport = FirebaseDatabase.getInstance().getReference("SugarReports").child(id);

        addReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    GenerateReport();
            }
        }); //addReport


        UpdateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String custID = CustomerID.getText().toString().trim();
                String patName = patientName.getText().toString().trim();
                String glucoseLevel = glucoseLevelinput.getText().toString().trim();

                if(TextUtils.isEmpty(custID)){

                    CustomerID.setError("Customer ID required");
                    return;
                } else if(TextUtils.isEmpty(patName)){

                    patientName.setError("Patient Name Required");
                    return;
                }else if(TextUtils.isEmpty(glucoseLevel)){

                    glucoseLevelinput.setError("Glucose Level Required");
                    return;
                }else{

                    updateReports(ReportIDs,custID,patName,glucoseLevel);
                }


            }
        });

        DeleteReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(ReportIDs);
            }
        });

    }

    private void GenerateReport(){

        String cusID = CustomerID.getText().toString().trim();
        String patName = patientName.getText().toString().trim();
        String glucoseLevel = glucoseLevelinput.getText().toString().trim();

        if(TextUtils.isEmpty(cusID)){

            Toast.makeText(this, "You should enter the customer ID", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(patName)){

            Toast.makeText(this, "You should enter the patient name", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(glucoseLevel)){

            Toast.makeText(this, "You should enter the glucose level", Toast.LENGTH_SHORT).show();
        }else {
//            String id = sugarReports.push().getKey();

            String p = String.valueOf(maxID+1);
            String id =  "R"+p;



//            SugarReport Report = new SugarReport(id, cusID, patName, glucoseLevel);
//            sugarReports.child(id).setValue(Report);
            Toast.makeText(this, "Report Generated", Toast.LENGTH_SHORT).show();

//            SugarReport Report = new SugarReport(id, cusID, patName, glucoseLevel);
//            sugarReports.child(id).setValue(Report);
            Toast.makeText(this, "Artist Added", Toast.LENGTH_SHORT).show();

      }
    }

    private boolean updateReports(String ReportID,String cusID, String patName,String glucoseLevel){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("sugarReports").child(ReportID);
//        SugarReport sugarReport = new  SugarReport(ReportID,cusID,patName,glucoseLevel);

//        databaseReference.setValue(sugarReport);

        Toast.makeText(this, "Report Updated Successfully", Toast.LENGTH_SHORT).show();

        return true;
    }

    private void delete(String ReportID){
        DatabaseReference sugar = FirebaseDatabase.getInstance().getReference("sugarReports").child(ReportID);

        sugar.removeValue();


        Toast.makeText(this, "Report is deleted successfully", Toast.LENGTH_SHORT).show();
    }

}