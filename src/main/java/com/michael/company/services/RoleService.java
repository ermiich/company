package com.michael.company.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.michael.company.entities.Role;
import com.michael.company.repositories.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public ResponseEntity<Map<String, Object>> getAllRoles() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Role> roles = roleRepository.findAll();
            response.put("message", "Roles retrieved successfully");
            response.put("data", roles);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error retrieving roles: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> getRoleById(Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Role role = roleRepository.findById(id).orElse(null);
            if (role == null) {
                response.put("message", "Role not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.put("message", "Role retrieved successfully");
            response.put("data", role);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error retrieving role: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> saveRole(Role role) {
        Map<String, Object> response = new HashMap<>();
        try {
            Role savedRole = roleRepository.save(role);
            response.put("message", "Role saved successfully");
            response.put("data", savedRole);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error saving role: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> deleteRole(Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            roleRepository.deleteById(id);
            response.put("message", "Role deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error deleting role: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
