package com.michael.company.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.michael.company.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
}
