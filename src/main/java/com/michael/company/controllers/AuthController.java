package com.michael.company.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.michael.company.dtos.requests.AuthenticationRequest;
import com.michael.company.services.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("/login")
  public ResponseEntity<Map<String, Object>> login(@RequestBody AuthenticationRequest request) {
    return authenticationService.authenticate(request);
  }

}