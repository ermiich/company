package com.michael.company.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.michael.company.entities.CarsSold;
import com.michael.company.repositories.CarsSoldRepository;

@Service
public class CarsSoldService {

    @Autowired
    private CarsSoldRepository carsSoldRepository;

    public void saveCarsSold(CarsSold carsSold) {
        carsSoldRepository.save(carsSold);
    }

    public List<CarsSold> getAllCarsSold() {
        return carsSoldRepository.findAll();
    }

    public CarsSold getCarsSoldById(Long id) {
        return carsSoldRepository.findById(id).orElse(null);
    }
}
