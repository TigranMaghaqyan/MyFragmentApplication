package com.example.myfragmentapplication;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPreferences";
    private static final String KEY_BOTTOM_BAR_POSITION = "bottom_bar_position";
    SharedPreferences settings;
    SharedPreferences.Editor prefEditor;
    private OnBackPressedCallback onBackPressedCallback;


    @Override
    protected void onStart() {
        super.onStart();
        prefEditor = settings.edit();
        prefEditor.putInt(KEY_BOTTOM_BAR_POSITION, settings.getInt(KEY_BOTTOM_BAR_POSITION,0));
        prefEditor.commit();
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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int position = settings.getInt(KEY_BOTTOM_BAR_POSITION,0);

        if (position==0){
            replaceFragment(new HomeFragment());
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        } else if (position==1) {
            replaceFragment(new SearchFragment());
            bottomNavigationView.setSelectedItemId(R.id.nav_search);
        }else if (position==2){
            bottomNavigationView.setSelectedItemId(R.id.nav_login);
            replaceFragment(new LoginFragment());
        } else if (position==3) {
            bottomNavigationView.setSelectedItemId(R.id.nav_web);
            replaceFragment(new WebFragment());
        } else if (position==4) {
            bottomNavigationView.setSelectedItemId(R.id.nav_exit);
            replaceFragment(new ExitFragment());
        }


        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_home) {
                prefEditor = settings.edit();
                prefEditor.putInt(KEY_BOTTOM_BAR_POSITION, 0);
                prefEditor.commit();
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.nav_search) {
                prefEditor = settings.edit();
                prefEditor.putInt(KEY_BOTTOM_BAR_POSITION, 1);
                prefEditor.commit();
                replaceFragment(new SearchFragment());
            } else if (item.getItemId() == R.id.nav_login) {
                prefEditor = settings.edit();
                prefEditor.putInt(KEY_BOTTOM_BAR_POSITION, 2);
                prefEditor.commit();
                replaceFragment(new LoginFragment());
            } else if (item.getItemId() == R.id.nav_web) {
                prefEditor = settings.edit();
                prefEditor.putInt(KEY_BOTTOM_BAR_POSITION, 3);
                prefEditor.commit();
                replaceFragment(new WebFragment());
            } else if (item.getItemId() == R.id.nav_exit) {
                prefEditor = settings.edit();
                prefEditor.putInt(KEY_BOTTOM_BAR_POSITION, 4);
                prefEditor.commit();
                replaceFragment(new ExitFragment());
            }
            return true;
        });

//        bottomNavigationView.setSelectedItemId(R.id.nav_web);

        onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Toast.makeText(MainActivity.this,"exit",Toast.LENGTH_SHORT).show();
                prefEditor = settings.edit();
                prefEditor.putInt(KEY_BOTTOM_BAR_POSITION, 0);
                prefEditor.commit();
                System.exit(1);

            }
        };

        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        prefEditor = settings.edit();
        prefEditor.putInt(KEY_BOTTOM_BAR_POSITION, settings.getInt(KEY_BOTTOM_BAR_POSITION,0));
        prefEditor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        prefEditor = settings.edit();
        prefEditor.putInt(KEY_BOTTOM_BAR_POSITION, settings.getInt(KEY_BOTTOM_BAR_POSITION,0));
//        prefEditor.clear().commit();
        prefEditor.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        prefEditor = settings.edit();
        prefEditor.putInt(KEY_BOTTOM_BAR_POSITION, settings.getInt(KEY_BOTTOM_BAR_POSITION,0));
        prefEditor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        prefEditor = settings.edit();
        prefEditor.putInt(KEY_BOTTOM_BAR_POSITION, settings.getInt(KEY_BOTTOM_BAR_POSITION,0));
        prefEditor.commit();

    }
}