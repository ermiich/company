package com.michael.company.controllers;

import com.michael.company.dtos.requests.SaveClientRequest;
import com.michael.company.entities.Client;
import com.michael.company.services.ClientService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

  @Autowired
  private ClientService clientService;

  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllClients() {
    return clientService.getAllClients();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getClientById(@PathVariable Long id) {
    return clientService.getClientById(id);
  }

  @PostMapping
  public ResponseEntity<Map<String, Object>> saveClient(@RequestBody SaveClientRequest request) {
    return clientService.createClient(request);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Map<String, Object>> updateClient(
      @PathVariable Long id,
      @Valid @RequestBody SaveClientRequest request) {
    request.setId(id);
    return clientService.updateClient(request);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, Object>> deleteClient(@PathVariable Long id) {
    return clientService.deleteClient(id);
  }
}