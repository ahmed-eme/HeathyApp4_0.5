package com.example.heathyapp4.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.heathyapp4.AddItemActivite;
import com.example.heathyapp4.Cart.MyCart;
import com.example.heathyapp4.DrawerPages.CategoriesDrawer;
import com.example.heathyapp4.Favorite.favFragment;
import com.example.heathyapp4.R;
import com.example.heathyapp4.YourOrderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
            case R.id.cart:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,myCaty).commit();
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
    MyCart myCaty = new MyCart();

    /*****************Drawer variables*********************/

    DrawerLayout drawerlayout;
    NavigationView navigationView;
    CategoriesDrawer categoriesDrawer = new CategoriesDrawer();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // get user info

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.healthy_icon);



        drawerlayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // this line set the drawer item selectable
        navigationView.bringToFront();



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
                    case R.id.menu:
                        drawerlayout.openDrawer(Gravity.RIGHT);
                        return true;
                }
                return false;
            }
        });



    }

    @Override
    public void onBackPressed() {
        if (drawerlayout.isDrawerOpen(Gravity.RIGHT))
        {
            drawerlayout.closeDrawer(Gravity.RIGHT);
        }
        else if(!homeFragment.isVisible())
        {
            drawerlayout.closeDrawer(Gravity.RIGHT);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
        }
        else
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displayView(item.getItemId());
        return true;
    }

    public void displayView(int viewId) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {
            case R.id.nav_categories:
                fragment = new CategoriesDrawer();
                title  = "News";

                break;
           /* case R.id.nav_events:
                fragment = new EventsFragment();
                title = "Events";
                break;*/

        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
        drawerlayout.closeDrawer(Gravity.RIGHT);

    }
}