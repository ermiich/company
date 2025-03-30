package com.michael.company.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.michael.company.dtos.requests.SaveCarRequest;
import com.michael.company.entities.Brand;
import com.michael.company.services.BrandService;
import com.michael.company.services.CarService;

import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {
  @Autowired
  private CarService carService;
  @Autowired
  private BrandService brandService;

  /*
   * Car Endpoints
   */
  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllCars() {
    return carService.getAllCars();
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Map<String, Object>> saveCar(@Valid @ModelAttribute SaveCarRequest request) {
    System.out.println("Request: " + request);
    return carService.createCar(request);
  }

  @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Map<String, Object>> updateCar(
      @PathVariable Long id,
      @Valid @ModelAttribute SaveCarRequest request) {
    request.setCarId(id);
    return carService.updateCar(request);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Object>> deleteCar(@PathVariable Long id) {
    return carService.deleteCar(id);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getCarById(@PathVariable Long id) {
    return carService.getCarById(id);
  }

  /*
   * Brand Endpoints
   */
  @GetMapping("/brands")
  public ResponseEntity<Map<String, Object>> getAllBrands() {
    return brandService.getAllBrands();
  }

  @PostMapping("/brands")
  public ResponseEntity<Map<String, Object>> saveBrand(@RequestBody Brand brand) {
    return brandService.saveBrand(brand);
  }

  @PutMapping("/brands/{id}")
  public ResponseEntity<Map<String, Object>> updateBrand(@PathVariable Long id, @RequestBody Brand brand) {
    brand.setId(id);
    return brandService.updateBrand(brand);
  }
}
