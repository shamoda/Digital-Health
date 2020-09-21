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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.digitalhealth.prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateUserAccountActivity extends AppCompatActivity {

    private TextView closeBtn;
    private TextView updateBtn;
    private TextView changeProfileImageBtn;
    private CircleImageView profileImage;
    private TextInputEditText name;
    private TextInputEditText phone;
    private TextInputEditText address;
    private TextInputEditText password;
    private TextInputEditText reEnterPassword;
    private Button deleteAccountButton;

    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference imageRef;
    private String checker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_account);

        closeBtn = findViewById(R.id.sm_update_user_close_btn);
        updateBtn = findViewById(R.id.sm_update_user_update_btn);
        changeProfileImageBtn = findViewById(R.id.sm_update_user_change_profile_img_btn);
        profileImage = findViewById(R.id.sm_update_user_profile_img);
        name = findViewById(R.id.sm_update_user_name_value);
        phone = findViewById(R.id.sm_update_user_phone_value);
        address = findViewById(R.id.sm_update_user_address_value);
        password = findViewById(R.id.sm_update_user_password_value);
        reEnterPassword = findViewById(R.id.sm_update_user_re_enter_password_value);
        deleteAccountButton = findViewById(R.id.sm_update_user_delete_account_btn);

        imageRef = FirebaseStorage.getInstance().getReference().child("UsersImages");

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateUserAccountActivity.this, HomeActivity.class));
                finish();
            }
        });

        userInfoDisplay(profileImage, name, phone, address, password);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(password.getText().toString().equals(reEnterPassword.getText().toString())){
                    if(checker.equals("clicked")){
                        userInfoSaved();
                    }
                    else {
                        updateOnlyUserInfo();
                    }
                }
                else {
                    Toast.makeText(UpdateUserAccountActivity.this, "Passwords are not matching.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        changeProfileImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(UpdateUserAccountActivity.this);
            }
        });
    }


    private void updateOnlyUserInfo() {

        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Updating...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name.getText().toString());
        map.put("password", password.getText().toString());
        map.put("phone", phone.getText().toString());
        map.put("address", address.getText().toString());

        ref.child(Prevalent.currentUser.getPhone()).updateChildren(map);

        pd.dismiss();
        Toast.makeText(UpdateUserAccountActivity.this, "Profile updated successfully.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(UpdateUserAccountActivity.this, HomeActivity.class));
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImage.setImageURI(imageUri);
        }
        else {
            Toast.makeText(this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UpdateUserAccountActivity.this, UpdateUserAccountActivity.class));
            finish();
        }
    }

    private void userInfoSaved() {
        if(TextUtils.isEmpty(name.getText().toString())){
            Toast.makeText(this, "Name cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone.getText().toString())){
            Toast.makeText(this, "Phone cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(address.getText().toString())){
            Toast.makeText(this, "Address cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password.getText().toString())){
            Toast.makeText(this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(reEnterPassword.getText().toString())){
            Toast.makeText(this, "Re-enter Password cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if(checker.equals("clicked")){
            uploadImage();
        }
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Updating...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        if(imageUri != null){
            final StorageReference fileRef = imageRef.child(Prevalent.currentUser.getPhone()+".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){

                        Uri downloadUrl = task.getResult();
                        myUrl = downloadUrl.toString();

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("password", password.getText().toString());
                        map.put("phone", phone.getText().toString());
                        map.put("address", address.getText().toString());
                        map.put("profileImage", myUrl);

                        ref.child(Prevalent.currentUser.getPhone()).updateChildren(map);

                        pd.dismiss();
                        Toast.makeText(UpdateUserAccountActivity.this, "Profile updated successfully.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UpdateUserAccountActivity.this, HomeActivity.class));
                        finish();
                    }
                    else {
                        pd.dismiss();
                        Toast.makeText(UpdateUserAccountActivity.this, "Error: Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(this, "Please select a image to proceed.", Toast.LENGTH_SHORT).show();
        }
    }


    private void userInfoDisplay(final CircleImageView profileImage, final TextInputEditText name, final TextInputEditText phone, final TextInputEditText address, final TextInputEditText password) {

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentUser.getPhone());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if (dataSnapshot.child("profileImage").exists()){
                        String tempImage = dataSnapshot.child("profileImage").getValue().toString();
                        String tempName = dataSnapshot.child("name").getValue().toString();
                        String tempPassword = dataSnapshot.child("password").getValue().toString();
                        String tempPhone = dataSnapshot.child("phone").getValue().toString();
                        String tempAddress = dataSnapshot.child("address").getValue().toString();

                        Picasso.get().load(tempImage).into(profileImage);
                        name.setText(tempName);
                        phone.setText(tempPhone);
                        address.setText(tempAddress);
                        password.setText(tempPassword);
                        reEnterPassword.setText(tempPassword);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}