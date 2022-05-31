package com.example.rest.service;

import com.example.rest.entity.Disease;
import com.example.rest.entity.Doctor;
import com.example.rest.entity.Document_report;
import com.example.rest.entity.Patient;
import com.example.rest.exeption.ExceptionMsg;
import com.example.rest.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository repository;

    DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private DiseaseService diseaseService;
    @Autowired
    private Document_reportService document_reportService;
    @Autowired
    private PatientService patientService;


    public List<Doctor> findAll() {

        List<Doctor> allDoctors = repository.findAll();
        return allDoctors.stream().filter(Doctor::getActive).toList();
    }

    public Doctor findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ExceptionMsg("No doctor with id " + id));
    }

    public Doctor save(Doctor doctor) {

        return repository.save(doctor);
    }

    public Doctor createDoctor(Doctor newDoctor) {

        Document_report documentReport = document_reportService.createDocuemntReport("Doctor");
        documentReport.setEntity_id(newDoctor.getId());

        try {

            if (newDoctor.getId() == -1) {
                throw new ExceptionMsg("Doctor ID is mandatory.");
            }


            if (newDoctor.getPatients() != null
                    && !newDoctor.getPatients().isEmpty()) {

                validData(newDoctor);

                for (Patient patient : newDoctor.getPatients()) {

                    // create or update patients
                    patientService.createPatient(patient);
                }

            }

            Doctor saved = repository.save(newDoctor);
            document_reportService.save(documentReport);

            return  saved;

        } catch (Exception e){

            document_reportService.documentError(documentReport, e.getMessage());
            throw new ExceptionMsg(e.getMessage());
        }
    }

    public void deleteDoctor(Long id) {

        Document_report documentReport = document_reportService.createDocuemntReport("Doctor");
        documentReport.setEntity_id(id);

        try {

            Doctor doctor = findById(id);
            doctor.setActive(false);
            save(doctor);

            document_reportService.save(documentReport);

        } catch (Exception e) {

            document_reportService.documentError(documentReport, e.getMessage());
            throw new ExceptionMsg(e.getMessage());
        }
    }

    public void validData (Doctor doctor) {
        List<Disease> allDiseases = new ArrayList<>();

        // check if all patients have id and diseases of patients exist
        for (Patient patient : doctor.getPatients()) {
            if (patient.getId() == -1) {
                throw new ExceptionMsg("All patients must have an ID.");
            }
            allDiseases.addAll(patient.getDiseases());
        }
        if (!diseaseService.allExist(allDiseases)) {
            throw new ExceptionMsg("At least one of the diseases does not exist.");
        }

    }

}
