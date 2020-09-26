package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.digitalhealth.model.BloodReport;
import com.app.digitalhealth.model.SugarReport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ShowBloodreport extends AppCompatActivity {

    TextView CustomerID,patientNAME,heam,pcv,rbc,lympho,mono,eoso,myel,band,blast,platelet,comment;
    DatabaseReference bloodReports;
    TextView closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bloodreport);


        bloodReports = FirebaseDatabase.getInstance().getReference("bloodReports");


        CustomerID = (TextView) findViewById(R.id.txtcusID);
        patientNAME = (TextView) findViewById(R.id.patName);
        pcv = (TextView) findViewById(R.id.pcvtxt);
        rbc = (TextView) findViewById(R.id.txtRBC);
        lympho= (TextView) findViewById(R.id.txtLympho);
        heam = (TextView) findViewById(R.id.Heam);
        mono= (TextView) findViewById(R.id.txtMono);
        eoso = (TextView) findViewById(R.id.txtEoso);
        myel= (TextView) findViewById(R.id.txtMyelo);
        band = (TextView) findViewById(R.id.txtBand);
        blast = (TextView) findViewById(R.id.textBlast);
        platelet =(TextView) findViewById(R.id.txtPlatelet);
        comment = (TextView) findViewById(R.id.txtComment);
        closeBtn = findViewById(R.id.closeBlood);


        Intent intent = getIntent();
        final String ReportIDs = intent.getStringExtra(ReportList.Report_ID);
        final String cusID = intent.getStringExtra(ReportList.CUS_ID);
        String patName= intent.getStringExtra(ReportList.patientName);
        String Clicked = intent.getStringExtra("clicked");

        if(ReportIDs != null){
            loadData(ReportIDs);
        }


        CustomerID.setText(cusID);
        patientNAME.setText(patName);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowBloodreport.this, HomeActivity.class));
                finish();
            }
        });

    }

    private void loadData(String id) {


           bloodReports.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                BloodReport bldRep = dataSnapshot.getValue(BloodReport.class);

                pcv.setText(bldRep.getPcv());
                rbc.setText(bldRep.getRbc());
                lympho.setText(bldRep.getLympho());
                heam.setText(bldRep.getHeam());
                mono.setText(bldRep.getMono());
                eoso.setText(bldRep.getEoso());
                myel.setText(bldRep.getMyel());
                band.setText(bldRep.getBand());
                blast.setText(bldRep.getBlast());
                platelet.setText(bldRep.getPlatelet());
                comment.setText(bldRep.getComment());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
