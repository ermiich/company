package com.michael.company.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.michael.company.dtos.requests.SaveCarRequest;
import com.michael.company.dtos.requests.SaveEmployeeRequest;
import com.michael.company.entities.Brand;
import com.michael.company.entities.Car;
import com.michael.company.entities.Employee;
import com.michael.company.entities.Role;
import com.michael.company.repositories.EmployeeRepository;
import com.michael.company.repositories.RoleRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Employee generateEmployeeFromSaveRequest(SaveEmployeeRequest request) {
        Role role = roleRepository.findById(request.getRoleId()).orElse(null);
        if (role == null)
            return null;

        Employee employee = new Employee();
        if (request.getId() != null) {
            employee = employeeRepository.getById(request.getId());
        }

        employee.setRole(role);
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());

        // Si hay contraseña en el request, la codificamos
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            employee.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return employee;
    }

    public ResponseEntity<Map<String, Object>> getAllEmployees() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Employee> employees = employeeRepository.findAll();
            response.put("data", employees);
            response.put("message", "Employees retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error retrieving employees: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> getEmployeeById(Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Employee> employee = employeeRepository.findById(id);
            if (employee.isPresent()) {
                response.put("data", employee.get());
                response.put("message", "Employee retrieved successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Employee not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("message", "Error retrieving employee: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> createEmployee(SaveEmployeeRequest createEmployeeRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Employee employee = generateEmployeeFromSaveRequest(createEmployeeRequest);
            if (employee == null) {
                response.put("message", "Role not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            employeeRepository.save(employee);
            response.put("message", "Employee created successfully");
            response.put("data", employee);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("message", "Error creating employee");
            response.put("data", createEmployeeRequest);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> updateEmployee(SaveEmployeeRequest updateEmployeeRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Employee employee = employeeRepository.findById(updateEmployeeRequest.getId()).orElse(null);
            if (employee == null) {
                response.put("message", "Employee not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            employee = generateEmployeeFromSaveRequest(updateEmployeeRequest);
            if (employee == null) {
                response.put("message", "Role not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            employeeRepository.save(employee);

            response.put("message", "Employee updated successfully");
            response.put("data", employee);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error updating employee");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> deleteEmployee(Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (!employeeRepository.existsById(id)) {
                response.put("message", "Employee not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            employeeRepository.deleteById(id);
            response.put("message", "Employee deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error deleting employee: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}