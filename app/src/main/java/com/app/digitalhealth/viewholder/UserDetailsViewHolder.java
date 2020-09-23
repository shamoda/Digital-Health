package com.app.digitalhealth.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.digitalhealth.R;
import com.app.digitalhealth.interfaces.ItemClickListner;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView phone;
    public TextView name;
    public CircleImageView image;
    public ItemClickListner listner;

    public UserDetailsViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.sm_patient_details_row_img);
        phone = itemView.findViewById(R.id.sm_patient_details_row_phone_value);
        name = itemView.findViewById(R.id.sm_patient_details_row_name_value);

    }

    public void setItemClickListner(ItemClickListner listner){
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
