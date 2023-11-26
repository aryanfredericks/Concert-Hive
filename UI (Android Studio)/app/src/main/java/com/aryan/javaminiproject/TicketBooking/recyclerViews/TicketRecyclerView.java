package com.aryan.javaminiproject.TicketBooking.recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aryan.javaminiproject.TicketBooking.R;
import com.aryan.javaminiproject.TicketBooking.fragments.TicketFragment;
import com.aryan.javaminiproject.TicketBooking.models.BookedTickets;

import java.util.ArrayList;

public class TicketRecyclerView extends RecyclerView.Adapter<TicketRecyclerView.Viewholder> {

    private Context context;
    private ArrayList<BookedTickets> list ;
    private TicketFragment ticketFragment;

    public TicketRecyclerView(Context context, ArrayList<BookedTickets> list, TicketFragment ticketFragment) {
        this.context = context;
        this.list = list;
        this.ticketFragment = ticketFragment;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.ticket_card_view , parent , false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.concertName.setText(list.get(position).getConcertName());
        holder.concertDate.setText(list.get(position).getConcertDate());
        holder.totalPrice.setText("$"+list.get(position).getFinalPrice());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class Viewholder extends  RecyclerView.ViewHolder{
        TextView concertName , concertDate , totalPrice;
        LinearLayout redeemTickets;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            concertDate = itemView.findViewById(R.id.ticketCardConcertDate);
            concertName = itemView.findViewById(R.id.ticketCardConcertName);
            totalPrice = itemView.findViewById(R.id.ticketCardConcertPrice);
            redeemTickets = itemView.findViewById(R.id.redeemTickets);
        }
    }
}
