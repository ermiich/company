package com.michael.company.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.michael.company.entities.Car;
import com.michael.company.repositories.CarRepository;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public void saveCar(Car car) {
        carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id).orElse(null);
    }
}
