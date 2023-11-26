package com.aryan.javaminiproject.TicketBooking.recyclerViews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.aryan.javaminiproject.TicketBooking.R;
import com.aryan.javaminiproject.TicketBooking.fragments.HomeFragment;
import com.aryan.javaminiproject.TicketBooking.fragments.OnConcertBookedListener;
import com.aryan.javaminiproject.TicketBooking.models.ConcertList;
import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConcertRecyclerViewAdapter extends RecyclerView.Adapter<ConcertRecyclerViewAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<ConcertList> concertList;
    private HomeFragment homeFragment;
    int num;

    public ConcertRecyclerViewAdapter(Context context, ArrayList<ConcertList> concertList,HomeFragment homeFragment) {
        this.context = context;
        this.concertList = concertList;
        this.homeFragment = homeFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.home_concert_card,parent,false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(concertList.get(position).getTitle());
        holder.price.setText(String.format("Price : %s",concertList.get(position).getPrice()));
        holder.date.setText(String.format("Date : %s",concertList.get(position).getDate()));
        holder.time.setText(String.format("Time : %s",concertList.get(position).getTime()));
        holder.address.setText(String.format("Address : %s",concertList.get(position).getAddress()));
        holder.tickets_available.setText(String.format("Tickets Available : %s",concertList.get(position).getTickets_available()));
        holder.id.setText(String.format("Concert Id : %s",concertList.get(position).getId()));


        holder.book_button.setOnClickListener(v -> {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.confirm_booking_dialogue);
            SwipeButton swipeButton = dialog.findViewById(R.id.swipeBtn);
            Button addSeatBtn = dialog.findViewById(R.id.addSeatBtn);
            Button removeSeatBtn = dialog.findViewById(R.id.removeSeatBtn);
            TextView seats = dialog.findViewById(R.id.seats);

            addSeatBtn.setOnClickListener(
                    add->{
                        if(Integer.parseInt(seats.getText().toString())==10){
                            Toast.makeText(context,"Max of 10 seats can be booked",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            num = Integer.parseInt(seats.getText().toString());
                            num++;
                            seats.setText(String.valueOf(num));
                        }
                    }
            );
            removeSeatBtn.setOnClickListener(
                    remove->{
                        if(Integer.parseInt(seats.getText().toString())==0){
                            Toast.makeText(context,"Min of 0 seats can be booked",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            num = Integer.parseInt(seats.getText().toString());
                            num--;
                            seats.setText(String.valueOf(num));
                        }
                    }
            );
            swipeButton.setOnStateChangeListener(active -> {
                int ticket_count = Integer.parseInt(seats.getText().toString());
                homeFragment.updateTickets(ticket_count,concertList.get(position).getId());
                homeFragment.bookTickets(concertList.get(position).getId(),homeFragment.getUserId(),num);
                Handler handler= new Handler();
                handler.postDelayed(dialog::dismiss , 200);
            });
            dialog.show();
        });
    }
    @Override
    public int getItemCount() {
        return concertList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,price,date,time,address,tickets_available,id;
        Button book_button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.concert_card_title);
            price=itemView.findViewById(R.id.concert_card_price);
            date=itemView.findViewById(R.id.concert_card_date);
            time=itemView.findViewById(R.id.concert_card_time);
            address=itemView.findViewById(R.id.concert_card_address);
            tickets_available=itemView.findViewById(R.id.concert_card_tickets_available);
            id=itemView.findViewById(R.id.concert_card_id);
            book_button = itemView.findViewById(R.id.book_tickets);
        }
    }
}


