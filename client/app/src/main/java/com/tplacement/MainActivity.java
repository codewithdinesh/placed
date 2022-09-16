package com.tplacement;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tplacement.home.AccountFragment;
import com.tplacement.home.Applied_placement_Fragment;
import com.tplacement.home.dashboardFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        dashboardFragment dashboardFragment = new dashboardFragment();
        AccountFragment accountFragment = new AccountFragment();
        Applied_placement_Fragment appliedPlacementFragment = new Applied_placement_Fragment();
        bottomNavigationView.setItemPaddingBottom(5);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, dashboardFragment).commit();
                        return true;

                    case R.id.explore:
                        MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, appliedPlacementFragment).commit();
                        return true;

                    case R.id.account:
                        MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer, accountFragment).commit();
                        return true;
                }
                return false;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.home);
    }
}