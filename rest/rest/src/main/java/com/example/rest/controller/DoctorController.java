package com.example.rest.controller;

import com.example.rest.entity.Doctor;
import com.example.rest.entity.Document_report;
import com.example.rest.entity.Patient;
import com.example.rest.exeption.ExceptionMsg;
import com.example.rest.repository.DiseasRepository;
import com.example.rest.repository.DoctorRepository;
import com.example.rest.repository.Document_ReportRepository;
import com.example.rest.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class DoctorController {

    private final DoctorRepository repository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DiseasRepository diseaseRepository;

    @Autowired
    private Document_ReportRepository document_ReportRepository;


    DoctorController(DoctorRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/doctor")
    List<Doctor> all() {
        return repository.findAll();
    }

    @PostMapping("/doctor")
    Doctor getNewDoctor(@RequestBody Doctor newDoctor) {

        Document_report dr = new Document_report();
        dr.setEx_start(new Date());
        dr.setEntity("Doctor");
        dr.setEntity_id(newDoctor.getId());

        try {

            // aktiviramo, če ni podano
            if (newDoctor.getActive() == null) {
                newDoctor.setActive(true);
            }
            if (newDoctor.getPatients() != null
                    && !newDoctor.getPatients().isEmpty()) {

                for (Patient patient : newDoctor.getPatients()) {

                    // če ni id-ja vrnemo napako
                    if (patient.getId() == null) {
                        throw new ExceptionMsg("Patients ID is mandatory");
                    }
                    // če pacienta ali bolezni ni, jih ustvarimo

                    Patient p = patientRepository.findById(patient.getId()).orElse(new Patient(-1L));

                    boolean newP = false;
                    if (p.getId() == -1) {
                        p = new Patient(patient.getId(), patient.getFirst_name(), patient.getLast_name(), patient.getDiseases());
                        newP = true;
                    }
                    p = new PatientController(patientRepository).patientForSave(p, diseaseRepository);
                    if (newP) {
                        patientRepository.save(p);
                    }
                }

                if (newDoctor.getActive() == null) {
                    newDoctor.setActive(true);
                }
            }
            Doctor saved = repository.save(newDoctor);
            document_ReportRepository.save(dr);
            return  saved;
        } catch (Exception e){
            dr.setError(e.getMessage());
            document_ReportRepository.save(dr);
            throw new ExceptionMsg(e.getMessage());
        }
    }

    @DeleteMapping("/doctor/{id}")
    void deleteDoctor(@PathVariable Long id) {

        Document_report dr = new Document_report();
        dr.setEx_start(new Date());
        dr.setEntity("Doctor");
        dr.setEntity_id(id);
        try {
            repository.findById(id)
                    .map(doctor -> {
                        doctor.setActive(false);
                        return repository.save(doctor);
                    }).orElseThrow(() -> new ExceptionMsg("No doctor with id " + id));
            document_ReportRepository.save(dr);
        } catch (Exception e) {
            dr.setError(e.getMessage());
            document_ReportRepository.save(dr);
            throw new ExceptionMsg(e.getMessage());
        }
    }
}
