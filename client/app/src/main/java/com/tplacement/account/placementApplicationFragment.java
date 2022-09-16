package com.tplacement.account;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.tplacement.R;
import com.tplacement.config.config;
import com.tplacement.placement.placementAdapter;
import com.tplacement.placement.placementData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class placementApplicationFragment extends Fragment {
    RecyclerView placementContainer;
    TextView showError;
    CardView errorContainer;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    String login_token;

    List<placementData> your_placements = new ArrayList<placementData>();

    public placementApplicationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_placement_application, container, false);
        placementContainer = view.findViewById(R.id.applicationContainer);
        showError = view.findViewById(R.id.showError);
        progressBar = view.findViewById(R.id.progressBar);
        errorContainer = view.findViewById(R.id.errorContainer);
        fetchApplications();
        return view;
    }

    public void fetchApplications() {

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        progressBar.setVisibility(View.VISIBLE);

        // BASE URL
        String url = new config().getBASE_URL() + "/user";

        sharedPreferences = getActivity().getSharedPreferences("userDetails", MODE_PRIVATE);

        login_token = sharedPreferences.getString("login_token", "");


        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray applications = response.getJSONArray("applications");

                    if (applications.length() == 0) {
                        displayError("Not found any applications on your account ", 150000);
                    } else {


                        for (int i = 0; i < applications.length(); i++) {
                            JSONObject application = applications.getJSONObject(i);
                            String applicationID = application.getString("_id");
                            String dateOfApplication = application.getString("applied_on");
                            fetchPlacement(application.getString("placement"), applicationID, dateOfApplication);

                        }
                    }

                } catch (JSONException e) {
                    displayError("Application Not found", 100000);
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

                    displayError("Authentication Error, Please try Again..", 150000);

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


    private void fetchPlacement(String id, String ApplicationID, String DateOfApplication) {

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        progressBar.setVisibility(View.VISIBLE);
        // BASE URL
        String url = new config().getBASE_URL() + String.format("/placement?id=%1$s", id);

        sharedPreferences = getActivity().getSharedPreferences("userDetails", MODE_PRIVATE);

        login_token = sharedPreferences.getString("login_token", "");


        JsonObjectRequest placementRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject placement) {
                progressBar.setVisibility(View.GONE);

                try {
                    String name, description, date, role, hr, org, org_location, skills, org_desc, education, experience, dateOfApplication, applicationID, placementID;

                    name = placement.getString("name");
                    description = placement.getString("description");
                    date = placement.getString("placement_added_date");
                    role = placement.getString("role");
                    hr = placement.getString("hr");
                    org = placement.getString("organisation");
                    org_location = placement.getString("location");
                    org_desc = placement.getString("organisation_desc");
                    skills = placement.getString("skills");
                    education = placement.getString("education");
                    experience = placement.getString("experience");

                    dateOfApplication = DateOfApplication;
                    applicationID = ApplicationID;
                    placementID = placement.getString("_id");

                    placementData data = new placementData(name, description, date, role, hr, org, org_location, org_desc, skills, placementID, education, experience);

                    your_placements.add(data);
                    placementAdapter placementAdapter = new placementAdapter(your_placements, getActivity().getApplicationContext());
                    placementContainer.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    placementContainer.setAdapter(placementAdapter);

                } catch (JSONException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Error while parsing data", Toast.LENGTH_SHORT).show();
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

                    displayError("Authentication Error, Please try Again..", 150000);

                } else if (error instanceof ServerError) {

                    displayError("Server Error, Please try again..", 150000);

                } else if (error instanceof NetworkError) {
                    displayError("Network Issue, Please try again..", 150000);

                } else if (error instanceof ParseError) {
                    displayError("Parse Error", 150000);
                }
            }
        });

        queue.add(placementRequest);
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