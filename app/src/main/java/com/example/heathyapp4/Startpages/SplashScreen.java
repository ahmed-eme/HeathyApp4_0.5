package com.example.heathyapp4.Startpages;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.heathyapp4.MainActivity;
import com.example.heathyapp4.R;


public class SplashScreen extends AppCompatActivity {
   Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this , MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1500);


    }
}