package com.app.digitalhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.digitalhealth.model.Doctor;
import com.app.digitalhealth.model.Users;
import com.app.digitalhealth.viewholder.DoctorDetailsViewHolder;
import com.app.digitalhealth.viewholder.UserDetailsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ManageUsersActivity extends AppCompatActivity {

    private TextView closeBtn;
    private Button addNewCustomer;
    private SearchView searchUser;

    private DatabaseReference userRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        closeBtn = findViewById(R.id.sm_manage_users_close_btn);
        addNewCustomer = findViewById(R.id.sm_manage_users_add_new_patient_btn);
        searchUser = findViewById(R.id.sm_manage_users_search_view);

        userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        recyclerView = findViewById(R.id.sm_manage_users_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        searchUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchText(s);
                return false;
            }
        });


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageUsersActivity.this, AdminDashboard.class));
                finish();
            }
        });

        addNewCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageUsersActivity.this, UserRegistrationActivity.class);
                intent.putExtra("check", "check");
                startActivity(intent);
            }
        });

    }

    private void searchText(String s) {
        FirebaseRecyclerOptions<Users> options;

        if (s == null){
            options = new FirebaseRecyclerOptions.Builder<Users>().setQuery(userRef, Users.class).build();
        }
        else {
            Query firebaseSearchQuery = userRef.orderByChild("name").startAt(s).endAt(s + "\uf8ff");
            options = new FirebaseRecyclerOptions.Builder<Users>().setQuery(firebaseSearchQuery, Users.class).setLifecycleOwner(this).build();
        }


        FirebaseRecyclerAdapter<Users, UserDetailsViewHolder> adapter = new FirebaseRecyclerAdapter<Users, UserDetailsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserDetailsViewHolder userDetailsViewHolder, int i, @NonNull final Users users) {
                userDetailsViewHolder.name.setText(users.getName());
                userDetailsViewHolder.phone.setText(users.getPhone());
                Picasso.get().load(users.getProfileImage()).placeholder(R.drawable.ic_user).into(userDetailsViewHolder.image);

                userDetailsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showAlertDialog(users.getPhone(), users.getName(), users.getProfileImage());
                    }
                });
            }

            @NonNull
            @Override
            public UserDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_details_row, parent, false);
                UserDetailsViewHolder holder = new UserDetailsViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Users> options = new FirebaseRecyclerOptions.Builder<Users>().setQuery(userRef, Users.class).build();

        FirebaseRecyclerAdapter<Users, UserDetailsViewHolder> adapter = new FirebaseRecyclerAdapter<Users, UserDetailsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserDetailsViewHolder userDetailsViewHolder, int i, @NonNull final Users users) {
                userDetailsViewHolder.name.setText(users.getName());
                userDetailsViewHolder.phone.setText(users.getPhone());
                Picasso.get().load(users.getProfileImage()).placeholder(R.drawable.ic_user).into(userDetailsViewHolder.image);

                userDetailsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showAlertDialog(users.getPhone(), users.getName(), users.getProfileImage());
                    }
                });
            }

            @NonNull
            @Override
            public UserDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_details_row, parent, false);
                UserDetailsViewHolder holder = new UserDetailsViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void showAlertDialog(final String phone, String name, final String profileImage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ManageUsersActivity.this);
        builder.setTitle("Delete customer");
        builder.setMessage("Are you sure want to delete "+name+" from the system.");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                final ProgressDialog pd = new ProgressDialog(ManageUsersActivity.this);
                pd.setCanceledOnTouchOutside(false);
                pd.setMessage("Deleting...");
                pd.show();

                userRef.child(phone).removeValue();
                StorageReference proPicRef = FirebaseStorage.getInstance().getReferenceFromUrl(profileImage);
                proPicRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.dismiss();
                        Toast.makeText(ManageUsersActivity.this, "Customer record deleted successfully.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(ManageUsersActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                });

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
}