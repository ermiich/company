package com.michael.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.michael.company.dtos.requests.CreateCarRequest;
import com.michael.company.services.MinioService;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @Autowired
    private MinioService minioService;

    @PostMapping
    public ResponseEntity<String> test(@RequestParam("file") MultipartFile file) {
        System.out.println("ARCHIVO:" + file.getOriginalFilename());
        minioService.uploadFile(file, "car-images");
        return ResponseEntity.ok("test");
    }
}
