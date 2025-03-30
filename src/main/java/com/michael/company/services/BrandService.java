package com.michael.company.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.michael.company.entities.Brand;
import com.michael.company.repositories.BrandRepository;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public ResponseEntity<Map<String, Object>> getAllBrands() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Brand> brands = brandRepository.findAll();
            response.put("message", "Brands retrieved successfully");
            response.put("data", brands);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error retrieving brands: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> getBrandById(Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Brand brand = brandRepository.findById(id).orElse(null);
            if (brand == null) {
                response.put("message", "Brand not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.put("message", "Brand retrieved successfully");
            response.put("data", brand);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error retrieving brand: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> saveBrand(Brand brand) {
        Map<String, Object> response = new HashMap<>();
        try {
            Brand savedBrand = brandRepository.save(brand);
            response.put("message", "Brand saved successfully");
            response.put("data", savedBrand);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error saving brand: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> deleteBrand(Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            brandRepository.deleteById(id);
            response.put("message", "Brand deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error deleting brand: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> updateBrand(Brand brand) {
        Map<String, Object> response = new HashMap<>();
        try {
            Brand existingBrand = brandRepository.findById(brand.getId()).orElse(null);
            if (existingBrand == null) {
                response.put("message", "Brand not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            existingBrand.setName(brand.getName());
            Brand updatedBrand = brandRepository.save(existingBrand);
            response.put("message", "Brand updated successfully");
            response.put("data", updatedBrand);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error updating brand: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
