package com.app.digitalhealth;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.digitalhealth.model.Appoinments;

import java.util.List;

public class AppoinmentAdapter extends ArrayAdapter<Appoinments> {
    private Activity context;
    private List<Appoinments> appoinmentList;

    public AppoinmentAdapter(Activity context, List<Appoinments> appoinmentList){
        super(context,R.layout.appoinment_details_row, appoinmentList);
        this.context= context;
        this.appoinmentList = appoinmentList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater inflater  = context.getLayoutInflater();

        View listViewItem =    inflater.inflate(R.layout.appoinment_details_row,null,true);
        TextView app_id = (TextView) listViewItem.findViewById(R.id.ar_appoinment_history_id_value);
        TextView name = (TextView) listViewItem.findViewById(R.id.ar_appoinment_history_name_value);
        TextView date = (TextView) listViewItem.findViewById(R.id.ar_appoinment_history_date_value);

        Appoinments appoinments = appoinmentList.get(position);

        app_id.setText(appoinments.getId());
        name.setText(appoinments.getName());
        date.setText(appoinments.getDate());

        return listViewItem;
    }
}
