package com.tplacement.admin.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tplacement.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminNotLoggedFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminNotLoggedFragement extends Fragment {

    Button loginBtn;
    TextView msg;
    String str_msg;

    public AdminNotLoggedFragement(String smsg) {
        this.str_msg = smsg;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_not_logged_fragement, container, false);

        loginBtn = view.findViewById(R.id.loginBtn);
        msg = view.findViewById(R.id.msg);

        if (!str_msg.isEmpty()) {
            msg.setText(str_msg);
            msg.setVisibility(View.VISIBLE);
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), LoginAdminActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}