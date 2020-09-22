package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.digitalhealth.model.Users;
import com.app.digitalhealth.prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

import static android.Manifest.permission.CALL_PHONE;

public class MainActivity extends AppCompatActivity {

    private Button callAnAmbulance;
    private Button login;
    private Button createAccount;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callAnAmbulance = findViewById(R.id.sm_welcome_call_an_ambulance);
        login = findViewById(R.id.sm_welcome_login);
        createAccount = findViewById(R.id.sm_welcome_create_account);
        pd = new ProgressDialog(this);

        Paper.init(this);

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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserRegistrationActivity.class);
                startActivity(intent);
            }
        });


//        check whether users is already logged in or not
        String userPhoneKey = Paper.book().read(Prevalent.userPhoneKey);
        String userPasswordKey = Paper.book().read(Prevalent.userPasswordKey);

        if (userPhoneKey != "" && userPasswordKey != ""){
            if (!TextUtils.isEmpty(userPhoneKey) && !TextUtils.isEmpty(userPasswordKey)){
                pd.setMessage("Bringing to your account.");
                pd.setCanceledOnTouchOutside(false);
                pd.show();

                logUserAutomatically(userPhoneKey, userPasswordKey);
            }
        }
    }

    private void logUserAutomatically(final String phone, final String password) {

        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(phone).exists()){
                    Users user = dataSnapshot.child("Users").child(phone).getValue(Users.class);

                    if(user.getPhone().equals(phone)){
                        if (user.getPassword().equals(password)){
//                            Toast.makeText(MainActivity.this, "Logged in successfully.", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                            Prevalent.currentUser = user;
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Incorrect password.", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                    else {
//                        this part is not needed. because we have already checked the phone number is already exists or not
                        Toast.makeText(MainActivity.this, "There is no such user with "+phone+" number.", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "There is no such user with "+phone+" number.", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}