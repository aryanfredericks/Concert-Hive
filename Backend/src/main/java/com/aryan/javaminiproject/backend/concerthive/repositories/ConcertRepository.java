package com.aryan.javaminiproject.backend.concerthive.repositories;


import com.aryan.javaminiproject.backend.concerthive.models.Entities.Concert;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertRepository extends JpaRepository<Concert,Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Concert c SET c.tickets_available = c.tickets_available - :booked_tickets WHERE c.concert_id = :concert_id")
    int updateTicket(
            @Param("booked_tickets") int ticket,
            @Param("concert_id") int id
            );

    //Selecting the tickets available for a particular concert
    @Query("SELECT c.tickets_available FROM Concert c WHERE c.concert_id = :concert_id")
    int getTicketCount(
            @Param("concert_id") int id
    );
}
