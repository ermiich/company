package com.michael.company.controllers;

import com.michael.company.dtos.requests.SaveEmployeeRequest;
import com.michael.company.entities.Role;
import com.michael.company.services.EmployeeService;
import com.michael.company.services.RoleService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private RoleService roleService;

  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllEmployees() {
    return employeeService.getAllEmployees();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getEmployeeById(@PathVariable Long id) {
    return employeeService.getEmployeeById(id);
  }

  @PostMapping
  public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody SaveEmployeeRequest request) {

    return employeeService.createEmployee(request);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Map<String, Object>> updateEmployee(@PathVariable Long id,
      @RequestBody SaveEmployeeRequest request) {
    request.setId(id);
    System.out.println("======================");
    System.out.println("request " + request);
    System.out.println("=======================");
    return employeeService.updateEmployee(request);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Object>> deleteEmployee(@PathVariable Long id) {
    return employeeService.deleteEmployee(id);
  }

  /*
   * Roles
   */
  @GetMapping("/roles")
  public ResponseEntity<Map<String, Object>> getAllRoles() {
    return roleService.getAllRoles();
  }

  @GetMapping("/roles/{id}")
  public ResponseEntity<Map<String, Object>> getRoleById(@PathVariable Long id) {
    return roleService.getRoleById(id);
  }

  @PostMapping("/roles")
  public ResponseEntity<Map<String, Object>> saveRole(@RequestBody Role role) {
    return roleService.saveRole(role);
  }

  @DeleteMapping("/roles/{id}")
  public ResponseEntity<Map<String, Object>> deleteRole(@PathVariable Long id) {
    return roleService.deleteRole(id);
  }
}