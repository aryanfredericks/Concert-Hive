package com.aryan.javaminiproject.backend.concerthive.models.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "concerts")
public class Concert {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    int concert_id;

    @Column
    String concert_name;

    @Column
    String concert_price;

    @Column
    String concert_date;

    @Column
    String concert_time;

    @Column
    String concert_address;

    @Column
    int tickets_available;

    @Override
    public String toString() {
        return "{" +
                "\n    concert_id=" + concert_id +
                "\n    concert_name='" + concert_name + '\'' +
                "\n    concert_price='" + concert_price + '\'' +
                "\n    concert_date='" + concert_date + '\'' +
                "\n    concert_time='" + concert_time + '\'' +
                "\n    concert_address='" + concert_address + '\'' +
                "\n    tickets_available=" + tickets_available +
                "\n}";
    }

    
    public String toStringExcludingTicketsAvailable(String ticket_amount) {
        return "{" +
                "\n    concert_id=" + concert_id +","+
                "\n    concert_name='" + concert_name + ","+
                "\n    concert_price='" + concert_price + ","+
                "\n    concert_date='" + concert_date + ","+
                "\n    concert_time='" + concert_time + ","+
                "\n    concert_address='" + concert_address + ","+
                "\n    ticket_amount='" + ticket_amount + ","+
                "\n}";
    }
    



    public String getConcert_name() {
        return concert_name;
    }

    public void setConcert_name(String concert_name) {
        this.concert_name = concert_name;
    }

    public String getConcert_price() {
        return concert_price;
    }

    public void setConcert_price(String concert_price) {
        this.concert_price = concert_price;
    }

    public String getConcert_date() {
        return concert_date;
    }

    public void setConcert_date(String concert_date) {
        this.concert_date = concert_date;
    }

    public String getConcert_time() {
        return concert_time;
    }

    public void setConcert_time(String concert_time) {
        this.concert_time = concert_time;
    }

    public String getConcert_address() {
        return concert_address;
    }

    public void setConcert_address(String concert_address) {
        this.concert_address = concert_address;
    }

    public int getConcert_id() {
        return concert_id;
    }

    public void setConcert_id(int concert_id) {
        this.concert_id = concert_id;
    }

    public int getTickets_available() {
        return tickets_available;
    }

    public void setTickets_available(int tickets_available) {
        this.tickets_available = tickets_available;
    }

    public Concert(int concert_id, String concert_name, String concert_price, String concert_date, String concert_time, String concert_address, int tickets_available) {
        this.concert_id = concert_id;
        this.concert_name = concert_name;
        this.concert_price = concert_price;
        this.concert_date = concert_date;
        this.concert_time = concert_time;
        this.concert_address = concert_address;
        this.tickets_available = tickets_available;
    }

    public Concert() {
    }
}
