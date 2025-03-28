package com.michael.company.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.michael.company.entities.Employee;
import com.michael.company.repositories.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public ResponseEntity<Map<String, Object>> getAllEmployees() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Employee> employees = employeeRepository.findAll();
            response.put("message", "Employees retrieved successfully");
            response.put("data", employees);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error retrieving employees: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> getEmployeeById(Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Employee employee = employeeRepository.findById(id).orElse(null);
            if (employee == null) {
                response.put("message", "Employee not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.put("message", "Employee retrieved successfully");
            response.put("data", employee);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error retrieving employee: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> saveEmployee(Employee employee) {
        Map<String, Object> response = new HashMap<>();
        try {
            Employee savedEmployee = employeeRepository.save(employee);
            response.put("message", "Employee saved successfully");
            response.put("data", savedEmployee);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error saving employee: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> deleteEmployee(Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            employeeRepository.deleteById(id);
            response.put("message", "Employee deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error deleting employee: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
