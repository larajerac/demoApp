package com.example.rest.controller;

import com.example.rest.entity.Doctor;
import com.example.rest.service.DoctorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class DoctorController {

    private DoctorService doctorService;

    DoctorController(DoctorService doctorService) {this.doctorService = doctorService;}

    @ApiOperation(value = "Returns all active doctors.")
    @GetMapping("/doctor")
    List<Doctor> findAll() {

        return doctorService.findAll();
    }

    @ApiOperation(value = "Returns a doctor if, one with given id exists.")
    @GetMapping("/doctor/{id}")
    Doctor findById(@PathVariable Long id) {

        return doctorService.findById(id);
    }

    @ApiOperation(value = "Creates or updates a doctor if it exists.")
    @PostMapping("/doctor")
    Doctor createDoctor(@Valid @RequestBody Doctor newDoctor) {

        return doctorService.createDoctor(newDoctor);

    }

    @ApiOperation(value = "Deactivates doctor.")
    @DeleteMapping("/doctor/{id}")
    void deleteDoctor(@PathVariable Long id) {

         doctorService.deleteDoctor(id);
    }
}
