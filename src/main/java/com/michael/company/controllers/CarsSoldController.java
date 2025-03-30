package com.michael.company.controllers;

import com.michael.company.dtos.requests.SaveCarsSoldRequest;
import com.michael.company.entities.CarsSold;
import com.michael.company.services.CarsSoldService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/cars-sold")
public class CarsSoldController {

  /*
   * Cars Sold
   */
  @Autowired
  private CarsSoldService carsSoldService;

  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllCarsSold() {
    return carsSoldService.getAllCarsSold();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getCarSoldById(@PathVariable Long id) {
    return carsSoldService.getCarSoldById(id);
  }

  @PostMapping
  public ResponseEntity<Map<String, Object>> saveCarSold(@RequestBody SaveCarsSoldRequest request) {
    return carsSoldService.createCarSold(request);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Object>> deleteCarSold(@PathVariable Long id,
      @RequestBody SaveCarsSoldRequest request) {
    request.setId(id);
    return carsSoldService.deleteCarSold(id);
  }

}