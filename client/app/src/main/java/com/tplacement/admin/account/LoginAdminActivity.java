package com.tplacement.admin.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.tplacement.MainActivity;
import com.tplacement.R;
import com.tplacement.admin.AdminActivity;
import com.tplacement.config.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginAdminActivity extends AppCompatActivity {
    com.google.android.material.textfield.TextInputEditText email, password;
    com.google.android.material.button.MaterialButton loginBtn, signUpBtn;
    TextView showError;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        email = findViewById(R.id.userEmail);
        password = findViewById(R.id.userPassword);

        loginBtn = findViewById(R.id.loginBtn);
        signUpBtn = findViewById(R.id.signUpBtn);

        showError = findViewById(R.id.showError);

        progressBar = findViewById(R.id.progressBar);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uEmail, uPassword;
                uEmail = email.getText().toString().trim();
                uPassword = password.getText().toString().trim();

                LoginRequest(uEmail, uPassword);
            }
        });
    }

    private void LoginRequest(String userInputEmail, String userInputPassword) {
        RequestQueue queue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);

        // BASE URL
        String url = new config().getBASE_URL() + "/admin/login";

        //body parts
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", userInputEmail);
        params.put("password", userInputPassword);

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    String token = response.getString("login_token");
                    String email = response.getString("email");
                    String userID = response.getString("userId");

                    Toast.makeText(LoginAdminActivity.this, "Logged successfully", Toast.LENGTH_SHORT).show();

                    // if success account created then store user details
                    SharedPreferences sharedPreferences = getSharedPreferences("adminDetails", MODE_PRIVATE);
                    SharedPreferences.Editor mStore = sharedPreferences.edit();
                    mStore.putString("login_token", token);
                    mStore.putString("userEmail", email);
                    mStore.putString("userID", userID);
                    mStore.apply();
                    openActivity(AdminActivity.class);

                } catch (JSONException e) {

                    displayError("Something went wrong", 150000);

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

                    displayError("Authentication Error, Please Enter correct email and password", 150000);

                } else if (error instanceof ServerError) {

                    displayError("Server Error, Please try again..", 150000);

                } else if (error instanceof NetworkError) {
                    displayError("Network Issue, Please try again..", 150000);

                } else if (error instanceof ParseError) {
                    displayError("Parse Error", 150000);
                }
            }
        });
        queue.add(loginRequest);
    }


    public void openActivity(Class c) {
        Intent i = new Intent(getApplicationContext(), c);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
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