package com.aryan.javaminiproject.TicketBooking.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.aryan.javaminiproject.TicketBooking.HomeActivity;
import com.aryan.javaminiproject.TicketBooking.Login;
import com.aryan.javaminiproject.TicketBooking.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.HashMap;

public class ProfileFragment extends Fragment {
    FloatingActionButton fab;
    TextInputEditText profile_username,profile_email,profile_password;
    Button confirmEditButton, logoutButton;
    int user_id;

    public ProfileFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_profile, container, false);
        user_id = this.getArguments().getInt("SENDUSERID");
        fab = myView.findViewById(R.id.fab);
        profile_username = myView.findViewById(R.id.profile_username);
        profile_email = myView.findViewById(R.id.profile_email);
        profile_password = myView.findViewById(R.id.profile_password);
        confirmEditButton = myView.findViewById(R.id.confirmEditButton);
        logoutButton = myView.findViewById(R.id.logoutButton);
        profile_email.setText("");
        profile_username.setText("");
        profile_password.setText("");
        profile_username.setEnabled(false);
        profile_email.setEnabled(false);
        profile_password.setEnabled(false);
        fetchData();
        fab.setOnClickListener(view->{
            profile_username.setEnabled(true);
            profile_email.setEnabled(true);
            profile_password.setEnabled(true);
            confirmEditButton.setVisibility(View.VISIBLE);
        });
        //SENDING UPDATE REQUEST TO THE SERVER USING THE ENTERED FIELDS PROVIDED BY THE USER
        confirmEditButton.setOnClickListener(view->{
            refreshPage();
        });
        //logout button
        logoutButton.setOnClickListener(
                logout->{
                    startActivity(new Intent(getActivity(), Login.class));
                    try{getActivity().finish();}
                    catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
        );
        return myView;
    }


    public void fetchData(){
        RequestQueue rq = Volley.newRequestQueue(getContext());
        JsonObjectRequest jor =  new JsonObjectRequest(
                Request.Method.GET,
                "http://192.168.29.115:8080/api/v1/user/" + user_id,
                null,
                response -> {
                    try {
                        profile_username.setText(response.getString("username"));
                        profile_email.setText(response.getString("email"));
                        profile_password.setText(response.getString("password"));
                    }
                    catch(Exception e){
                        Log.d("errors", e.getMessage());
                    }
                },
                error -> {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );
        rq.add(jor);
    }

    public void refreshPage()
    {
        String username = profile_username.getText().toString();
        String email = profile_email.getText().toString();
        String password = profile_password.getText().toString();
        RequestQueue rq = Volley.newRequestQueue(getContext());

        HashMap<String , String> params =  new HashMap<>();
        params.put("username" , username);
        params.put("email" , email);
        params.put("password" , password);
        JsonObjectRequest jor =  new JsonObjectRequest(
                Request.Method.PUT,
                "http://192.168.29.115:8080/api/v1/user/" + user_id,
                new JSONObject(params),
                response -> {
                    try {
                        profile_username.setText(response.getString("username"));
                        profile_email.setText(response.getString("email"));
                        profile_password.setText(response.getString("password"));
                        Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_LONG).show();
                    }
                    catch(Exception e){
                        Toast.makeText(getContext(), "Failed to retrieve Request", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );
        rq.add(jor);

        profile_username.setEnabled(false);
        profile_email.setEnabled(false);
        profile_password.setEnabled(false);
        confirmEditButton.setVisibility(View.INVISIBLE);
    }
}