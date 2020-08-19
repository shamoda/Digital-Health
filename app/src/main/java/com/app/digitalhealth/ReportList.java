package com.app.digitalhealth;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;
import com.google.android.material.textfield.TextInputLayout;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ReportList extends AppCompatActivity {
    ListView listView;
    private TextInputLayout textInputLayout;
    private AutoCompleteTextView drops;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);


        String[] names = new String[]{"John Sparrow", "Jack Johnson", "Mahela Jayawardena", "Kumar Sangakkara", "Lasith Malinga", "MS Dhoni","Virat Kohli","Steven Smith"};




        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this,R.layout.reportrow,R.id.txt1
                ,names);

        listView = findViewById(R.id.myList);
        listView.setFocusable(false);
        listView.setAdapter(arrayAdapter);

//        textInputLayout =findViewById(R.id.PatientName);
//        drops = findViewById(R.id.drops);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.dropdown_item,names);
//
//
//        drops.setAdapter(adapter);


    }
}









