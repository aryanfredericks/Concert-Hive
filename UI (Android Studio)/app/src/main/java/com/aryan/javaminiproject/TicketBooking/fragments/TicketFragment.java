package com.aryan.javaminiproject.TicketBooking.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.aryan.javaminiproject.TicketBooking.R;
import com.aryan.javaminiproject.TicketBooking.models.BookedTickets;
import com.aryan.javaminiproject.TicketBooking.recyclerViews.TicketRecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TicketFragment extends Fragment {
    int userId;
    RecyclerView recyclerView;
    public TicketFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ticketView =  inflater.inflate(R.layout.fragment_ticket, container, false);
        userId = this.getArguments().getInt("SENDUSERID");
        recyclerView = ticketView.findViewById(R.id.ticketFragmentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchDetails();
        return ticketView;
    }

    public void fetchDetails(){
        RequestQueue rq = Volley.newRequestQueue(getContext());
        JsonObjectRequest jor = new JsonObjectRequest(
                Request.Method.GET,
                "http://192.168.29.115:8080/api/v1/booked-tickets/"+userId,
                null,
                response -> {
                    ArrayList<BookedTickets> arrayList = new ArrayList<>();
                    try{
                        JSONArray concertArray = response.getJSONArray("concerts");
                        JSONArray priceArray = response.getJSONArray("prices");
                        for (int i = 0; i < concertArray.length(); i++) {
                            arrayList.add(new BookedTickets(
                                concertArray.getJSONObject(i).getString("concert_name"),
                                concertArray.getJSONObject(i).getString("concert_date"),
                                (float)priceArray.getDouble(i)
                            ));
                        }
                    }
                    catch (Exception e){
                        Log.d("ola", "failed to fetch details");
                    }
                    TicketRecyclerView adapter = new TicketRecyclerView(getContext(),arrayList , this);
                    recyclerView.setAdapter(adapter);
                },
                error -> {
                    Log.d("ola", "error in API CaLL ");
                }
        );
        rq.add(jor);
    }
}