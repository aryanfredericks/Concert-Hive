package com.aryan.javaminiproject.TicketBooking.fragments;

import com.aryan.javaminiproject.TicketBooking.models.ConcertList;

public interface OnConcertBookedListener {
    void saveConcert(ConcertList concert);
}
