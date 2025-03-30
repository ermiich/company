package com.michael.company.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.michael.company.dtos.requests.SaveClientRequest;
import com.michael.company.entities.Client;
import com.michael.company.repositories.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    private Client generateClientFromSaveRequest(SaveClientRequest request) {
        if (request == null) {
            return null;
        }

        Client client = new Client();
        if (request.getId() != null) {
            client = clientRepository.findById(request.getId()).orElse(new Client());
        }

        client.setName(request.getName());
        client.setEmail(request.getEmail());
        client.setPhone(request.getPhone());
        client.setAddress(request.getAddress());

        return client;
    }

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

    public ResponseEntity<Map<String, Object>> createClient(SaveClientRequest createClientRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Client client = generateClientFromSaveRequest(createClientRequest);
            if (client == null) {
                response.put("message", "Invalid client data");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            clientRepository.save(client);
            response.put("message", "Client created successfully");
            response.put("data", client);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("message", "Error creating client");
            response.put("data", createClientRequest);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> updateClient(SaveClientRequest updateClientRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Client client = clientRepository.findById(updateClientRequest.getId()).orElse(null);
            if (client == null) {
                response.put("message", "Client not found");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            client = generateClientFromSaveRequest(updateClientRequest);
            if (client == null) {
                response.put("message", "Invalid client data");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            clientRepository.save(client);

            response.put("message", "Client updated successfully");
            response.put("data", client);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error updating client");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
