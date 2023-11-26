package com.aryan.javaminiproject.backend.concerthive.models.Entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "booked_ticks")
public class BookedTickets {
    
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int transcation_id ;


    @Column
    private int ticket_id;

    @Column
    private int booked_by_user;

    @Column 
    int ticket_amount;

    @Column
    float total_price;

    public BookedTickets() {
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public int getBooked_by_user() {
        return booked_by_user;
    }

    

    

    public void setBooked_by_user(int booked_by_user) {
        this.booked_by_user = booked_by_user;
    }
    public int getTicket_amount() {
        return ticket_amount;
    }

    public void setTicket_amount(int ticket_amount) {
        this.ticket_amount = ticket_amount;
    }

    public BookedTickets(int ticket_id, int booked_by_user, int ticket_amount) {
        this.ticket_id = ticket_id;
        this.booked_by_user = booked_by_user;
        this.ticket_amount = ticket_amount;
    }

    public BookedTickets(int ticket_id, int booked_by_user, int ticket_amount, float total_price) {
        this.ticket_id = ticket_id;
        this.booked_by_user = booked_by_user;
        this.ticket_amount = ticket_amount;
        this.total_price = total_price;
    }

    @Override
    public String toString() {
        return "{" +
                "\n    ticket_id=" + ticket_id +
                "\n    booked_by_user=" + booked_by_user +
                "\n    ticket_amount=" + ticket_amount +
                "\n}";
    }

    public int getTranscation_id() {
        return transcation_id;
    }

    public void setTranscation_id(int transcation_id) {
        this.transcation_id = transcation_id;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }
    

    
}
