package com.aryan.javaminiproject.TicketBooking.models;

public class BookedTickets {
    private String concertName;
    private String concertDate;
    private Float finalPrice;

    @Override
    public String toString() {
        return "BookedTickets{" +
                "concertName='" + concertName + '\'' +
                ", concertDate='" + concertDate + '\'' +
                ", finalPrice='" + finalPrice + '\'' +
                '}';
    }

    public String getConcertName() {
        return concertName;
    }

    public void setConcertName(String concertName) {
        this.concertName = concertName;
    }

    public String getConcertDate() {
        return concertDate;
    }

    public void setConcertDate(String concertDate) {
        this.concertDate = concertDate;
    }

    public Float getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Float finalPrice) {
        this.finalPrice = finalPrice;
    }

    public BookedTickets() {
    }

    public BookedTickets(String concertName, String concertDate, Float finalPrice) {
        this.concertName = concertName;
        this.concertDate = concertDate;
        this.finalPrice = finalPrice;
    }
}
