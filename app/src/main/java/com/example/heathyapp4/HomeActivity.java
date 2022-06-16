package com.example.heathyapp4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_bar , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.fav:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,favfragment).commit();
                return true;
            case R.id.notification:
                Intent intent = new Intent(HomeActivity.this, AddItemActivite.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    YourOrderFragment yourOrderFragment = new YourOrderFragment();
    ReportFragment reportFragment = new ReportFragment();
    ExpiredFragment expiredFragment = new ExpiredFragment();
    favFragment favfragment = new favFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get user info




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.healthy_icon);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.YourOrder:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,yourOrderFragment).commit();
                        return true;
                    case R.id.report:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,reportFragment).commit();
                        return true;
                    case R.id.expired:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,expiredFragment).commit();
                        return true;
                }

                return false;
            }
        });
    }


}