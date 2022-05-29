package com.example.rest.controller;

import com.example.rest.entity.Disease;
import com.example.rest.entity.Document_report;
import com.example.rest.exeption.ExceptionMsg;
import com.example.rest.repository.DiseasRepository;
import com.example.rest.repository.Document_ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class DiseasController {
    private final DiseasRepository repository;

    @Autowired
    private Document_ReportRepository document_ReportRepository;

    DiseasController(DiseasRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/disease")
    List<Disease> all() {
        return repository.findAll();
    }

    @GetMapping("/disease/{id}")
    Disease one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ExceptionMsg("No disease with id " + id));
    }

    @GetMapping("/diseaseByName/{name}")
    Disease one(@PathVariable String name) {

        return repository.findByName(name);
    }

    @PostMapping("/disease")
    Disease newDisease(@RequestBody Disease newDisease) {
        Document_report dr = new Document_report();
        dr.setEx_start(new Date());
        dr.setEntity("Disease");
        try{
            if (newDisease.getActive() == null) {
                // aktiviramo
                newDisease.setActive(true);
            }
            Disease saved = repository.save(newDisease);
            dr.setEntity_id(saved.getId());
            document_ReportRepository.save(dr);
            return saved;
        } catch (Exception e) {
            dr.setError(e.getMessage());
            document_ReportRepository.save(dr);
            throw new ExceptionMsg(e.getMessage());
        }
    }

    @DeleteMapping("/disease/{id}")
    void deleteDisease(@PathVariable Long id) {

        // samo deaktiviramo (soft delete)
        Document_report dr = new Document_report();
        dr.setEx_start(new Date());
        dr.setEntity("Disease");
        dr.setEntity_id(id);
        try {
            repository.findById(id)
                    .map(disease -> {
                        disease.setActive(false);
                        return repository.save(disease);
                    }).orElseThrow(() -> new ExceptionMsg("No disease with id " + id));
            document_ReportRepository.save(dr);
        }
        catch (Exception e) {
            dr.setError(e.getMessage());
            document_ReportRepository.save(dr);
            throw new ExceptionMsg(e.getMessage());
        }
    }

}




