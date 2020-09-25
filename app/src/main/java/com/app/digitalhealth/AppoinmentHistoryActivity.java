package com.app.digitalhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.app.digitalhealth.Inflators.AppoinmentAdapter;
import com.app.digitalhealth.model.Appoinments;
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

public class AppoinmentHistoryActivity extends AppCompatActivity {

    ListView applist;
    DatabaseReference rootRef;
    List<Appoinments> listAppoinment;
    SearchView search;
    Query query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoinment_history);

        rootRef= FirebaseDatabase.getInstance().getReference("Appoinments");
        query = rootRef;

        applist = findViewById(R.id.ar_appoinment_history_list);
        listAppoinment = new ArrayList<>();

        search = findViewById(R.id.ar_appoinment_history_search);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchApp(s);
                return false;
            }
        });





    }

    private void searchApp(String s) {
        if(s == null){
            query = rootRef;
        }
        else if (s != null){
            query = rootRef.orderByChild("id").startAt(s).endAt(s + "\uf8ff");
        }

        loadData();
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadData();
    }

    private void loadData() {
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listAppoinment.clear();
                for (DataSnapshot appoinmentSnapshot: dataSnapshot.getChildren()){
                    Appoinments appoinments = appoinmentSnapshot.getValue(Appoinments.class);
                    listAppoinment.add(appoinments);
                }

                AppoinmentAdapter adapter = new AppoinmentAdapter(AppoinmentHistoryActivity.this,listAppoinment);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(AppoinmentHistoryActivity.this);
                builder.setTitle("Delete Appointment");
                builder.setMessage("Are you sure that you want to delete this appointment?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteApp(appoinments.getId());
                    }
                });

                builder.setNeutralButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
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
                Toast.makeText(AppoinmentHistoryActivity.this, "Deleted successfully.", Toast.LENGTH_SHORT).show();
            }
        });

    }


}


