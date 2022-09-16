package com.tplacement.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.tplacement.R;
import com.tplacement.config.config;
import com.tplacement.placement.placementAdapter;
import com.tplacement.placement.placementData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class dashboardFragment extends Fragment {
    RecyclerView placementContainer;
    TextView showError;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    String login_token;
    List<placementData> placementData;

    public dashboardFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        showError = view.findViewById(R.id.showError);
        progressBar = view.findViewById(R.id.progressBar);
        placementContainer = view.findViewById(R.id.placements_container);
        placementData = new ArrayList<placementData>();

        fetchPlacement();

        return view;
    }


    private void fetchPlacement() {

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        progressBar.setVisibility(View.VISIBLE);
        // BASE URL
        String url = new config().getBASE_URL() + "/placements";

        sharedPreferences = getActivity().getSharedPreferences("userDetails", MODE_PRIVATE);

        login_token = sharedPreferences.getString("login_token", "");


        JsonArrayRequest placementRequest = new JsonArrayRequest(Request.Method.GET, url, new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray placements) {
                progressBar.setVisibility(View.GONE);
                try {

                    if (placements.length() == 0) {
                        displayError("No placements Found", 2500000);
                    } else {


                        for (int i = 0; i < placements.length(); i++) {

                            String name, description, date, role, hr, org, org_location, skills, org_desc, education, experience, dateOfApplication, placementID;
                            JSONObject placement = placements.getJSONObject(i);

                            name = placement.getString("name");
                            description = placement.getString("description");
                            date = placement.getString("placement_added_date");
                            role = placement.getString("role");
                            hr = placement.getString("hr");
                            org = placement.getString("organisation");
                            org_location = placement.getString("location");
                            org_desc = placement.getString("organisation_desc");
                            skills = placement.getString("skills");
                            placementID = placement.getString("_id");
                            education = placement.getString("education");
                            experience = placement.getString("experience");

                            placementData data = new placementData(name, description, date, role, hr, org, org_location, org_desc, skills, placementID, education, experience);
                            placementData.add(data);
                            placementAdapter placementAdapter = new placementAdapter(placementData, getActivity().getApplicationContext());
                            placementContainer.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                            placementContainer.setAdapter(placementAdapter);
                            
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Error while parsing data", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                if (error == null) {
                    displayError("something went wrong, please check internet connection", 150000);
                } else {
                    Log.d("errors", error.toString());
                }
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