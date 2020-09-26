package com.app.digitalhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SearchView;

import com.app.digitalhealth.Inflators.Showsugarlist;
import com.app.digitalhealth.Inflators.SugarReportList;
import com.app.digitalhealth.model.BloodReport;
import com.app.digitalhealth.model.Doctor;
import com.app.digitalhealth.model.Report;
import com.app.digitalhealth.model.SugarReport;
import com.app.digitalhealth.prevalent.Prevalent;
import com.app.digitalhealth.viewholder.DoctorDetailsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class ReportList extends AppCompatActivity implements SearchView.OnQueryTextListener{

    public static  final String Report_ID = "ReportID";
    public static  final String CUS_ID = "cusID";
    public static  final String patientName = "patientName";
    public static  final String glucoseLevel = "glucoseLevel";
    private static final String reportType ="Sugar Report";
    private String CurrentUser = Prevalent.currentUser.getPhone();
    String selectedItem ="";
    //    public static  final String clicked = "";
    SearchView search;
    AutoCompleteTextView dropDown;
    AutoCompleteTextView selector;
    TextInputLayout textlayout;
    ListView listView;
    DatabaseReference dataBaseReports;
    private TextView closeBtn;


    public static ArrayList<Report> sugarList = new ArrayList<Report>();

    private String parentDbName = "sugarReports";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        listView = (ListView) findViewById(R.id.listUser);
//        sugarList = new ArrayList<>();


        dropDown = findViewById(R.id.drops_type);
        selector = findViewById(R.id.drops_type);
        textlayout = findViewById(R.id.ReposType);
        search = (SearchView) findViewById(R.id.searchReport);
        search.setOnQueryTextListener(this);
        closeBtn =  findViewById(R.id.reportlistClose);


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportList.this, HomeActivity.class));
                finish();
            }
        });

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


        dataBaseReports = FirebaseDatabase.getInstance().getReference(parentDbName);
        Log.d("parentNice", parentDbName);


        dataBaseReports.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    sugarList.clear();
                for (DataSnapshot sugarSnapshot : dataSnapshot.getChildren()) {

                    Report sugarReport = sugarSnapshot.getValue(Report.class);
                    if(CurrentUser.equals(sugarReport.getcustomerID())) {
                        sugarList.add(sugarReport);
                    }
                }

                Showsugarlist adapter = new Showsugarlist(ReportList.this, sugarList);
                listView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        if (parentDbName.equals("sugarReports")) {

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                    Report sugarReport = sugarList.get(i);
                    Intent intent = new Intent(getApplicationContext(), showsugarReportactivity.class);
                    intent.putExtra(Report_ID, sugarReport.getReportID());
                    intent.putExtra(CUS_ID, sugarReport.getcustomerID());
                    intent.putExtra(patientName, sugarReport.getPatientName());
//                   intent.putExtra(glucoseLevel, sugarReport.getGlucoseLevel());


                    startActivity(intent);

                }
            });
        } else {

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Report sugarReport = sugarList.get(i);
                    Intent intent = new Intent(getApplicationContext(), ShowBloodreport.class);
                    intent.putExtra(Report_ID, sugarReport.getReportID());

                    intent.putExtra(CUS_ID, sugarReport.getcustomerID());
                    intent.putExtra(patientName, sugarReport.getPatientName());
                    intent.putExtra("clicked", "clicked");

                    startActivity(intent);
                }
            });

        }


    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String  newText) {

        SugarReportList adapters = new SugarReportList(ReportList.this, sugarList);
        listView.setAdapter(adapters);
        String text = newText;
        adapters.filter(text);

        if(newText.isEmpty())
        {
            loadData();
        }

        return false;


    }
}





