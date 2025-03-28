package com.michael.company.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.michael.company.entities.CarsSold;
import com.michael.company.repositories.CarsSoldRepository;

@Service
public class CarsSoldService {

    @Autowired
    private CarsSoldRepository carsSoldRepository;

    public ResponseEntity<Map<String, Object>> getAllCarsSold() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<CarsSold> carsSoldList = carsSoldRepository.findAll();
            response.put("message", "Cars sold retrieved successfully");
            response.put("data", carsSoldList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error retrieving cars sold: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> getCarSoldById(Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            CarsSold carSold = carsSoldRepository.findById(id).orElse(null);
            if (carSold == null) {
                response.put("message", "Car sold not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.put("message", "Car sold retrieved successfully");
            response.put("data", carSold);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error retrieving car sold: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> saveCarSold(CarsSold carSold) {
        Map<String, Object> response = new HashMap<>();
        try {
            CarsSold savedCarSold = carsSoldRepository.save(carSold);
            response.put("message", "Car sold saved successfully");
            response.put("data", savedCarSold);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error saving car sold: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> deleteCarSold(Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            carsSoldRepository.deleteById(id);
            response.put("message", "Car sold deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error deleting car sold: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> getCarsSoldByClientId(Long clientId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<CarsSold> carsSoldList = carsSoldRepository.findByClientId(clientId);
            response.put("message", "Cars sold by client retrieved successfully");
            response.put("data", carsSoldList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error retrieving cars sold by client: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
