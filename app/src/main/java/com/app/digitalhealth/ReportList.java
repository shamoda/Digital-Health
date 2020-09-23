package com.app.digitalhealth;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.app.digitalhealth.model.SugarReport;
import com.app.digitalhealth.prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;


public class ReportList extends AppCompatActivity {

//    public static  final String Report_ID = "ReportID";
//    public static  final String CUS_ID = "cusID";
//    public static  final String patientName = "patientName";
//    public static  final String glucoseLevel = "glucoseLevel";
//    public static  final String clicked = "";
    private String CurrentUser =  Prevalent.currentUser.getPhone();

    ListView listViewsugarReport;
    DatabaseReference dataBaseSugarReport;
    List<SugarReport> sugarLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        dataBaseSugarReport = FirebaseDatabase.getInstance().getReference("sugarReports");

        listViewsugarReport = (ListView) findViewById(R.id.UsersSugar_list);
        sugarLists = new ArrayList<>();

        Log.d("CurrentUser", CurrentUser);


    }

    @Override
    protected void onStart() {
        super.onStart();

        dataBaseSugarReport.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sugarLists.clear();



                    for (DataSnapshot sugarSnapshot : dataSnapshot.getChildren()) {


                        SugarReport sugarReport = sugarSnapshot.getValue(SugarReport.class);

                    if(sugarReport.getCustomerID().equals(CurrentUser)){
                            sugarLists.add(sugarReport);
                        }

                    }




                SugarReportList adapter = new SugarReportList(ReportList.this, sugarLists);
                listViewsugarReport.setAdapter(adapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}









