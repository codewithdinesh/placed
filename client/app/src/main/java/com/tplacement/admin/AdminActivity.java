package com.tplacement.admin;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tplacement.R;
import com.tplacement.admin.account.adminAccountFragment;

public class AdminActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;
    String login_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        AdminDashBoardFragment dashboardFragment = new AdminDashBoardFragment();
        adminAccountFragment accountFragment = new adminAccountFragment();

        bottomNavigationView.setItemPaddingBottom(5);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        AdminActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, dashboardFragment).commit();
                        return true;

                    case R.id.account:
                        AdminActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, accountFragment).commit();
                        return true;
                }
                return false;
            }
        });


        bottomNavigationView.setSelectedItemId(R.id.home);
    }

}