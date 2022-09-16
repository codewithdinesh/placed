package com.tplacement.placement;

import static android.view.View.GONE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tplacement.R;
import com.tplacement.config.config;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PlacementActivity extends AppCompatActivity {
    TextView name, desc, date, org, org_desc, org_location, hr, skills, roles, education, experience;
    String str_name, str_desc, str_date, str_org, str_org_desc, str_org_location, str_hr, str_skills, str_roles, str_id, str_education, str_experience;
    Button Apply_Btn;
    TextView showError;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences, sharedPreferences2;
    String login_token, login_token_admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement);

        name = findViewById(R.id.name);
        desc = findViewById(R.id.desc);
        date = findViewById(R.id.date);
        org = findViewById(R.id.org);
        org_desc = findViewById(R.id.org_desc);
        org_location = findViewById(R.id.org_location);
        hr = findViewById(R.id.org_hr);
        skills = findViewById(R.id.skills);
        roles = findViewById(R.id.roles);
        Apply_Btn = findViewById(R.id.apply);
        education = findViewById(R.id.education);
        experience = findViewById(R.id.experience);
        showError = findViewById(R.id.showError);
        progressBar = findViewById(R.id.progressBar);


        Intent dashboard = getIntent();

        try {
            // Getting values
            str_name = dashboard.getStringExtra("name");
            str_desc = dashboard.getStringExtra("desc");
            str_org_desc = dashboard.getStringExtra("org_desc");
            str_date = dashboard.getStringExtra("date");
            str_org = dashboard.getStringExtra("org");
            str_hr = dashboard.getStringExtra("hr");
            str_skills = dashboard.getStringExtra("skills");
            str_roles = dashboard.getStringExtra("roles");
            str_org_location = dashboard.getStringExtra("org_location");
            str_id = dashboard.getStringExtra("id");
            str_education = dashboard.getStringExtra("education");
            str_experience = dashboard.getStringExtra("experience");

            name.setText(str_name);
            org.setText(str_org);
            org_location.setText(str_org_location);
            org_desc.setText(str_org_desc);
            desc.setText(str_desc);
            hr.setText(str_hr);
            roles.setText(str_roles);
            skills.setText(str_skills);
            date.setText(str_date);
            education.setText(str_education);
            experience.setText(str_experience);
        } catch (Exception e) {
            displayError("Something went wrong", 150000);
        }

        sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);
        login_token = sharedPreferences.getString("login_token", "");

        sharedPreferences2 = getSharedPreferences("adminDetails", MODE_PRIVATE);
        login_token_admin = sharedPreferences2.getString("login_token", "");

        Apply_Btn.setVisibility(GONE);

        if (login_token.trim().isEmpty()) {

            Apply_Btn.setVisibility(GONE);
            if (login_token_admin.trim().isEmpty()) {
                displayError("Login to Apply ", 1500000);
            }
            displayError("Login as user to Apply ", 1500000);

        } else {
            if (login_token_admin.trim().isEmpty()) {
                Apply_Btn.setVisibility(View.VISIBLE);
            }
        }

        Apply_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyPlacement(str_id);
            }
        });

    }

    public void applyPlacement(String id) {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        progressBar.setVisibility(View.VISIBLE);

        // BASE URL
        String url = new config().getBASE_URL() + "/apply";


        sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);

        login_token = sharedPreferences.getString("login_token", "");

        //body parts
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("placement_id", id);

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(GONE);
                displayError("Your application are submitted successfully", 150000);
                Toast.makeText(getApplicationContext(), "Your application are submitted successfully", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(GONE);

                int statusCode = error.networkResponse.statusCode;

                if (error instanceof NoConnectionError) {
                    displayError("No Internet, Please try again..", 150000);
                }
                if (error instanceof TimeoutError) {
                    displayError("Server Down, Please try again..", 150000);
                }

                switch (statusCode) {
                    case 400:
                        displayError("Error while fetching application", 150000);
                        break;
                    case 402:
                        displayError("your application is already applied", 150000);
                        break;

                    case 401:
                        displayError("Error while fetching Profile", 150000);

                    case 404:
                        displayError("Please Login to Apply placement", 150000);
                        break;

                    case 403:
                        displayError("could not found user profile data , please create profile and then apply or login please", 100000);
                        break;

                    default:
                        displayError("Something went wrong,please try again letter", 150000);
                        break;
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("login_token", login_token);
                params.put("User-Agent", "Mozilla/5.0");
                return params;
            }

        };
        queue.add(loginRequest);
    }

    public void displayError(String msg, int time) {
        showError.setVisibility(GONE);
        showError.setText(msg);
        showError.setVisibility(View.VISIBLE);
        showError.postDelayed(new Runnable() {
            public void run() {
                showError.setVisibility(GONE);
            }
        }, time);
    }
}