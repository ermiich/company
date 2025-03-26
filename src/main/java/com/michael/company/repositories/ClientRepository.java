package com.michael.company.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.michael.company.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
