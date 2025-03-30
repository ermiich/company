package com.michael.company.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.michael.company.dtos.requests.AuthenticationRequest;
import com.michael.company.entities.Employee;
import com.michael.company.entities.Role;
import com.michael.company.repositories.EmployeeRepository;
import com.michael.company.repositories.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthenticationService {

  @Autowired
  private JwtService jwtService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private CustomUserDetailsService userDetailsService;

  public ResponseEntity<Map<String, Object>> authenticate(AuthenticationRequest request) {
    Map<String, Object> response = new HashMap<>();

    try {
      // Autenticar al usuario
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

      // Obtener detalles del usuario
      UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

      // Generar token JWT
      String token = jwtService.generateToken(userDetails.getUsername());

      // Obtener información del empleado
      Employee employee = employeeRepository.findByEmail(request.getEmail())
          .orElseThrow(() -> new RuntimeException("Employee not found"));

      // Construir respuesta
      response.put("message", "Authentication successful");
      response.put("data", List.of(token, employee));
      return ResponseEntity.ok(response);

    } catch (AuthenticationException e) {
      response.put("message", "Authentication failed: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    } catch (Exception e) {
      response.put("message", "Error during authentication: " + e.getMessage());
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
