package com.michael.company.dtos.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class SaveClientRequest implements Serializable {

  private Long id; // null para creación, con valor para actualización

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Email is required")
  @Email(message = "Email must be valid")
  private String email;

  @NotBlank(message = "Phone is required")
  private String phone;

  @NotBlank(message = "Address is required")
  private String address;
}