package com.michael.company.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.michael.company.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
