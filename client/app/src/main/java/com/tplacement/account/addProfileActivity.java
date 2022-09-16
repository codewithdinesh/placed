package com.tplacement.account;

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
import com.tplacement.config.config;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class addProfileActivity extends AppCompatActivity {
    TextView showError;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    String login_token;
    LinearLayout container;
    MaterialButton addProfile;

    TextInputEditText name, email, address, phone_no, skills, experience, teducation, tcollege, tschool, tschool_cgpa, tcollege_cgpa, dob, interest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);

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
        addProfile = findViewById(R.id.addProfileBtn);
        interest = findViewById(R.id.interest);

        addProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_name, str_email, str_address, str_phone_no, str_experience, str_education, str_school, str_college, str_college_cgpa, str_school_cgpa, str_dob, str_skills, str_interest;

                str_name = name.getText().toString().trim();
                str_email = email.getText().toString().toLowerCase().trim();
                str_address = address.getText().toString().trim();
                str_phone_no = phone_no.getText().toString().trim();
                str_experience = experience.getText().toString();
                str_college = tcollege.getText().toString();
                str_college_cgpa = tcollege_cgpa.getText().toString();
                str_school_cgpa = tschool_cgpa.getText().toString();
                str_education = teducation.getText().toString();
                str_school = tschool.getText().toString();
                str_dob = dob.getText().toString();
                str_skills = skills.getText().toString();
                str_interest = interest.getText().toString();

                // validate input and then add to profile

                addProfile(str_name, str_email, str_address, str_phone_no, str_skills, str_experience, str_education, str_school, str_school_cgpa, str_college, str_college_cgpa, str_dob, str_interest);
            }
        });


    }

    public void addProfile(String name, String email, String address, String phone_no, String skills, String experience, String teducation, String tschool, String tschool_cgpa, String tcollege, String tcollege_cgpa, String dob, String interest) {

        RequestQueue queue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);

        // BASE URL
        String url = new config().getBASE_URL() + "/profile";


        sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);

        login_token = sharedPreferences.getString("login_token", "");

        //body parts
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("name", name);
        params.put("dob", dob);
        params.put("address", address);
        params.put("phone_no", phone_no);
        params.put("skills", skills);
        params.put("experience", experience);
        params.put("education", teducation);
        params.put("school", tschool);
        params.put("school_cgpa", tschool_cgpa);
        params.put("college", tcollege);
        params.put("college_cgpa", tcollege_cgpa);
        params.put("interest", interest);

        JsonObjectRequest addProfileRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                displayError("Profile Created successfully",150000);
                Toast.makeText(getApplicationContext(), "Profile Created successfully", Toast.LENGTH_SHORT).show();


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

                    case 401:
                        displayError("Your Profile are already Created", 150000);

                    case 404:
                        displayError("Please Login to Create Profile", 150000);
                        break;

                    case 403:
                        displayError("Error while fetching profile", 150000);
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