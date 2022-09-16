package com.tplacement.placement;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.tplacement.R;
import com.tplacement.config.config;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class placement_Details_Fragment extends Fragment {
    String str_name, str_desc, str_date, str_org, str_org_desc, str_org_location, str_hr, str_skills, str_roles, str_id, str_education, str_experience;
    TextView showError;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    String login_token;

    public placement_Details_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_placement__details, container, false);

        TextView name = view.findViewById(R.id.name);
        TextView desc = view.findViewById(R.id.desc);
        TextView date = view.findViewById(R.id.date);
        TextView org = view.findViewById(R.id.org);
        TextView org_desc = view.findViewById(R.id.org_desc);
        TextView org_location = view.findViewById(R.id.org_location);
        TextView hr = view.findViewById(R.id.org_hr);
        TextView skills = view.findViewById(R.id.skills);
        TextView roles = view.findViewById(R.id.roles);
        TextView experience = view.findViewById(R.id.experience);
        TextView education = view.findViewById(R.id.education);
        Button apply = view.findViewById(R.id.apply);

        showError = view.findViewById(R.id.showError);
        progressBar = view.findViewById(R.id.progressBar);


        if (getArguments() != null) {
            str_name = getArguments().getString("name");
            str_desc = getArguments().getString("desc");
            str_org_desc = getArguments().getString("org_desc");
            str_date = getArguments().getString("date");
            str_org = getArguments().getString("org");
            str_hr = getArguments().getString("hr");
            str_skills = getArguments().getString("skills");
            str_roles = getArguments().getString("roles");
            str_org_location = getArguments().getString("org_location");
            str_id = getArguments().getString("id");
            str_education = getArguments().getString("education");
            str_experience = getArguments().getString("experience");
        }

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

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
                applyPlacement(str_id);
            }
        });

        return view;
    }

    public void applyPlacement(String id) {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        progressBar.setVisibility(View.VISIBLE);

        // BASE URL
        String url = new config().getBASE_URL() + "/apply";


        sharedPreferences = getActivity().getSharedPreferences("userDetails", MODE_PRIVATE);

        login_token = sharedPreferences.getString("login_token", "");

        //body parts
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("placement_id", id);

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getActivity().getApplicationContext(), "Your application are submitted successfully" +response.toString(), Toast.LENGTH_SHORT).show();

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
}