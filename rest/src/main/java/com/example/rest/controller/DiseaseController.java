package com.example.rest.controller;

import com.example.rest.entity.Disease;
import com.example.rest.service.DiseaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class DiseaseController {

    private DiseaseService diseaseService;

    DiseaseController(DiseaseService diseaseService) {this.diseaseService = diseaseService;}

    @ApiOperation(value = "Returns all active diseases.")
    @GetMapping("/disease")
    List<Disease> findAll() {

        return diseaseService.findAll();
    }

    @ApiOperation(value = "Returns a disease, if one with given id exists.")
    @GetMapping("/disease/{id}")
    Disease findById(@PathVariable Long id) {

        return diseaseService.findById(id);
    }

    @ApiOperation(value = "Returns a disease, if one with given name exists.")
    @GetMapping("/diseaseByName/{name}")
    Disease findByName(@PathVariable String name) {

        return diseaseService.findByName(name);
    }

    @ApiOperation(value = "Creates or updates entity if it exists.")
    @PostMapping("/disease")
    Disease createDisease(@Valid  @RequestBody Disease newDisease) {

        return diseaseService.createDisease(newDisease);
    }

    @ApiOperation(value = "Deactivates desease.")
    @DeleteMapping("/disease/{id}")
    void deleteDisease(@PathVariable Long id) {

        diseaseService.deleteDisease(id);
    }

}




