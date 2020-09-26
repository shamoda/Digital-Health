package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.app.digitalhealth.model.BloodReport;


import com.app.digitalhealth.model.SugarReport;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AddBloodReport extends AppCompatActivity {


    Integer maxID = 0;
    EditText CustomerID,patientNAME,heam,pcv,rbc,lympho,mono,eoso,myel,band,blast,platelet,comment;

    Button reportAdd,UpdateReport,DeleteReport;
    DatabaseReference bloodReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blood_report);



        bloodReports = FirebaseDatabase.getInstance().getReference("bloodReports");

        CustomerID = (EditText) findViewById(R.id.inputcusID);
        patientNAME = (EditText) findViewById(R.id.inputPatientName);
        pcv = (EditText) findViewById(R.id.pcvinput);
        rbc = (EditText) findViewById(R.id.RBCinput);
        lympho= (EditText) findViewById(R.id.Lymphoinput);
        heam = (EditText) findViewById(R.id.Heaminput);
        mono= (EditText) findViewById(R.id.Monoinput);
        eoso = (EditText) findViewById(R.id.Eosoinput);
        myel= (EditText) findViewById(R.id.Myeloinput);
        band = (EditText) findViewById(R.id.bandInput);
        blast = (EditText) findViewById(R.id.blastInput);
        platelet = (EditText) findViewById(R.id.plateletInput);
        comment = (EditText) findViewById(R.id.commentInput);
        reportAdd = (Button) findViewById(R.id.GenReport);
        UpdateReport = (Button) findViewById(R.id.Update);
        DeleteReport = (Button) findViewById(R.id.Delete);



        Intent intent = getIntent();
        final String ReportIDs = intent.getStringExtra(ReportSearch.Report_ID);
        final String cusID = intent.getStringExtra(ReportSearch.CUS_ID);
        String patName= intent.getStringExtra(ReportSearch.patientName);
        CustomerID.setText(cusID);
        patientNAME.setText(patName);
        String Clicked = intent.getStringExtra("clicked");
        if(ReportIDs != null){
            loadData(ReportIDs);
        }

        if (Clicked != null){
            reportAdd.setVisibility(View.INVISIBLE);
            UpdateReport.setVisibility(View.VISIBLE);
            DeleteReport.setVisibility(View.VISIBLE);

        }

        if (Clicked == null){
            reportAdd.setVisibility(View.VISIBLE);
            UpdateReport.setVisibility(View.INVISIBLE);
            DeleteReport.setVisibility(View.INVISIBLE);

        }







        DatabaseReference db =FirebaseDatabase.getInstance().getReference().child("bloodReports");
        Query query = db.orderByKey().limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    maxID = Integer.parseInt(child.getKey()) ;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            };

        });



//       sugarReport = FirebaseDatabase.getInstance().getReference("SugarReports").child(id);

        reportAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenerateReport();
            }
        }); //addReport


        UpdateReport.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String custID = CustomerID.getText().toString().trim();
                String patName = patientNAME.getText().toString().trim();
                String pcvs = pcv.getText().toString().trim();
                String rbcs = rbc.getText().toString().trim();
                String lymphos = lympho.getText().toString().trim();
                String heams = heam.getText().toString().trim();
                String monos = mono.getText().toString().trim();
                String eosos = eoso.getText().toString().trim();
                String myels = myel.getText().toString().trim();
                String bands = band.getText().toString().trim();
                String blasts = blast.getText().toString().trim();
                String plateletes = platelet.getText().toString().trim();
                String comments = comment.getText().toString().trim();

                if(TextUtils.isEmpty(custID)){

                    CustomerID.setError("Customer ID required");
                    return;
                } else if(TextUtils.isEmpty(patName)){

                    patientNAME.setError("Patient Name Required");
                    return;
                }else{

                    updateReports(ReportIDs,custID,patName,rbcs,pcvs,lymphos,heams,monos,eosos,myels,bands,blasts,plateletes,comments);
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
        String patName = patientNAME.getText().toString().trim();
        String heams = heam.getText().toString().trim();
        String pcvs = pcv.getText().toString().trim();
        String rbcs = rbc.getText().toString().trim();
        String lymphos = lympho.getText().toString().trim();
        String monos = mono.getText().toString().trim();
        String eosos = eoso.getText().toString().trim();
        String  myelo = myel.getText().toString().trim();
        String bands = band.getText().toString().trim();
        String blasts = blast.getText().toString().trim();
        String platelets = platelet.getText().toString().trim();
        String comments = comment.getText().toString().trim();




        if(TextUtils.isEmpty(cusID)){

            Toast.makeText(this, "You should enter the customer ID", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(patName)){

            Toast.makeText(this, "You should enter the patient name", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(heams)){

            Toast.makeText(this, "You should enter the glucose level", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(patName)){

            Toast.makeText(this, "You should enter the patient name", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(pcvs)){

            Toast.makeText(this, "You should enter the glucose level", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(patName)){

            Toast.makeText(this, "You should enter the patient name", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(rbcs)){

            Toast.makeText(this, "You should enter the glucose level", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(lymphos)){

            Toast.makeText(this, "You should enter the patient name", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(monos)){

            Toast.makeText(this, "You should enter the glucose level", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(eosos)){

            Toast.makeText(this, "You should enter the patient name", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(myelo)){

            Toast.makeText(this, "You should enter the glucose level", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(bands)){

            Toast.makeText(this, "You should enter the patient name", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(blasts)){

            Toast.makeText(this, "You should enter the glucose level", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(platelets)){

            Toast.makeText(this, "You should enter the glucose level", Toast.LENGTH_SHORT).show();
        }else {
//            String id = sugarReports.push().getKey();

            String id = String.valueOf(maxID+1);
//            String id =  "R"+p;


            BloodReport bloodreport = new BloodReport(id,cusID,patName, heams,pcvs,rbcs, lymphos, monos,eosos,myelo,bands,blasts,platelets,comments);
            bloodReports.child(id).setValue(bloodreport);
            Toast.makeText(this, "Report Generated", Toast.LENGTH_SHORT).show();




        }
    }

    private void loadData(String id) {

        bloodReports.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                pcv = (EditText) findViewById(R.id.pcvinput);
                rbc = (EditText) findViewById(R.id.RBCinput);
                lympho= (EditText) findViewById(R.id.Lymphoinput);
                heam = (EditText) findViewById(R.id.Heaminput);
                mono= (EditText) findViewById(R.id.Monoinput);
                eoso = (EditText) findViewById(R.id.Eosoinput);
                myel= (EditText) findViewById(R.id.Myeloinput);
                band = (EditText) findViewById(R.id.bandInput);
                blast = (EditText) findViewById(R.id.blastInput);
                platelet = (EditText) findViewById(R.id.plateletInput);
                comment = (EditText) findViewById(R.id.commentInput);
                reportAdd = (Button) findViewById(R.id.GenReport);
                UpdateReport = (Button) findViewById(R.id.Update);
                DeleteReport = (Button) findViewById(R.id.Delete);

                BloodReport rep = dataSnapshot.getValue(BloodReport.class);
                pcv.setText(rep.getPcv());
                rbc.setText(rep.getRbc());
                lympho.setText(rep.getLympho());
                heam.setText(rep.getHeam());
                mono.setText(rep.getMono());
                eoso.setText(rep.getEoso());
                myel.setText(rep.getMyel());
                band.setText(rep.getBand());
                blast.setText(rep.getBlast());
                platelet.setText(rep.getPlatelet());
                comment.setText(rep.getComment());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private boolean updateReports(String ReportID,String cusID, String patName,String heams,String pcvss,
                                  String rbc,String lymphocytes,String mono,String eoso,String myelosytes, String bands,String blasts,String platelets,String comments){


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("bloodReports").child(ReportID);
        BloodReport bloodReport = new BloodReport(ReportID,cusID,patName,heams,pcvss,rbc,lymphocytes,mono,eoso,myelosytes,bands,blasts,platelets,comments);

        databaseReference.setValue(bloodReport);

        Toast.makeText(this, "Report Updated Successfully", Toast.LENGTH_SHORT).show();

        return true;
    }

    private void delete(String ReportID){
        DatabaseReference blood = FirebaseDatabase.getInstance().getReference("bloodReports").child(ReportID);

        blood.removeValue();


        Toast.makeText(this, "Report is deleted successfully", Toast.LENGTH_SHORT).show();
    }

}