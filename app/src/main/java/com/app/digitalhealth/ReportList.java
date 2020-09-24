package com.app.digitalhealth;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.app.digitalhealth.Inflators.SugarReportList;
import com.app.digitalhealth.model.Report;
import com.app.digitalhealth.model.SugarReport;
import com.app.digitalhealth.prevalent.Prevalent;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ReportList extends AppCompatActivity {

    public static  final String Report_IDS = "ReportID";
    public static  final String CUS_IDS = "cusID";
    public static  final String patientNameS = "patientName";
    public static  final String glucoseLevelS = "glucoseLevel";
    public static  final String clicked = "";
    private String CurrentUser = Prevalent.currentUser.getPhone();

    AutoCompleteTextView dropDown;
    AutoCompleteTextView selector;
    TextInputLayout textlayout;
    ListView listViewsugarReport;
    DatabaseReference dataBaseReport;
    List<Report> sugarLists;
    private String parentDbName = "sugarReports";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);


        listViewsugarReport = (ListView) findViewById(R.id.UsersSugar_list);
        sugarLists = new ArrayList<>();
        dropDown = findViewById(R.id.drops_type);
        selector = findViewById(R.id.drops_type);
        textlayout = findViewById(R.id.ReposType);

        Log.d("CurrentUser", CurrentUser);

        final String[] reportTypes = new String[]{
                "Blood Report",
                "Sugar Report"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                ReportList.this,
                R.layout.drop_down_item, reportTypes

        );

        dropDown.setAdapter(adapter);

        selector.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String s = charSequence.toString();

                if(s.equals("Sugar Report")){
                    parentDbName = "sugarReports";
                    Toast.makeText(ReportList.this, parentDbName, Toast.LENGTH_SHORT).show();
                    loadData();
                }
                if(s.equals("Blood Report")){
                    parentDbName = "bloodReports";
                    Toast.makeText(ReportList.this, parentDbName, Toast.LENGTH_SHORT).show();
                    loadData();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();

    }

    private void loadData() {

        dataBaseReport = FirebaseDatabase.getInstance().getReference(parentDbName);

        dataBaseReport.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sugarLists.clear();


                for (DataSnapshot sugarSnapshot : dataSnapshot.getChildren()) {


                    Report Report = sugarSnapshot.getValue(Report.class);

                    if (Report.getcustomerID().equals(CurrentUser)) {
                        sugarLists.add(Report);
                    }

                }


                SugarReportList adapter = new SugarReportList(ReportList.this, sugarLists);
                listViewsugarReport.setAdapter(adapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(parentDbName.equals("sugarReports")) {

            listViewsugarReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                    Report sugarReport = sugarLists.get(i);
                    Intent intent = new Intent(getApplicationContext(), ShowSugarReport_Activity.class);
                    intent.putExtra(Report_IDS, sugarReport.getReportID());
                    intent.putExtra(CUS_IDS, sugarReport.getcustomerID());
                    intent.putExtra(patientNameS, sugarReport.getPatientName());
//                   intent.putExtra(glucoseLevel, sugarReport.getGlucoseLevel());
                    intent.putExtra("clicked", "clicked");

                    startActivity(intent);

                }
            });
        }
//        else {
//
//            listViewsugarReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    Report sugarReport = sugarLists.get(i);
//                    Intent intent = new Intent(getApplicationContext(), AddBloodReport.class);
//                    intent.putExtra(Report_IDS, sugarReport.getReportID());
//                    intent.putExtra(CUS_IDS, sugarReport.getcustomerID());
//                    intent.putExtra(patientNameS, sugarReport.getPatientName());
//
//                    intent.putExtra("clicked", "clicked");
//
//                    startActivity(intent);
//                }
//            });
//
//        }

    }

}









