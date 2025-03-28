package com.michael.company.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.michael.company.entities.Client;
import com.michael.company.repositories.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ResponseEntity<Map<String, Object>> getAllClients() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Client> clients = clientRepository.findAll();
            response.put("message", "Clients retrieved successfully");
            response.put("data", clients);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error retrieving clients: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> getClientById(Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Client client = clientRepository.findById(id).orElse(null);
            if (client == null) {
                response.put("message", "Client not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.put("message", "Client retrieved successfully");
            response.put("data", client);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error retrieving client: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> saveClient(Client client) {
        Map<String, Object> response = new HashMap<>();
        try {
            Client savedClient = clientRepository.save(client);
            response.put("message", "Client saved successfully");
            response.put("data", savedClient);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error saving client: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> deleteClient(Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            clientRepository.deleteById(id);
            response.put("message", "Client deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error deleting client: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> updateClient(Long id, Client client) {
        Map<String, Object> response = new HashMap<>();
        try {
            Client existingClient = clientRepository.findById(id).orElse(null);
            if (existingClient == null) {
                response.put("message", "Client not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            client.setId(id);
            Client updatedClient = clientRepository.save(client);
            response.put("message", "Client updated successfully");
            response.put("data", updatedClient);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error updating client: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
