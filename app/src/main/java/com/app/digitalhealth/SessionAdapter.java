package com.app.digitalhealth;

import android.app.Activity;
import android.se.omapi.Session;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.digitalhealth.model.Sessions;
import com.app.digitalhealth.model.SugarReport;

import java.util.ArrayList;
import java.util.List;

public class SessionAdapter extends ArrayAdapter<Sessions> {

    private Activity context;
    private List<Sessions> sessionsList;
    private ArrayList<Sessions> arrayList;
    LayoutInflater layoutInflater;


    public SessionAdapter(Activity context, List<Sessions> sessionsList) {
        super(context, R.layout.activity_sessions, sessionsList);
        this.context = context;
        this.sessionsList = sessionsList;


    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_sessions, null, true);
        TextView date = (TextView) listViewItem.findViewById(R.id.ak_session_list_date);
        TextView doctorName = (TextView) listViewItem.findViewById(R.id.ak_session_list_day);
        TextView time = (TextView) listViewItem.findViewById(R.id.ak_session_list_time);
        TextView patientCount = (TextView) listViewItem.findViewById(R.id.ak_session_list_active_patients);


        Sessions sessions = sessionsList.get(position);

        date.setText(sessions.getDate());
        doctorName.setText(sessions.getName());
        time.setText(sessions.getTime());
        patientCount.setText(sessions.getNoOfPatients());

        return listViewItem;


    }




}
