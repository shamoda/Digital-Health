package com.app.digitalhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.app.digitalhealth.model.Users;
import com.app.digitalhealth.prevalent.Prevalent;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText phone;
    private TextInputEditText password;
    private Button login;
    private CheckBox rememberMe;
    private TextView adminPanel;
    private TextView notAdminPanel;

    private ProgressDialog pd;
    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone = findViewById(R.id.sm_login_phone_value);
        password = findViewById(R.id.sm_login_password_value);
        login = findViewById(R.id.sm_login_btn);
        rememberMe = findViewById(R.id.sm_login_remember_me);
        adminPanel = findViewById(R.id.sm_login_admin_panel);
        notAdminPanel = findViewById(R.id.sm_login_not_admin_panel);

//        initiallizing the Paper library
        Paper.init(this);

        pd = new ProgressDialog(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        adminPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setText("Login as an Admin");
                adminPanel.setVisibility(View.INVISIBLE);
                notAdminPanel.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });

        notAdminPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setText("Login");
                adminPanel.setVisibility(View.VISIBLE);
                notAdminPanel.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });

    }

    private void loginUser() {

        String txtPhone = phone.getText().toString();
        String txtPassword = password.getText().toString();

        if(TextUtils.isEmpty(txtPhone)){
            Toast.makeText(this, "Please enter phone number.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(txtPassword)){
            Toast.makeText(this, "Please enter password.", Toast.LENGTH_SHORT).show();
        }
        else {
            pd.setMessage("Please wait.");
            pd.setCanceledOnTouchOutside(false);
            pd.show();

            allowAccess(txtPhone, txtPassword);
        }

    }

    private void allowAccess(final String phone, final String password) {

        if(rememberMe.isChecked()){
            Paper.book().write(Prevalent.userPhoneKey, phone);
            Paper.book().write(Prevalent.userPasswordKey, password);
        }

        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(phone).exists()){
                    Users user = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if(user.getPhone().equals(phone)){
                        if (user.getPassword().equals(password)){
                            if(parentDbName.equals("Admins")){
                                Toast.makeText(LoginActivity.this, "Logged in as an Admin.", Toast.LENGTH_SHORT).show();
                                pd.dismiss();

                                startActivity(new Intent(LoginActivity.this, AdminDashboard.class));
                                finish();
                            }
                            else if (parentDbName.equals("Users")){
                                Toast.makeText(LoginActivity.this, "Logged in successfully.", Toast.LENGTH_SHORT).show();
                                pd.dismiss();

                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            }
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Incorrect password.", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                        }
                    }
                    else {
//                        this part is not needed. because we have already checked the phone number is already exists or not
                        Toast.makeText(LoginActivity.this, "There is no such user with "+phone+" number.", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "There is no such user with "+phone+" number.", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}