package com.michael.company.dtos.requests;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@ToString(exclude = "image") // Evita problemas al imprimir la imagen en logs
public class SaveCarRequest implements Serializable {

    private Long carId;

    @NotNull(message = "BrandId is required")
    private Long brandId;

    @NotBlank(message = "Model is required")
    private String model;

    @NotNull(message = "Year is required")
    @Min(value = 1900, message = "Year must be greater than or equal to 1900")
    private int year;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private double price;

    private MultipartFile image;

}
