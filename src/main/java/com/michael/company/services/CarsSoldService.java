package com.michael.company.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.michael.company.dtos.requests.SaveCarsSoldRequest;
import com.michael.company.entities.Car;
import com.michael.company.entities.CarsSold;
import com.michael.company.entities.Client;
import com.michael.company.entities.Employee;
import com.michael.company.repositories.CarRepository;
import com.michael.company.repositories.CarsSoldRepository;
import com.michael.company.repositories.ClientRepository;
import com.michael.company.repositories.EmployeeRepository;

@Service
public class CarsSoldService {

    @Autowired
    private CarsSoldRepository carsSoldRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    private CarsSold generateCarsSoldFromSaveRequest(SaveCarsSoldRequest request) {
        Car car = carRepository.findById(request.getCarId()).orElse(null);

        Client client = clientRepository.findById(request.getClientId()).orElse(null);

        Employee employee = employeeRepository.findById(request.getEmployeeId()).orElse(null);

        // Crear o recuperar la entidad
        CarsSold carsSold = new CarsSold();
        if (request.getId() != null) {
            carsSold = carsSoldRepository.findById(request.getId()).orElse(new CarsSold());
        }

        // Establecer relaciones
        carsSold.setCar(car);
        carsSold.setClient(client);
        carsSold.setEmployee(employee);

        return carsSold;
    }

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

    public ResponseEntity<Map<String, Object>> createCarSold(SaveCarsSoldRequest createCarsSoldRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Car car = carRepository.findById(createCarsSoldRequest.getCarId()).orElse(null);
            if (car == null) {
                response.put("message", "Car not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            Employee employee = employeeRepository.findById(createCarsSoldRequest.getEmployeeId()).orElse(null);
            if (employee == null) {
                response.put("message", "Employee not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            Client client = clientRepository.findById(createCarsSoldRequest.getClientId()).orElse(null);
            if (client == null) {
                response.put("message", "Client not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            CarsSold carSold = generateCarsSoldFromSaveRequest(createCarsSoldRequest);
            if (carSold == null) {
                response.put("message", "Invalid car sold data - Related entities not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            carsSoldRepository.save(carSold);
            response.put("message", "Car sold record created successfully");
            response.put("data", carSold);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("message", "Error creating car sold record: " + e.getMessage());
            response.put("data", createCarsSoldRequest);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> updateCarSold(SaveCarsSoldRequest updateCarsSoldRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (updateCarsSoldRequest.getId() == null) {
                response.put("message", "Car sold ID is required for update");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            CarsSold existingCarSold = carsSoldRepository.findById(updateCarsSoldRequest.getId()).orElse(null);
            if (existingCarSold == null) {
                response.put("message", "Car sold record not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            CarsSold carSold = generateCarsSoldFromSaveRequest(updateCarsSoldRequest);
            if (carSold == null) {
                response.put("message", "Invalid car sold data - Related entities not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            carsSoldRepository.save(carSold);

            response.put("message", "Car sold record updated successfully");
            response.put("data", carSold);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error updating car sold record: " + e.getMessage());
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
