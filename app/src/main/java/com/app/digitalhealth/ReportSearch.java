package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.app.digitalhealth.model.SugarReport;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReportSearch extends AppCompatActivity {

    public static  final String Report_ID = "ReportID";
    public static  final String CUS_ID = "cusID";
    public static  final String patientName = "patientName";
    public static  final String glucoseLevel = "glucoseLevel";
    private static final String reportType ="Sugar Report";
    private
    String selectedItem ="";
//    public static  final String clicked = "";

    AutoCompleteTextView dropDown;
    TextInputLayout textlayout;
    ListView listViewsugarReports;
    DatabaseReference dataBaseSugarReports;
    List<SugarReport> sugarList;


    Button GenTest;
    private String parentDbName = "sugarReports";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_search);

        listViewsugarReports = (ListView) findViewById(R.id.UsersSugar_list);
        sugarList = new ArrayList<>();
        GenTest = findViewById(R.id.GenReportTest);
        dropDown = findViewById(R.id.drops_type);
        textlayout = findViewById(R.id.ReposType);

        final String[] reportTypes = new String[]{
                "Blood Report",
                "Sugar Report"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                ReportSearch.this,
                R.layout.drop_down_item, reportTypes

        );

        dropDown.setAdapter(adapter);



        GenTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportSearch.this,AddBloodReport.class));

            }
        });




        dropDown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem =  (String) parent.getItemAtPosition(position);
                // here is your selected item
                Log.d("type", selectedItem);
                if(selectedItem.equals(reportType)) {
                    parentDbName = "sugarReports";
                    Log.d("parent", parentDbName);
                }
            }


        });
    }

    @Override
    protected void onStart() {
        super.onStart();




            dataBaseSugarReports = FirebaseDatabase.getInstance().getReference(parentDbName);
            Log.d("parentNice", parentDbName);

            dataBaseSugarReports.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    sugarList.clear();
                    for (DataSnapshot sugarSnapshot : dataSnapshot.getChildren()) {

                        SugarReport sugarReport = sugarSnapshot.getValue(SugarReport.class);
                        sugarList.add(sugarReport);
                    }

                    SugarReportList adapter = new SugarReportList(ReportSearch.this, sugarList);
                    listViewsugarReports.setAdapter(adapter);
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });






        listViewsugarReports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                 SugarReport sugarReport = sugarList.get(i);
                 Intent intent = new Intent(getApplicationContext(),SugarReportActivity.class);
//                 intent.putExtra(Report_ID,sugarReport.getReportID());
//                 intent.putExtra(CUS_ID,sugarReport.getCustomerID());
//                 intent.putExtra(patientName,sugarReport.getPatientName());
//                 intent.putExtra(glucoseLevel,sugarReport.getGlucoseLevel());
                 intent.putExtra("clicked","clicked");

                 startActivity(intent);
}
        });
                }




}