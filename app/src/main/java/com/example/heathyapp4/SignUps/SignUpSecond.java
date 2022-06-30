package com.example.heathyapp4.SignUps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.heathyapp4.Home.HomeActivity;
import com.example.heathyapp4.Model;
import com.example.heathyapp4.R;
import com.example.heathyapp4.User.DAOUser;
import com.example.heathyapp4.User.UserInfoClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;


public class SignUpSecond extends AppCompatActivity {


    private EditText inputCity, inputDistrict, inputStreetName, inputBuildingNumber;
    private Button sendinfo;
    private ImageView avatarImage;


    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;



    private StorageReference reference = FirebaseStorage.getInstance().getReference("profileImage");

    private Uri imageUri;


    UserInfoClass userInfoClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_second);
        getSupportActionBar().hide();



        inputCity = findViewById(R.id.city);

            inputDistrict = findViewById(R.id.District);
            inputStreetName = findViewById(R.id.StreetName);

            inputBuildingNumber = findViewById(R.id.buildingName);



            sendinfo = findViewById(R.id.sendInfo);

            avatarImage = findViewById(R.id.avatarImage);

            progressDialog = new ProgressDialog(this);
            mAuth = FirebaseAuth.getInstance();
            mUser = mAuth.getCurrentUser();

            /************-------- IMAGE CLICK ------------------------*/

        avatarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , 2);
            }
        });






            /**********************************************/


            firebaseDatabase = FirebaseDatabase.getInstance();


            userInfoClass = new UserInfoClass();

            /********************** Get value from first SignUp *********************/
             Bundle bundle=getIntent().getExtras();
             String phoneNumber=bundle.getString("phone");
          /*   String crnumber =bundle.getString("crnumber");
             String companyname= bundle.getString("companyname");
             String email = bundle.getString("email");*/


             /*************************************************************/

            sendinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String city = inputCity.getText().toString();
                    String district = inputDistrict.getText().toString();
                    String street = inputStreetName.getText().toString();
                    String buildName = inputBuildingNumber.getText().toString();


                    /**********************************************************/

                    DAOUser user = new DAOUser();

                /*    UserInfoClass userclas = new UserInfoClass(crnumber, companyname, phoneNumber, city, district, street, buildName, email);*/

                    HashMap<String, Object> hashMap = new HashMap<String, Object>();
                    hashMap.put("city" , city);
                    hashMap.put("buildingNumber" , buildName);
                    hashMap.put("streetName" , street);
                    hashMap.put("district" , district);
                    user.update(hashMap);

                    /**************************************************************/
 /*
                   userInfoClass.setCity(city);
                    userInfoClass.setDistrict(district);
                    userInfoClass.setStreetName(street);
                    userInfoClass.setBuildingNumber(buildName);
                    userInfoClass.setPhone(phoneNumber);
                    userInfoClass.setCrNumber(crnumber);
                    userInfoClass.setCompanyName(companyname);
                    userInfoClass.setEmail(email);}*/




                    uploadToFirebase(imageUri , phoneNumber);

                       // databaseReference = firebaseDatabase.getReference("User").child(phoneNumber).child("UserInfo");

                      //  databaseReference.setValue(userInfoClass);

                        progressDialog.setMessage("Please Wait While SignUp ....");
                        progressDialog.setTitle("SignUp");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        sendUserToNextPage();



                }
            });



        }
        /****************************************Change image to new one that user upload*****************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            avatarImage.setImageURI(imageUri);

        }
    }

    /****************************************** upload image *************************************/


    private void uploadToFirebase(Uri uri , String phoneNumber){

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        databaseReference = firebaseDatabase.getReference("User").child(phoneNumber).child("UserInfo");
                        Model model = new Model(uri.toString());
                       // String modelId = databaseReference.push().getKey();
                        databaseReference.child("avatar").setValue(model);
                        // progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(SignUpSecond.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        avatarImage.setImageResource(R.drawable.avatar);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                //   progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(SignUpSecond.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

    /******************************************* Send user to next page ************************************************/


        private void sendUserToNextPage () {
            Intent intent = new Intent(SignUpSecond.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


    }
