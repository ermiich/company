package com.michael.company.dtos.requests;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SaveEmployeeRequest implements Serializable {

  private Long id; // Null para creación, con valor para actualización

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Email is required")
  @Email(message = "Email must be valid")
  private String email;

  @NotBlank(message = "Phone is required")
  private String phone;

  private String password; // Solo requerido para creación

  @NotNull(message = "Role ID is required")
  private Long roleId;
}