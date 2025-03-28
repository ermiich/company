package com.michael.company.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.michael.company.dtos.requests.SaveCarRequest;
import com.michael.company.services.CarService;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {
  @Autowired
  private CarService carService;

  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllCars() {
    return carService.getAllCars();
  }

  @PostMapping
  public ResponseEntity<Map<String, Object>> saveCar(@Valid @RequestBody SaveCarRequest request) {
    return carService.createCar(request);
  }

}
