package com.aryan.javaminiproject.backend.concerthive.services;

import com.aryan.javaminiproject.backend.concerthive.repositories.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConcertService {

    @Autowired
    ConcertRepository crep;


}
