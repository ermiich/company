package com.michael.company.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.michael.company.entities.Employee;
import com.michael.company.entities.Role;
import com.michael.company.repositories.EmployeeRepository;
import com.michael.company.repositories.RoleRepository;
import com.michael.company.services.RoleService;

@Component
public class DataInitializer implements CommandLineRunner {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private RoleService roleService;

  @Override
  public void run(String... args) throws Exception {

    if (employeeRepository.count() > 0)
      return;

    Role jefeRole = new Role();
    jefeRole.setName("JEFE");
    roleService.saveRole(jefeRole);

    Employee admin = new Employee();
    admin.setName("Admin");
    admin.setEmail("admin@company.com");
    admin.setPhone("123456789");
    admin.setPassword(passwordEncoder.encode("admin123")); // Clave inicial
    admin.setRole(jefeRole);

    employeeRepository.save(admin);
    System.out.println("Administrador inicial creado: admin@company.com / admin123");

  }
}