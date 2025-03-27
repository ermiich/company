package com.michael.company.dtos.requests;

import lombok.Data;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateCarRequest implements Serializable {

    private String model;

    private MultipartFile image;

}
