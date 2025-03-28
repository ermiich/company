package com.michael.company.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.michael.company.entities.Brand;
import com.michael.company.services.BrandService;

@RestController
@RequestMapping("/api/v1/brands")
public class BrandController {
  @Autowired
  private BrandService brandService;

  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllBrands() {
    return brandService.getAllBrands();
  }

  @Postmapping
  public ResponseEntity<Map<String, Object>> saveBrand(@RequestBody Brand brand) {
    return brandService.saveBrand(brand);
  }
}
