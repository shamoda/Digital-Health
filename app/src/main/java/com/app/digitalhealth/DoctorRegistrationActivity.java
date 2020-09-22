package com.app.digitalhealth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.common.util.Strings;
import com.app.digitalhealth.model.Doctor;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DoctorRegistrationActivity extends AppCompatActivity {

    TextInputLayout textInputLayout;
    AutoCompleteTextView dropDown;

    private String txtName;
    private String txtAddress;
    private String txtPhone;
    private String txtNic;
    private String txtSlmcRegNo;
    private String txtSpecialization;
    private String dId;
    private String saveCurrentTime;
    private String doctorImageRandomKey;


    private TextView close;
    private ImageView doctorImage;
    private TextInputEditText name;
    private TextInputEditText address;
    private TextInputEditText phone;
    private TextInputEditText nic;
    private TextInputEditText slmcRegNo;
    private Button addDoctorBtn;

    private static final int galleryPick = 1;
    private Uri imageUri;
    private StorageReference doctorImageRef;
    private String downloadImageUrl;
    private DatabaseReference doctorRef;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        dId = getIntent().getStringExtra("dId");

        close = findViewById(R.id.sm_doctor_registration_close_btn);
        doctorImage = findViewById(R.id.sm_doctor_registration_profile_img);
        name = findViewById(R.id.sm_doctor_registration_name_value);
        address = findViewById(R.id.sm_doctor_registration_address_value);
        phone = findViewById(R.id.sm_doctor_registration_phone_value);
        nic = findViewById(R.id.sm_doctor_registration_nic_value);
        slmcRegNo = findViewById(R.id.sm_doctor_registration_slmc_reg_no_value);
        addDoctorBtn = findViewById(R.id.sm_doctor_registration_add_doctor_btn);

        doctorImageRef = FirebaseStorage.getInstance().getReference().child("DoctorsImages");
        doctorRef = FirebaseDatabase.getInstance().getReference().child("Doctors");
        pd = new ProgressDialog(this);

        textInputLayout = findViewById(R.id.sm_doctor_registration_specialization);
        dropDown = findViewById(R.id.sm_doctor_registration_specialization_value);

        String[] specialization = new String[]{
                "Allergists",
                "Anesthesiologist",
                "Cardiologist",
                "Colon and Rectal Surgeon",
                "Dermatologist",
                "Endocrinologist",
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
                DoctorRegistrationActivity.this,
                R.layout.drop_down_item,
                specialization
        );
        dropDown.setAdapter(adapter);

        if(dId != null){
            loadData(dId);
            addDoctorBtn.setText("Update");
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorRegistrationActivity.this, ManageDoctorsActivity.class));
                finish();
            }
        });

        doctorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        
        addDoctorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }

    private void loadData(String dId) {
        doctorRef.child(dId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Doctor doc = dataSnapshot.getValue(Doctor.class);
                Picasso.get().load(doc.getImage()).into(doctorImage);
                name.setText(doc.getName());
                dropDown.setText(doc.getSpecialization());
                address.setText(doc.getAddress());
                phone.setText(doc.getPhone());
                nic.setText(doc.getNic());
                slmcRegNo.setText(doc.getSlmcRegNo());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void validateData() {
        txtName = name.getText().toString();
        txtAddress = address.getText().toString();
        txtPhone = phone.getText().toString();
        txtNic = nic.getText().toString();
        txtSlmcRegNo = slmcRegNo.getText().toString();
        txtSpecialization = dropDown.getText().toString();

        if(imageUri == null){
            Toast.makeText(this, "Doctor's profile image is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(txtName)){
            Toast.makeText(this, "Name cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(txtSpecialization)){
            Toast.makeText(this, "Specialization cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(txtAddress)){
            Toast.makeText(this, "Address cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(txtPhone)){
            Toast.makeText(this, "Phone cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(txtNic)){
            Toast.makeText(this, "NIC cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(txtSlmcRegNo)){
            Toast.makeText(this, "SLMC Registration Number cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if (txtPhone.length() != 10){
            Toast.makeText(this, "Phone number must contain 10 digits.", Toast.LENGTH_SHORT).show();
        }
        else {
            storeDoctorInfo();
        }

    }

    private void storeDoctorInfo() {

        if (dId != null){
            pd.setMessage("Updating...");
        }
        else {
            pd.setMessage("Registering new doctor");
        }
        pd.setCanceledOnTouchOutside(false);
        pd.show();

//        Calendar calendar = Calendar.getInstance();
//
//        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd yyyy");
//        saveCurrentDate = currentDate.format(calendar.getTime());
//
//        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
//        saveCurrentTime = currentTime.format(calendar.getTime());
//
//        doctorImageRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = doctorImageRef.child(txtPhone + ".jpg");

        final UploadTask uploadTask = filePath.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(DoctorRegistrationActivity.this, "Error: " +e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(DoctorRegistrationActivity.this, "Profile image uploaded successfully.", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful()){
                            downloadImageUrl = task.getResult().toString();
//                            Toast.makeText(DoctorRegistrationActivity.this, "Profile image download URL taken successfully.", Toast.LENGTH_SHORT).show();

                            saveDoctorInfo();
                        }
                    }
                });
            }
        });
    }

    private void saveDoctorInfo() {
        HashMap<String, Object> doctorMap = new HashMap<>();
        doctorMap.put("name", txtName);
        doctorMap.put("phone", txtPhone);
        doctorMap.put("specialization", txtSpecialization);
        doctorMap.put("nic", txtNic);
        doctorMap.put("address", txtAddress);
        doctorMap.put("slmcRegNo", txtSlmcRegNo);
        doctorMap.put("image", downloadImageUrl);

        doctorRef.child(txtPhone).updateChildren(doctorMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    pd.dismiss();
                    if (dId != null){
                        Toast.makeText(DoctorRegistrationActivity.this, "Doctor details updated successfully.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(DoctorRegistrationActivity.this, "Doctor registration successful.", Toast.LENGTH_SHORT).show();
                    }

                    startActivity(new Intent(DoctorRegistrationActivity.this, ManageDoctorsActivity.class));
                    finish();
                }
                else {
                    pd.dismiss();
                    Toast.makeText(DoctorRegistrationActivity.this, "Error: "+task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, galleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == galleryPick && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            doctorImage.setImageURI(imageUri);
        }
    }
}