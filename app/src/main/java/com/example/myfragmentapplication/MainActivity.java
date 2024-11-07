package com.example.myfragmentapplication;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private int bottomPosition;

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        bottomPosition = sharedPreferences.getInt("bottomPosition", 0);
        if (bottomPosition == 0) {
            replaceFragment(new HomeFragment());
        } else if (bottomPosition == 1) {
            replaceFragment(new SearchFragment());
        } else if (bottomPosition == 2) {
            replaceFragment(new LoginFragment());
        } else if (bottomPosition == 3) {
            replaceFragment(new WebFragment());
        } else if (bottomPosition == 4) {
            replaceFragment(new ExitFragment());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //replaceFragment(new HomeFragment());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_home) {
                bottomPosition = 0;
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.nav_search) {
                bottomPosition = 1;
                replaceFragment(new SearchFragment());
            } else if (item.getItemId() == R.id.nav_login) {
                bottomPosition = 2;
                replaceFragment(new LoginFragment());
            } else if (item.getItemId() == R.id.nav_web) {
                bottomPosition = 3;
                replaceFragment(new WebFragment());
            } else if (item.getItemId() == R.id.nav_exit) {
                bottomPosition = 4;
                replaceFragment(new ExitFragment());
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("bottomPosition", bottomPosition);
        myEdit.apply();
    }
}