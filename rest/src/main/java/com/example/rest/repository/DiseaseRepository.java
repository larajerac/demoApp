package com.example.rest.repository;

import com.example.rest.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    Disease findByName(String name);
}