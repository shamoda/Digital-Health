package com.app.digitalhealth;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.digitalhealth.model.Doctor;
import com.app.digitalhealth.prevalent.Prevalent;
import com.app.digitalhealth.viewholder.DoctorDetailsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

import static android.Manifest.permission.CALL_PHONE;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextInputLayout textInputLayout;
    AutoCompleteTextView dropDown;
    AutoCompleteTextView specializationSelector;

    Button callAnAmbulance;
    private SearchView searchDoctor;

    private DatabaseReference doctorRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setNavigationViewListener();

        callAnAmbulance = findViewById(R.id.sm_home_call_an_ambulance);
        specializationSelector = findViewById(R.id.sm_home_specialization_value);
        searchDoctor = findViewById(R.id.sm_home_search_view);

        Paper.init(this);

        doctorRef = FirebaseDatabase.getInstance().getReference().child("Doctors");

        textInputLayout = findViewById(R.id.sm_home_specialization);
        dropDown = findViewById(R.id.sm_home_specialization_value);

        String[] specialization = new String[]{
                "All",
                "Allergists",
                "Anesthesiologist",
                "Cardiologist",
                "Colon and Rectal Surgeon",
                "Dermatologist",
                "Endocrinologist",
                "Family Physician",
                "Gastroenterologist",
                "Hematologist",
                "Infectious Disease",
                "Internist",
                "Nephrologist",
                "Neurologist",
                "Oncologist",
                "Pathologist",
                "Psychiatrist",
                "Radiologist",
                "Rheumatologist",
                "Sports Medicine",
                "Urologist"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                HomeActivity.this,
                R.layout.drop_down_item,
                specialization
        );

        dropDown.setAdapter(adapter);


        searchDoctor.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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


        specializationSelector.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterDoctor(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        callAnAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:1234"));

                if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                } else {
                    requestPermissions(new String[]{CALL_PHONE}, 1);
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Digital Health");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);


        recyclerView = findViewById(R.id.sm_home_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }



    private void searchText(String s) {
        FirebaseRecyclerOptions<Doctor> options;

        if (s == null){
            options = new FirebaseRecyclerOptions.Builder<Doctor>().setQuery(doctorRef, Doctor.class).build();
        }
        else {
            Query firebaseSearchQuery = doctorRef.orderByChild("name").startAt(s).endAt(s + "\uf8ff");
            options = new FirebaseRecyclerOptions.Builder<Doctor>().setQuery(firebaseSearchQuery, Doctor.class).setLifecycleOwner(this).build();
        }


        FirebaseRecyclerAdapter<Doctor, DoctorDetailsViewHolder> adapter = new FirebaseRecyclerAdapter<Doctor, DoctorDetailsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DoctorDetailsViewHolder doctorDetailsViewHolder, int i, @NonNull final Doctor doctor) {
                doctorDetailsViewHolder.specialization.setText(doctor.getSpecialization());
                doctorDetailsViewHolder.name.setText("Dr. " + doctor.getName());
                Picasso.get().load(doctor.getImage()).into(doctorDetailsViewHolder.image);

                doctorDetailsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(HomeActivity.this, doctor.getName(), Toast.LENGTH_SHORT).show();
                        //Add intent for session list
                    }
                });
            }

            @NonNull
            @Override
            public DoctorDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_details_row, parent, false);
                DoctorDetailsViewHolder holder = new DoctorDetailsViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }



    private void filterDoctor(String s) {
        FirebaseRecyclerOptions<Doctor> options;

        if (s.equals("All")){
            options = new FirebaseRecyclerOptions.Builder<Doctor>().setQuery(doctorRef, Doctor.class).build();
        }
        else {
            Query firebaseSearchQuery = doctorRef.orderByChild("specialization").startAt(s).endAt(s + "\uf8ff");
            options = new FirebaseRecyclerOptions.Builder<Doctor>().setQuery(firebaseSearchQuery, Doctor.class).setLifecycleOwner(this).build();
        }


        FirebaseRecyclerAdapter<Doctor, DoctorDetailsViewHolder> adapter = new FirebaseRecyclerAdapter<Doctor, DoctorDetailsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DoctorDetailsViewHolder doctorDetailsViewHolder, int i, @NonNull final Doctor doctor) {
                doctorDetailsViewHolder.specialization.setText(doctor.getSpecialization());
                doctorDetailsViewHolder.name.setText("Dr. " + doctor.getName());
                Picasso.get().load(doctor.getImage()).into(doctorDetailsViewHolder.image);

                doctorDetailsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(HomeActivity.this, doctor.getName(), Toast.LENGTH_SHORT).show();
                        //Add intent for session list
                    }
                });
            }

            @NonNull
            @Override
            public DoctorDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_details_row, parent, false);
                DoctorDetailsViewHolder holder = new DoctorDetailsViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Doctor> options = new FirebaseRecyclerOptions.Builder<Doctor>().setQuery(doctorRef, Doctor.class).build();

        FirebaseRecyclerAdapter<Doctor, DoctorDetailsViewHolder> adapter = new FirebaseRecyclerAdapter<Doctor, DoctorDetailsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DoctorDetailsViewHolder doctorDetailsViewHolder, int i, @NonNull final Doctor doctor) {
                doctorDetailsViewHolder.specialization.setText(doctor.getSpecialization());
                doctorDetailsViewHolder.name.setText("Dr. " + doctor.getName());
                Picasso.get().load(doctor.getImage()).into(doctorDetailsViewHolder.image);

                doctorDetailsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Add intent for session list
                        Intent intent = new Intent(HomeActivity.this, SelectSessionActivity.class);
                        intent.putExtra("name", doctor.getName());
                        intent.putExtra("phone", doctor.getPhone());
                        intent.putExtra("specialization", doctor.getSpecialization());
                        intent.putExtra("image", doctor.getImage());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public DoctorDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_details_row, parent, false);
                DoctorDetailsViewHolder holder = new DoctorDetailsViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.sm_side_drawer_user_name);
        CircleImageView userProfileImage = headerView.findViewById(R.id.sm_side_drawer_user_profile_img);

        userNameTextView.setText(Prevalent.currentUser.getName());
        Picasso.get().load(Prevalent.currentUser.getProfileImage()).placeholder(R.drawable.ic_user).into(userProfileImage);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.sm_nav_find_a_doctor){

        }
        else  if(id == R.id.sm_nav_my_appointments){

        }
        else if(id == R.id.sm_nav_my_reports){
            startActivity(new Intent(HomeActivity.this, ReportList.class));
        }
        else if(id == R.id.sm_nav_quick_health_checkups){
            startActivity(new Intent(HomeActivity.this, QuickHealthCheckupsActivity.class));
        }
        else if (id == R.id.sm_nav_settings){
            startActivity(new Intent(HomeActivity.this, UpdateUserAccountActivity.class));
        }
        else if (id == R.id.sm_nav_logout){
            Paper.book().destroy();

            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}