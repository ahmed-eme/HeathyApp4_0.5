package com.example.heathyapp4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.heathyapp4.User.DAOUser;
import com.example.heathyapp4.User.UserInfoClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    private  EditText inputCrNumber, inputCompanyName , inputEmailAddress
    ,inputPhone ,inputPassword , inputRePassword;
    private Button signUpButton;
    private  String emailPattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
    CheckBox checkBox;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private boolean checkboxState = false;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;



    UserInfoClass userInfoClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();



        inputEmailAddress = findViewById(R.id.emailAddress);

         inputPassword = findViewById(R.id.password);
         inputRePassword = findViewById(R.id.repassword);

        signUpButton = findViewById(R.id.signUpButton);
        checkBox = findViewById(R.id.checkBox3);



        progressDialog = new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        /**********************************************/

       inputCrNumber = findViewById(R.id.CRNumber);
        inputCompanyName = findViewById(R.id.companyName);
        inputPhone = findViewById(R.id.phone);

        firebaseDatabase =FirebaseDatabase.getInstance();


        userInfoClass = new UserInfoClass();

        /*******************************************/


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String crnumber = inputCrNumber.getText().toString();
                String companyname = inputCompanyName.getText().toString();
                String phonenumber = inputPhone.getText().toString();
                databaseReference = firebaseDatabase.getReference("User").child(phonenumber).child("UserInfo");


                perforAuth(crnumber, companyname, phonenumber );



            }
        });



    }





    private void perforAuth(String crnumber, String companyname, String phonenumber ) {

     String email = inputEmailAddress.getText().toString();
     String password = inputPassword.getText().toString();
     String rePassword = inputRePassword.getText().toString();

        if (checkBox.isChecked())
        {
            checkboxState = true;
        } else {
            checkboxState = false;
        }


     if(!email.matches(emailPattern))
     {
         inputEmailAddress.setError("Email is invalid");

     }
     else if(password.isEmpty() || password.length()<6)
     {
         inputPassword.setError("password Can't be less than 6");
     }
     else if (!password.equals(rePassword))
     {
         inputRePassword.setError("Password Not match Both field");
     }
     else if (phonenumber.isEmpty())
     {
         inputPassword.setError("Enter your phone number");
     }
     else if (!checkboxState)
     {
         checkBox.setError("");
     }
     else {
      progressDialog.setMessage("Please Wait While SignUp ....");
      progressDialog.setTitle("SignUp");
      progressDialog.setCanceledOnTouchOutside(false);
      progressDialog.show();


      mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful())
              {
                  /************************************************/

                  DAOUser user = new DAOUser();

                  UserInfoClass userclas = new UserInfoClass(crnumber, companyname, phonenumber, "", "", "", "", email);

                  user.add(userclas);



                 /* userInfoClass.setCrNumber(crnumber);
                  userInfoClass.setCompanyName(companyname);
                  userInfoClass.setPhone("+966"+phonenumber);


                  databaseReference.setValue(userInfoClass);*/



                  /*************************************************/
                  progressDialog.dismiss();

                  /***************************Send user to next page and pass value***************************/
                  Intent intent = new Intent(SignUp.this, SignUpSecond.class);
                  intent.putExtra("phone",phonenumber);
                  intent.putExtra("crnumber" , crnumber);
                  intent.putExtra("companyname", companyname);
                  intent.putExtra("email" , email);
                  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                  startActivity(intent);

                //  sendUserToNextPage();

                  Toast.makeText(SignUp.this , "Sign Successful" + task.getException(), Toast.LENGTH_SHORT).show();
              }
              else {
                  progressDialog.dismiss();
                  Toast.makeText(SignUp.this , ""+ task.getException() , Toast.LENGTH_SHORT).show();
              }
          }
      });
     }


    }



    private void sendUserToNextPage() {
        Intent intent = new Intent(SignUp.this, SignUpSecond.class);
        intent.putExtra("phone",inputPhone.toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}