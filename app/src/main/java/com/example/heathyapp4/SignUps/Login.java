package com.example.heathyapp4.SignUps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heathyapp4.Home.HomeActivity;
import com.example.heathyapp4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText inputEmailLogin;
   EditText  inputPasswordLogin;
    Button loginButton;
    TextView textYouDontHaveAccount;
    String emailPattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
    private ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        inputEmailLogin = findViewById(R.id.loginEmail);
        inputPasswordLogin = findViewById(R.id.loginPassword);
       loginButton = findViewById(R.id.loginButton);
       textYouDontHaveAccount = findViewById(R.id.signUpText);



       progressDialog = new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

       loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                perforLogin();
           }
        });

       textYouDontHaveAccount.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Login.this, SignUp.class);
               //this line delete this activity you cant return to this page \|/
              // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(intent);
           }
       });
    }

   private void perforLogin() {


       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String email = inputEmailLogin.getText().toString();
       String password = inputPasswordLogin.getText().toString();

        if(!email.matches(emailPattern))
        {
            inputEmailLogin.setError("Email is invalid");

        }
        else if(password.isEmpty() || password.length()<6)
        {
            inputPasswordLogin.setError("password Can't be less than 6");
        }
        else {
            progressDialog.setMessage("Please Wait While Login ....");
            progressDialog.setTitle("Login");
           progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                   {
                        progressDialog.dismiss();
                        sendUserToNextPage();
                       Toast.makeText(Login.this , "Login Successful" + task.getException(), Toast.LENGTH_SHORT).show();
                   }
                    else {

                        progressDialog.dismiss();
                        Toast.makeText(Login.this , "Wrong Email or Password"/*+ task.getException()*/ , Toast.LENGTH_SHORT).show();

                    }
                }
           });
        }
    }
    private void sendUserToNextPage() {
        Intent intent = new Intent(Login.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}