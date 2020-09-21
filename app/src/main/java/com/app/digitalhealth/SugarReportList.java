package com.app.digitalhealth;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.digitalhealth.model.SugarReport;

import java.util.List;

public class SugarReportList extends ArrayAdapter<SugarReport> {

    private Activity context;
    private List<SugarReport> sugarlist;

    public SugarReportList(Activity context, List<SugarReport> sugarList){
        super(context, R.layout.reportrow,sugarList);
        this.context = context;
        this.sugarlist = sugarList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.reportrow,null,true);
        TextView  CusID = (TextView)listViewItem.findViewById(R.id.CusID);
        TextView  patientName = (TextView)listViewItem.findViewById(R.id.patientname);
        TextView  ReportID = (TextView)listViewItem.findViewById(R.id.reportID);

        SugarReport sugarReport = sugarlist.get(position);

        CusID.setText(sugarReport.getCustomerID());
        patientName.setText(sugarReport.getPatientName());
        ReportID.setText(sugarReport.getReportID());

        return listViewItem;

    }
}
