package com.app.digitalhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.digitalhealth.model.Appoinments;
import com.app.digitalhealth.prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyAppoinmentsActivity extends AppCompatActivity {

    ListView applist;
    DatabaseReference rootRef;
    List<Appoinments> listAppoinment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appoinments);

        applist = findViewById(R.id.ar_my_appoinments_list);
        listAppoinment = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        loadData();
    }

    private void loadData() {
        rootRef= FirebaseDatabase.getInstance().getReference("Appoinments");
        Query query = rootRef.orderByChild("userId").equalTo(Prevalent.currentUser.getPhone());
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listAppoinment.clear();
                for (DataSnapshot appoinmentSnapshot: dataSnapshot.getChildren()){
                    Appoinments appoinments = appoinmentSnapshot.getValue(Appoinments.class);
                    listAppoinment.add(appoinments);
                }

                AppoinmentAdapter adapter = new AppoinmentAdapter(MyAppoinmentsActivity.this,listAppoinment);
                applist.setAdapter(adapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        applist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Appoinments appoinments = listAppoinment.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(MyAppoinmentsActivity.this);
                builder.setTitle("Delete Appointment");
                builder.setMessage("Are you sure that you want to delete this appointment?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteApp(appoinments.getId());
                    }
                });

                builder.setNeutralButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MyAppoinmentsActivity.this, AddAppoinmentActivity.class);
                        intent .putExtra("appId", appoinments.getId());
                        startActivity(intent);
                    }
                });
                builder.show();

            }
        });
    }

    private void deleteApp(String id) {

        rootRef.child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MyAppoinmentsActivity.this, "Deleted successfully.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}