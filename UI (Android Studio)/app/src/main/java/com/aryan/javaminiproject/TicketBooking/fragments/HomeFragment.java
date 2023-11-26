package com.aryan.javaminiproject.TicketBooking.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aryan.javaminiproject.TicketBooking.R;
import com.aryan.javaminiproject.TicketBooking.models.ConcertList;
import com.aryan.javaminiproject.TicketBooking.recyclerViews.ConcertRecyclerViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment{
    RecyclerView recyclerView;
    int id;
    public HomeFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        id = this.getArguments().getInt("SENDUSERID");

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshHomePage();
        fetchData();
        return  view;
    }
    public void refreshHomePage(){
        RequestQueue rq = Volley.newRequestQueue(requireContext());
        JsonArrayRequest jar = new JsonArrayRequest(
                Request.Method.GET,
                "http://192.168.29.115:8080/api/v1/get-available-concerts",
                null,
                response -> {
                    ArrayList<ConcertList> arrayList= new ArrayList<>();
                    for(int i = 0 ; i< response.length();i++){
                        try {
                            arrayList.add(new ConcertList(
                                    response.getJSONObject(i).getString("concert_name"),
                                    response.getJSONObject(i).getString("concert_price"),
                                    response.getJSONObject(i).getString("concert_date"),
                                    response.getJSONObject(i).getString("concert_time"),
                                    response.getJSONObject(i).getString("concert_address"),
                                    Integer.parseInt(response.getJSONObject(i).getString("concert_id")),
                                    Integer.parseInt(response.getJSONObject(i).getString("tickets_available"))
                            ));
                        } catch (JSONException e) {
                            Toast.makeText(getContext(),"Failed to add data",Toast.LENGTH_LONG).show();
                            Log.d("ola", e.getMessage());
                        }
                    }
                    ConcertRecyclerViewAdapter adapter = new ConcertRecyclerViewAdapter(getContext(),arrayList,this);
                    recyclerView.setAdapter(adapter);
                },
                error -> {
                    Toast.makeText(getContext(),"Failed to load data",Toast.LENGTH_LONG).show();
                }
        );
        rq.add(jar);
    }

    public void fetchData(){
        RequestQueue rq = Volley.newRequestQueue(getContext());
        JsonObjectRequest jor =  new JsonObjectRequest(
                Request.Method.GET,
                "http://192.168.29.115:8080/api/v1/user/" + id,
                null,
                response -> {
                    try {
                        Log.d("ola", "Correctly Loaded Data");
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

    public void updateTickets(int ticketCount, int concertId) {
        RequestQueue rq = Volley.newRequestQueue(requireContext());
        StringRequest jar = new StringRequest(
                Request.Method.POST,
                "http://192.168.29.115:8080/api/v1/update-ticket-count",
                response -> {
                    if(response.toString().equals("Updated Successfully")){
                        Toast.makeText(getContext(),"Thank You For Booking Tickets",Toast.LENGTH_LONG).show();
                    } else if (response.toString().equals("Updated Successfully")) {
                        Toast.makeText(getContext(),"Tickets Not Available",Toast.LENGTH_LONG).show();
                    } else{
                        Toast.makeText(getContext(),"Booking Unsuccessful. Please Try Again Later",Toast.LENGTH_LONG).show();
                    }
                    refreshHomePage();
                },
                error -> {
                    Toast.makeText(getContext(),"Failed to load data",Toast.LENGTH_LONG).show();
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("ticket count", String.valueOf(ticketCount));
                params.put("concert id", String.valueOf(concertId));
                return params;
            }
        };
        rq.add(jar);
    }

    public void bookTickets(int ticket_id , int booked_by_user , int ticket_amount){
        RequestQueue rq = Volley.newRequestQueue(getContext());

        JSONObject obj = new JSONObject();
        try {
            obj.put("ticket_id" ,ticket_id );
            obj.put("booked_by_user" , booked_by_user);
            obj.put("ticket_amount" , ticket_amount);
        } catch (JSONException e) {
            Log.d("ola", e.getMessage());
        }
        JsonObjectRequest jor =  new JsonObjectRequest(
                Request.Method.POST,
                "http://192.168.29.115:8080/api/v1/book-tickets",
                obj,
                response -> {
                    try{Toast.makeText(getContext(), "Pleaase Check Email for ticket information.", Toast.LENGTH_SHORT).show();}
                    catch(Exception e){
                        Log.d("ola", "entered response but got error");
                    }
                },
                error -> {
                    Log.d("ola", "some server error occured");
                }
        );
        rq.add(jor);
    }
    public int getUserId(){
        return id;
    }
}