package com.michael.company.dtos.requests;

import java.io.Serializable;
import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveCarsSoldRequest implements Serializable {
  private Long id; // Null para creación, con valor para actualización

  @NotNull(message = "Car ID is required")
  private Long carId; // ID del coche vendido

  @NotNull(message = "Employee ID is required")
  private Long employeeId; // ID del empleado que realizó la venta

  @NotNull(message = "Client ID is required")
  private Long clientId; // ID del cliente que compró el coche

  private Date soldAt = new Date(); // Fecha de la venta
}
