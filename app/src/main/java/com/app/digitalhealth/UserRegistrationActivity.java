package com.app.digitalhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UserRegistrationActivity extends AppCompatActivity {

    private TextInputEditText name;
    private TextInputEditText phone;
    private TextInputEditText password;
    private TextInputEditText reEnterPassword;
    private Button createAccount;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        name = findViewById(R.id.sm_user_registration_name_value);
        phone = findViewById(R.id.sm_user_registration_phone_value);
        password = findViewById(R.id.sm_user_registration_password_value);
        reEnterPassword = findViewById(R.id.sm_user_registration_re_enter_password_value);
        createAccount = findViewById(R.id.sm_user_registration_btn);

        pd = new ProgressDialog(this);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

    }

    private void createAccount() {
        String txtName = name.getText().toString();
        String txtPhone = phone.getText().toString();
        String txtPassword = password.getText().toString();
        String txtReEnterPassword = reEnterPassword.getText().toString();

        if(TextUtils.isEmpty(txtName)){
            Toast.makeText(this, "Name cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(txtPhone)){
            Toast.makeText(this, "Phone cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(txtPassword)){
            Toast.makeText(this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(txtReEnterPassword)){
            Toast.makeText(this, "Re-enter password cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if (txtPhone.length() != 10){
            Toast.makeText(this, "Phone number must contain 10 digits.", Toast.LENGTH_SHORT).show();
        }
        else if (txtPassword.length() < 4){
            Toast.makeText(this, "Password must contain at least 4 characters.", Toast.LENGTH_SHORT).show();
        }
        else if(!txtPassword.equals(txtReEnterPassword)){
            Toast.makeText(this, "Passwords are not matching.", Toast.LENGTH_SHORT).show();
        }
        else {
            pd.setMessage("Creating new account.");
            pd.setCanceledOnTouchOutside(false);
            pd.show();

            validateCredentials(txtName, txtPhone, txtPassword);
        }

    }

    private void validateCredentials(final String name, final String phone, final String password) {
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phone).exists())){
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("phone" , phone);
                    map.put("name" , name);
                    map.put("password" , password);

                    rootRef.child("Users").child(phone).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                pd.dismiss();
                                Toast.makeText(UserRegistrationActivity.this, "Thank you for joining with us.", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(UserRegistrationActivity.this, LoginActivity.class));
                                finish();
                                Toast.makeText(UserRegistrationActivity.this, "Please login.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                pd.dismiss();
                                Toast.makeText(UserRegistrationActivity.this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else {
                    pd.dismiss();
                    Toast.makeText(UserRegistrationActivity.this, "Account with same phone number is already exists.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(UserRegistrationActivity.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}