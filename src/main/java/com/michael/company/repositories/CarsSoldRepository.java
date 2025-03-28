package com.michael.company.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.michael.company.entities.CarsSold;

public interface CarsSoldRepository extends JpaRepository<CarsSold, Long> {

  List<CarsSold> findByClientId(Long clientId);

}
