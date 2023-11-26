package com.aryan.javaminiproject.backend.concerthive.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.javaminiproject.backend.concerthive.models.Entities.BookedTickets;
import com.aryan.javaminiproject.backend.concerthive.models.Entities.Concert;
import com.aryan.javaminiproject.backend.concerthive.models.Entities.User;
import com.aryan.javaminiproject.backend.concerthive.repositories.BookedTicketsRepository;
import com.aryan.javaminiproject.backend.concerthive.repositories.ConcertRepository;
import com.aryan.javaminiproject.backend.concerthive.repositories.UserRepository;
import com.aryan.javaminiproject.backend.concerthive.services.EmailSenderService;

@RestController
@RequestMapping("/api/v1")
public class BookedTicketsController {
    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private BookedTicketsRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConcertRepository concertrepository;
    

    @PostMapping("/book-tickets")
    public ResponseEntity<?> bookTickets(
        @RequestBody BookedTickets bookedTickets
    ){

        int tId = bookedTickets.getTicket_id();
        int tAmount = bookedTickets.getTicket_amount();
        int bookedBy = bookedTickets.getBooked_by_user();
        Concert concert = concertrepository.findById(tId).get();
        String price_of_1_ticket = concert.getConcert_price();
        Float finalPrice = Float.parseFloat(price_of_1_ticket.substring(1)) * tAmount;
        BookedTickets finalBookedTickets = new BookedTickets(tId, bookedBy, tAmount, finalPrice);
        System.out.println(bookedTickets.toString());
        BookedTickets t = repository.save(finalBookedTickets);
        int id = bookedTickets.getBooked_by_user();
        int ticket_id = bookedTickets.getTicket_id();
        String c = concertrepository.findById(ticket_id).get().toStringExcludingTicketsAvailable(String.valueOf(bookedTickets.getTicket_amount()));
        String email = userRepository.findById(id).get().getEmail();
        String subject = "Concert Hive Ticket Booking";
        String body = "Your ticket has been booked successfully.\nYour Final Payout Price is $"+finalPrice+".\nBelow you will find the details of your booking.\n" + c+ "\n\nThank you for using Concert Hive.";
        sendEmail(email, subject, body);
        return new ResponseEntity<>("Email Sent Successfully", HttpStatus.OK);
    }

    @GetMapping("/booked-tickets/{id}")
    public ResponseEntity<?> getBookedTickets(
        @PathVariable int id
    ){

        int myUserId= userRepository.findById(id).get().getId();

        ArrayList<BookedTickets> bookedTicketsByUser = repository.findByBooked_by_user(id);

        ArrayList<Float> totalPrices = new ArrayList<>();
        for(BookedTickets bookedTickets : bookedTicketsByUser){
            totalPrices.add(bookedTickets.getTotal_price());
        }

        ArrayList<Concert> concerts = new ArrayList<>();
        for(BookedTickets bookedTickets : bookedTicketsByUser){
            concerts.add(concertrepository.findById(bookedTickets.getTicket_id()).get());
        }

        HashMap<String, Object> response = new HashMap<>();
        response.put("prices", totalPrices);
        response.put("concerts", concerts);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public void sendEmail(String email , String subject , String body){
        emailSenderService.sendEmail(email, subject, body);
    }
}

