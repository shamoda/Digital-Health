package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ReportSearch extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_search);




        String[] name = new String[]{"John Sparrow", "Jack Johnson", "Mahela Jayawardena", "Kumar Sangakkara", "Lasith Malinga", "MS Dhoni","Virat Kohli","Steven Smith"};


        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this,R.layout.reportrow,R.id.txt1
                ,name);

        listView = findViewById(R.id.myList);
        listView.setFocusable(false);
        listView.setAdapter(arrayAdapter);
    }
}