package com.aryan.javaminiproject.backend.concerthive.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.aryan.javaminiproject.backend.concerthive.models.Entities.BookedTickets;

public interface BookedTicketsRepository extends JpaRepository<BookedTickets,Integer> {

    @Query("SELECT b FROM BookedTickets b WHERE b.booked_by_user = :id")
    public ArrayList<BookedTickets> findByBooked_by_user(@Param("id") int id);
} 
