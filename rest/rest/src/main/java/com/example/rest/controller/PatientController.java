package com.example.rest.controller;

import com.example.rest.entity.Disease;
import com.example.rest.entity.Document_report;
import com.example.rest.entity.Patient;
import com.example.rest.exeption.ExceptionMsg;
import com.example.rest.repository.DiseasRepository;
import com.example.rest.repository.Document_ReportRepository;
import com.example.rest.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class PatientController {

    private final PatientRepository repository;

    @Autowired
    private DiseasRepository diseasRepository;

    @Autowired
    private Document_ReportRepository document_ReportRepository;

    PatientController(PatientRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/patient")
    List<Patient> all() {
        return repository.findAll();
    }

    @GetMapping("/patient/{id}")
    Patient one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ExceptionMsg("No Patient with id " + id));
    }


    @PostMapping("/patient")
    Patient getNewPatient(@RequestBody Patient newPatient) {
        Document_report dr = new Document_report();
        dr.setEx_start(new Date());
        dr.setEntity("Patient");
        dr.setEntity_id(newPatient.getId());
        try {
            Patient saved = repository.save(patientForSave(newPatient, diseasRepository));
            document_ReportRepository.save(dr);
            return saved;

        } catch (Exception e) {
            dr.setError(e.getMessage());
            document_ReportRepository.save(dr);
            throw new ExceptionMsg(e.getMessage());
        }

    }

    @DeleteMapping("/patient/{id}")
    void deletePatient(@PathVariable Long id) {
        Document_report dr = new Document_report();
        dr.setEx_start(new Date());
        dr.setEntity("Doctor");
        dr.setEntity_id(id);

        try{
            repository.deleteById(id);
        } catch (Exception e) {
            dr.setError(e.getMessage());
            document_ReportRepository.save(dr);
            throw new ExceptionMsg(e.getMessage());
        }

    }

    public Patient patientForSave (Patient newPatient, DiseasRepository diseasRepository) {

        if (newPatient.getDiseases() != null
                && !newPatient.getDiseases().isEmpty()) {
            int counter = 0;
            for (Disease disease : newPatient.getDiseases()) {
                Disease d = diseasRepository.findByName(disease.getName());
                // če bolezen z danim imenom ne obstaja, jo ustvarimo
                if (d == null) {
                    d = diseasRepository.save(new Disease(disease.getName()));
                }

                //določimo id bolezni

                newPatient.getDiseases().get(counter).setId(d.getId());
                counter ++;
            }
        }
        return newPatient;
    }


}
