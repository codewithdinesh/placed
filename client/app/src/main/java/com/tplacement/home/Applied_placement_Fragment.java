package com.tplacement.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.tplacement.R;
import com.tplacement.account.LoginActivity;
import com.tplacement.account.placementApplicationFragment;
import com.tplacement.account.userNotLoggedFragment;

public class Applied_placement_Fragment extends Fragment {

    SharedPreferences getShared;
    String login_token;

    public Applied_placement_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applied_placement_, container, false);

        getShared = getActivity().getSharedPreferences("userDetails", MODE_PRIVATE);
        login_token = getShared.getString("login_token", "");

        if (login_token.trim().isEmpty()) {
            Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (login_token.trim().isEmpty()) {
            userNotLoggedFragment userNotLoggedFragment = new userNotLoggedFragment("Please Login To See Your Placement Applications");
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.applicationContainer, userNotLoggedFragment).commit();
        } else {
            placementApplicationFragment applicationFragment = new placementApplicationFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.applicationContainer, applicationFragment).commit();
        }

    }
}