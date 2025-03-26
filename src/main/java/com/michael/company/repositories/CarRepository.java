package com.michael.company.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.michael.company.entities.Car;

public interface CarRepository extends JpaRepository<Car, Long> {

    
}