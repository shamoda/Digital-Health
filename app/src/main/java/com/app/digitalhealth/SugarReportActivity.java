package com.app.digitalhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class SugarReportActivity extends AppCompatActivity {

    long maxID = 0;
    EditText CustomerID;
    EditText patientName;
    EditText glucoseLevelinput;
    Button addReport;
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

//       sugarReport = FirebaseDatabase.getInstance().getReference("SugarReports").child(id);

        addReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    GenerateReport();
            }
        });


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
            Toast.makeText(this, "Artist Added", Toast.LENGTH_SHORT).show();
      }
    }
}