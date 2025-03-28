package com.michael.company.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.michael.company.dtos.requests.SaveCarRequest;
import com.michael.company.entities.Brand;
import com.michael.company.entities.Car;
import com.michael.company.repositories.BrandRepository;
import com.michael.company.repositories.CarRepository;

import jakarta.transaction.Transactional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private MinioService minioService;

    @Value("${minio.bucketName.carImages}")
    private String bucketName;

    @Value("${minio.url}")
    private String minioUrl;

    private Car generateCarFromSaveRequest(SaveCarRequest request) {
        Brand brand = brandRepository.findById(request.getBrandId()).orElse(null);
        if (brand == null)
            return null;

        Car car = new Car();
        car.setBrand(brand);
        car.setModel(request.getModel());
        car.setYear(request.getYear());
        car.setPrice(request.getPrice());
        return car;
    }

    public ResponseEntity<Map<String, Object>> getAllCars() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Car> cars = carRepository.findAll();
            response.put("message", "Cars retrieved successfully");
            response.put("data", cars);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error retrieving cars");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> getCarById(Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Car car = carRepository.findById(id).orElse(null);
            if (car == null) {
                response.put("message", "Car not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response.put("message", "Car retrieved successfully");
            response.put("data", car);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error retrieving car");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> createCar(SaveCarRequest createCarRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Car car = generateCarFromSaveRequest(createCarRequest);
            if (car == null) {
                response.put("message", "Brand not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            String fileName = minioService.uploadFile(createCarRequest.getImage(), bucketName);

            car.setPhotoUrl("/" + bucketName + "/" + fileName);
            carRepository.save(car);
            response.put("message", "Car created successfully");
            response.put("data", car);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("message", "Error creating car");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<Map<String, Object>> updateCar(SaveCarRequest updateCarRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Car car = carRepository.findById(updateCarRequest.getCarId()).orElse(null);
            if (car == null) {
                response.put("message", "Car not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            car = generateCarFromSaveRequest(updateCarRequest);
            if (car == null) {
                response.put("message", "Brand not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            String fileName = minioService.uploadFile(updateCarRequest.getImage(), bucketName);

            car.setId(updateCarRequest.getCarId());
            car.setPhotoUrl("/" + bucketName + "/" + fileName);
            carRepository.save(car);

            response.put("message", "Car created successfully");
            response.put("data", car);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("message", "Error creating car");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Transactional
    public ResponseEntity<Map<String, Object>> deleteCar(Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Car car = carRepository.findById(id).orElse(null);
            if (car == null) {
                response.put("message", "Car not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            String fileName = car.getPhotoUrl().substring(car.getPhotoUrl().lastIndexOf("/") + 1);
            minioService.deleteFile(fileName, bucketName);
            carRepository.delete(car);
            response.put("message", "Car deleted successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error deleting car");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
