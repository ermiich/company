package com.michael.company.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.michael.company.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
