package com.example.courier.service;

import com.example.courier.model.Courier;
import com.example.courier.repository.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DataLoader {

    @Autowired
    private CourierRepository userRepository;

    AtomicInteger id = new AtomicInteger(0);

    @PostConstruct
    public void loadData(){

        Courier courier1 = new Courier(id.incrementAndGet(),"Kenneth" ,"Hansen","kenneth@kenneth.dk","password123",true);
        Courier courier2 = new Courier(id.incrementAndGet(),"Jens" ,"Jansen","jens@jens.dk","password123",false);
        Courier courier3 = new Courier(id.incrementAndGet(),"Birger" ,"Birgersen","birger@birger.dk","password123",true);


        userRepository.save(courier1);
        userRepository.save(courier2);
        userRepository.save(courier3);


    }

}
