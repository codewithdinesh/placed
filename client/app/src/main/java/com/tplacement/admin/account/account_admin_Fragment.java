package com.tplacement.admin.account;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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
import com.tplacement.account.ProfileActivity;
import com.tplacement.config.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class account_admin_Fragment extends Fragment {
    SharedPreferences sharedPreferences;
    String login_token;
    TextView welcome;
    ProgressBar progressBar;
    TextView showError;
    TextView logout;

    public account_admin_Fragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account_admin, container, false);
        welcome = view.findViewById(R.id.welcome);
        showError = view.findViewById(R.id.showError);
        progressBar = view.findViewById(R.id.progressBar);
        logout = view.findViewById(R.id.logout);

        fetchProfile();


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOut();
            }
        });

        return view;

    }

    public void fetchProfile() {
        progressBar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        // BASE URL
        String url = new config().getBASE_URL() + "/admin";

        sharedPreferences = getActivity().getSharedPreferences("adminDetails", MODE_PRIVATE);

        login_token = sharedPreferences.getString("login_token", "");

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    String email = response.getString("email");
                    welcome.setText("Welcome, " + email);

                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    welcome.setVisibility(View.GONE);
                    displayError("Error while fetching profile data, please try again", 150000);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                welcome.setVisibility(View.GONE);

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
        showError.setVisibility(View.GONE);
        showError.setText(msg);
        showError.setVisibility(View.VISIBLE);
        showError.postDelayed(new Runnable() {
            public void run() {
                showError.setVisibility(View.GONE);
            }
        }, time);
    }

    public void LogOut() {
        sharedPreferences = getActivity().getSharedPreferences("adminDetails", MODE_PRIVATE);
        SharedPreferences.Editor mStore = sharedPreferences.edit();
        mStore.putString("login_token", "");
        mStore.apply();
        openActivity(LoginAdminActivity.class);
    }

    public void openActivity(Class c) {
        Intent i = new Intent(getActivity().getApplicationContext(), c);
        startActivity(i);
    }


}