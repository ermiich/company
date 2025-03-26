package com.michael.company.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.michael.company.entities.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

}
