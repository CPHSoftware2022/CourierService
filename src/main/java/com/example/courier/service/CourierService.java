package com.example.courier.service;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.courier.model.Courier;
import com.example.courier.repository.CourierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class CourierService implements GraphQLQueryResolver, GraphQLMutationResolver {

    AtomicInteger id = new AtomicInteger(0);

    @Autowired
    private CourierRepository courierRepository;

    public List<Courier> findAllCouriers() {
        return courierRepository.findAll();
    }

    public List<Courier> findOneCourier(Integer id) {
        return findAllCouriers().stream()
                .filter(courier -> courier.getCourierId() == id)
                .collect(Collectors.toList());
    }

    public List<Courier> findAvailableCouriers(Boolean available) {
        return findAllCouriers().stream()
                .filter(courier -> courier.getAvailable() == available)
                .collect(Collectors.toList());
    }

    private List<Courier> couriers = new ArrayList<>();

    public Courier addCourier(String firstName, String lastName, String email, String password, Boolean available) {
        return courierRepository
                .save(Courier.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .password(password)
                        .available(available)
                        .build());
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
}
