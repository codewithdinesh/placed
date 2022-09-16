package com.tplacement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.tplacement.admin.AdminActivity;
import com.tplacement.admin.account.LoginAdminActivity;

public class splashActivity extends AppCompatActivity {
    MaterialButton admin, user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        user = findViewById(R.id.user);
        admin = findViewById(R.id.admin);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(MainActivity.class);
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(AdminActivity.class);
            }
        });
    }

    public void openActivity(Class c) {
        Intent i = new Intent(getApplicationContext(), c);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }
}