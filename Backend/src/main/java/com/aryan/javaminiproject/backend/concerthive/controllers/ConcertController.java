package com.aryan.javaminiproject.backend.concerthive.controllers;


import com.aryan.javaminiproject.backend.concerthive.models.Entities.Concert;
import com.aryan.javaminiproject.backend.concerthive.repositories.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ConcertController {
    @Autowired
    ConcertRepository rep;

    @PostMapping("/book-concert")
    public ResponseEntity<?> addConcert(
            @RequestBody Concert concert
            ){
        System.out.println(concert);
        if(concert.toString().isEmpty()){
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }
        else{
            Concert c  = rep.save(concert);
            return new ResponseEntity<>(c,HttpStatus.OK);
        }
    }

    @GetMapping("/get-available-concerts")
    public ResponseEntity<?> fetchAllConcerts(){
        return new ResponseEntity<>(rep.findAll(),HttpStatus.OK);
    }

    @PostMapping("/update-ticket-count")
    public ResponseEntity<?> updateCount(
            @RequestParam ("concert id") String _id,
            @RequestParam ("ticket count") String _count
    ){

        int available = rep.getTicketCount(Integer.parseInt(_id));
        if(available<Integer.parseInt(_count)){
            return new ResponseEntity<>("Not Enough Tickets Available",HttpStatus.OK);
        }
        System.out.println(_id+" "+_count);
        int id = Integer.parseInt(_id);
        int count = Integer.parseInt(_count);
        if(rep.findById(id).isPresent()){
            int result = rep.updateTicket(count , id);
            if (result>0){
                return new ResponseEntity<>("Updated Successfully",HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Not Updated",HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("No Concert Found with that id",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/greet")
    public ResponseEntity<?> GOODmORNING(){
        return new ResponseEntity<>("Good Morning",HttpStatus.OK);
    }
}
