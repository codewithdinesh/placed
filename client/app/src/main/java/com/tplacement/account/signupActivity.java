package com.tplacement.account;

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
import com.tplacement.config.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class signupActivity extends AppCompatActivity {

    com.google.android.material.textfield.TextInputEditText email, password, retype_password;
    com.google.android.material.button.MaterialButton loginBtn, signUpBtn;
    TextView showError;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.userEmail);
        password = findViewById(R.id.userPassword);
        retype_password = findViewById(R.id.retype_userPassword);

        loginBtn = findViewById(R.id.loginBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        showError = findViewById(R.id.showError);
        progressBar = findViewById(R.id.progressBar);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(LoginActivity.class);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_password, str_retype_password, str_email;

                str_password = password.getText().toString();
                str_email = email.getText().toString();
                str_retype_password = retype_password.getText().toString();

                // Input validation
                signup(str_email, str_password, str_retype_password);
            }
        });

    }

    public void signup(String userInputEmail, String userInputPassword, String userInputRetypePassword) {

        RequestQueue queue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);

        // BASE URL
        String url = new config().getBASE_URL() + "/user/signup";

        //body parts
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", userInputEmail);
        params.put("pass", userInputPassword);
        params.put("retype_pass", userInputRetypePassword);

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    String token = response.getString("login_token");
                    String email = response.getString("email");

                    Toast.makeText(getApplicationContext(), "Account Created successfully", Toast.LENGTH_SHORT).show();

                    // if success account created then store user details
                    SharedPreferences sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);
                    SharedPreferences.Editor mStore = sharedPreferences.edit();
                    mStore.putString("login_token", token);
                    mStore.putString("userEmail", email);
                    mStore.apply();
                    openActivity(MainActivity.class);

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

    public void openActivity(Class c) {
        Intent i = new Intent(getApplicationContext(), c);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }

}