
package com.example.rest.repository;

import com.example.rest.entity.Disease;
import com.example.rest.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> { }