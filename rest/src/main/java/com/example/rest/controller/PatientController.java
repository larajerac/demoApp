package com.example.rest.controller;

import com.example.rest.entity.Patient;
import com.example.rest.service.PatientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PatientController {

    private PatientService patientService;

    PatientController(PatientService patientService) {this.patientService = patientService;}



    @ApiOperation(value = "Returns all patients.")
    @GetMapping("/patient")
    List<Patient> findAll() {

        return patientService.findAll();
    }

    @ApiOperation(value = "Returns a patient, if one with given id exists.")
    @GetMapping("/patient/{id}")
    Patient findById(@PathVariable Long id) {

        return patientService.findById(id);
    }

    @ApiOperation(value = "Creates or updates a patient if it exists.")
    @PostMapping("/patient")
    Patient getNewPatient(@Valid  @RequestBody Patient newPatient) {

        return patientService.createPatient(newPatient);
    }

    @ApiOperation(value = "Deletes a patient with given id.")
    @DeleteMapping("/patient/{id}")
    void deletePatient(@PathVariable Long id) {

        patientService.deletePatient(id);
    }

}
