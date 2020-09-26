package com.app.digitalhealth.Inflators;

import android.app.Activity;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.digitalhealth.R;
import com.app.digitalhealth.ReportSearch;
import com.app.digitalhealth.SessionList;
import com.app.digitalhealth.model.Report;
import com.app.digitalhealth.model.Sessions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchAdapter extends BaseAdapter {

    private Activity context;
    List<Sessions> List;
    private ArrayList<Sessions> arraylist;
    LayoutInflater inflater;

    public SearchAdapter(Activity context, List<Sessions>  List){
//        super(context, R.layout.reportrow,List);
        this.context = context;
        this. List = List;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<Sessions>();
        this.arraylist.addAll(SessionList.SessionList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return SessionList.SessionList.size();
    }

    @Override
    public Sessions getItem(int i) {
        return SessionList.SessionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_sessions,null,true);
//        TextView  CusID = (TextView)listViewItem.findViewById(R.id.CusID);
//        TextView  patientName = (TextView)listViewItem.findViewById(R.id.patientname);
//        TextView  ReportID = (TextView)listViewItem.findViewById(R.id.reportID);

        TextView date = (TextView) listViewItem.findViewById(R.id.ak_session_list_date);
        TextView doctorName = (TextView) listViewItem.findViewById(R.id.ak_session_list_day);
        TextView time = (TextView) listViewItem.findViewById(R.id.ak_session_list_time);
        TextView patientCount = (TextView) listViewItem.findViewById(R.id.ak_session_list_active_patients);


        Sessions SessionList = List.get(position);

        date.setText(SessionList.getDate());
        doctorName.setText(SessionList.getName());
        patientCount.setText(SessionList.getNoOfPatients());


        return listViewItem;

    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        SessionList.SessionList.clear();
        if (charText.length() == 0) {
            SessionList.SessionList.addAll(arraylist);
        } else {
            for (Sessions wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    SessionList.SessionList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
