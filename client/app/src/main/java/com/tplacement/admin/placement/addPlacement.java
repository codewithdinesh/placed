package com.tplacement.admin.placement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.tplacement.R;
import com.tplacement.admin.AdminActivity;
import com.tplacement.config.config;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class addPlacement extends AppCompatActivity {

    TextInputEditText name, desc, org, org_type, org_location, org_Desc, skills, hr, roles, education, experience;
    MaterialButton addPlacementBtn;

    TextView showError;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    String login_token;
    LinearLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_placement);

        showError = findViewById(R.id.showError);
        progressBar = findViewById(R.id.progressBar);

        name = findViewById(R.id.name);
        desc = findViewById(R.id.desc);
        org = findViewById(R.id.org);
        org_type = findViewById(R.id.org_type);
        org_Desc = findViewById(R.id.org_desc);
        org_location = findViewById(R.id.org_location);
        skills = findViewById(R.id.skills);
        hr = findViewById(R.id.hr);
        roles = findViewById(R.id.roles);
        education = findViewById(R.id.education);
        experience = findViewById(R.id.experience);
        addPlacementBtn = findViewById(R.id.addPlacementBtn);
        container = findViewById(R.id.container);

        addPlacementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_name, str_desc, str_org, str_org_desc, str_org_location, str_hr, str_skills, str_roles, str_education, str_experience, str_org_type;

                str_name = name.getText().toString().trim();
                str_desc = desc.getText().toString().trim();
                str_org = org.getText().toString().trim();
                str_org_location = org_location.getText().toString().trim();
                str_org_desc = org_Desc.getText().toString().trim();
                str_hr = hr.getText().toString().trim();
                str_skills = skills.getText().toString().trim();
                str_roles = roles.getText().toString().trim();
                str_education = education.getText().toString().trim();
                str_experience = experience.getText().toString().trim();
                str_org_type = org_type.getText().toString().trim();

                addPlacement(str_name, str_desc, str_org, str_org_desc, str_org_type, str_org_location, str_hr, str_skills, str_roles, str_education, str_experience);
            }
        });

    }

    public void addPlacement(String name, String desc, String org, String org_Desc, String org_type, String org_location, String org_hr, String skills, String roles, String education, String experience) {
        RequestQueue queue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);

        // BASE URL
        String url = new config().getBASE_URL() + "/add_placement";

        sharedPreferences = getSharedPreferences("adminDetails", MODE_PRIVATE);

        login_token = sharedPreferences.getString("login_token", "");

        //body parts
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("desc", desc);
        params.put("org", org);
        params.put("skills", skills);
        params.put("experience", experience);
        params.put("education", education);
        params.put("org_location", org_location);
        params.put("org_type", org_type);
        params.put("org_desc", org_Desc);
        params.put("hr", org_hr);
        params.put("roles", roles);

        JsonObjectRequest addProfileRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                displayError("Placement Created successfully", 150000);
                Toast.makeText(getApplicationContext(), "Placement Created successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(addPlacement.this, AdminActivity.class);
                startActivity(i);
                finish();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                int statusCode = error.networkResponse.statusCode;

                if (error instanceof NoConnectionError) {
                    displayError("No Internet, Please try again..", 150000);
                }
                if (error instanceof TimeoutError) {
                    displayError("Server Down, Please try again..", 150000);
                }

                switch (statusCode) {
                    case 400:
                        displayError("Please FIll all Fields", 100000);
                        break;

                    case 404:
                        displayError("Please Login as Admin to Create Placement", 150000);
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
        queue.add(addProfileRequest);
    }

    public void displayError(String msg, int time) {
        showError.setVisibility(View.GONE);
        showError.setText(msg);
        showError.setVisibility(View.VISIBLE);
        showError.postDelayed(new Runnable() {
            public void run() {
                showError.setVisibility(View.GONE);
            }
        }, time);

    }
}