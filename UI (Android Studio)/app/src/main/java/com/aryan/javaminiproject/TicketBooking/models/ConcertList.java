package com.aryan.javaminiproject.TicketBooking.models;

import androidx.annotation.NonNull;

public class ConcertList {
    private String imageUrl;
    private String title;
    private String price;
    private String date;
    private String time;
    private String address;
    private int id;
    private int tickets_available;

    @Override
    public String toString() {
        return "ConcertList{" +
                "imageUrl='" + imageUrl + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", address='" + address + '\'' +
                ", id=" + id +
                ", tickets_available=" + tickets_available +
                '}';
    }

    public ConcertList(String title, String price, String date, String time, String address, int id, int tickets_available) {
        this.title = title;
        this.price = price;
        this.date = date;
        this.time = time;
        this.address = address;
        this.id = id;
        this.tickets_available = tickets_available;
    }

    public ConcertList() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTickets_available() {
        return tickets_available;
    }

    public void setTickets_available(int tickets_available) {
        this.tickets_available = tickets_available;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
