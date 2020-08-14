package com.app.digitalhealth.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.digitalhealth.R;
import com.app.digitalhealth.interfaces.ItemClickListner;

public class DoctorDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView specialization, name;
    public ImageView image;
    public ItemClickListner listner;

    public DoctorDetailsViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.sm_doctor_details_row_img);
        specialization = itemView.findViewById(R.id.sm_doctor_details_row_specialization_value);
        name = itemView.findViewById(R.id.sm_doctor_details_row_name_value);

    }

    public void setItemClickListner(ItemClickListner listner){
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
