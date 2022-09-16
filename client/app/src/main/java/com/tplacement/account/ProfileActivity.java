package com.tplacement.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tplacement.R;
import com.tplacement.config.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    TextView showError;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    String login_token;
    LinearLayout container;
    FloatingActionButton btnProfile;

    TextView name, email, address, phone_no, skills, experience, teducation, tcollege, tschool, tschool_cgpa, tcollege_cgpa, dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        showError = findViewById(R.id.showError);
        progressBar = findViewById(R.id.progressBar);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        phone_no = findViewById(R.id.phone_no);
        skills = findViewById(R.id.skills);
        experience = findViewById(R.id.experience);
        teducation = findViewById(R.id.education);
        tschool = findViewById(R.id.school);
        tschool_cgpa = findViewById(R.id.school_cgpa);
        tcollege = findViewById(R.id.college);
        tcollege_cgpa = findViewById(R.id.college_cgpa);
        dob = findViewById(R.id.dob);
        container = findViewById(R.id.container);
        btnProfile = findViewById(R.id.add_profile);

        fetchProfile();

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, addProfileActivity.class);
                startActivity(i);
            }
        });

    }

    public void fetchProfile() {
        progressBar.setVisibility(View.VISIBLE);
        btnProfile.setVisibility(View.GONE);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        // BASE URL
        String url = new config().getBASE_URL() + "/user";

        sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);

        login_token = sharedPreferences.getString("login_token", "");

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String edu, school, school_cgpa, college, cgpa_college;
                    String profile_id, user_id, user_email, user_name, user_dob, user_phone_no, user_address, user_skills, user_experience, profile_created;

                    progressBar.setVisibility(View.GONE);
                    JSONObject profile = response.getJSONObject("profile");
                    JSONObject education = profile.getJSONObject("education");
                    edu = education.getString("education");
                    school = education.getString("school");
                    school_cgpa = education.getString("school_cgpa");
                    college = education.getString("college");
                    cgpa_college = education.getString("college_cgpa");
                    profile_id = profile.getString("_id");
                    user_id = profile.getString("user_id");
                    user_dob = profile.getString("dob");
                    user_email = profile.getString("email");
                    user_phone_no = profile.getString("phone_no");
                    user_address = profile.getString("address");
                    user_name = profile.getString("name");
                    user_skills = profile.getString("skills");
                    user_experience = profile.getString("experience");
                    profile_created = profile.getString("profile_created");

                    teducation.setText("Education: " + edu);
                    experience.setText("Experience: " + user_experience);
                    phone_no.setText("Phone No.: " + user_phone_no);
                    skills.setText("Skills: " + user_skills);
                    name.setText("Name : " + user_name);
                    address.setText("Address: " + user_address);
                    email.setText("Email: " + user_email);
                    dob.setText("Date of Birth: " + user_dob);
                    tcollege_cgpa.setText("College CGPA: " + cgpa_college);
                    tschool.setText("School: " + school);
                    tschool_cgpa.setText("School CGPA : " + school_cgpa);
                    tcollege.setText("College: " + college);


                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    btnProfile.setVisibility(View.VISIBLE);
                    displayError("Profile not Found, Please Create it", 150000);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

                if (error instanceof NoConnectionError) {
                    displayError("No Internet, Please try again..", 150000);
                }
                if (error instanceof TimeoutError) {
                    displayError("Server Down, Please try again..", 150000);
                } else if (error instanceof AuthFailureError) {

                    displayError("Authentication Error, Please try again later.. or please log Out then Return Login", 150000);

                } else if (error instanceof ServerError) {

                    displayError("Server Error, Please try again..", 150000);

                } else if (error instanceof NetworkError) {
                    displayError("Network Issue, Please try again..", 150000);

                } else if (error instanceof ParseError) {
                    displayError("Parse Error", 150000);
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
        container.setVisibility(View.GONE);
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