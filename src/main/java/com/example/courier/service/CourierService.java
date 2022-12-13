package com.example.courier.service;

import com.example.courier.kafka.ProducerService;
import com.example.courier.model.Courier;
import com.example.courier.model.EventModel;
import com.example.courier.repository.CourierRepository;

import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.GraphQlResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class CourierService implements GraphQLQueryResolver, GraphQLMutationResolver {

    AtomicInteger id = new AtomicInteger(0);

    @Autowired
    private ProducerService producerService;
    @Autowired
    private CourierRepository courierRepository;

    public List<Courier> findAllCouriers() {

        List<Courier> listOfCouriers = courierRepository.findAll();

        ResponseEntity response = new ResponseEntity<>(
                listOfCouriers,
                HttpStatus.OK);

        String resultString = "CourierList{size="+listOfCouriers.size()+"}";;
        EventModel eventModel = new EventModel("GET", response.getStatusCode(), resultString);
        producerService.sendMessage(eventModel.toString());

        return listOfCouriers;
    }

    public Optional<Courier> findOneCourier(Integer id) {
        return courierRepository.findById(id);
    }

    public List<Courier> findAvailableCouriers(Boolean available) {
        return findAllCouriers().stream()
                .filter(courier -> courier.getAvailable() == available)
                .collect(Collectors.toList());
    }

    private List<Courier> couriers = new ArrayList<>();

    public Courier addCourier(String firstName, String lastName, String email, String password, Boolean available) {

        Courier courier = courierRepository
                .save(Courier.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .password(password)
                        .available(available)
                        .build());

        ResponseEntity response = new ResponseEntity<>(
                courier,HttpStatus.OK);

        EventModel eventModel = new EventModel("POST", response.getStatusCode(), courier.toString());
        producerService.sendMessage(eventModel.toString());

        return courier;
    }


    public Courier delete(Integer id) {
        courierRepository.deleteById(id);
        return null;
    }

    public Courier update(Integer id, String firstName, String lastName, String email, String password, Boolean available) {
        Courier courier = new Courier();
        courier.setCourierId(id);
        courier.setFirstName(firstName);
        courier.setLastName(lastName);
        courier.setEmail(email);
        courier.setPassword(password);
        courier.setAvailable(available);

        return courierRepository.saveAndFlush(courier);
    }

    public Courier updateStatus(Integer id, Boolean available) {
        Courier courier = new Courier();
        courier.setCourierId(id);
        courier.setAvailable(available);

        return courierRepository.saveAndFlush(courier);
    }
}
