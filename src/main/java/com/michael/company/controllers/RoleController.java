package com.michael.company.controllers;

import com.michael.company.entities.Role;
import com.michael.company.services.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

  @Autowired
  private RoleService roleService;

  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllRoles() {
    return roleService.getAllRoles();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getRoleById(@PathVariable Long id) {
    return roleService.getRoleById(id);
  }

  @PostMapping
  public ResponseEntity<Map<String, Object>> saveRole(@RequestBody Role role) {
    return roleService.saveRole(role);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Object>> deleteRole(@PathVariable Long id) {
    return roleService.deleteRole(id);
  }
}