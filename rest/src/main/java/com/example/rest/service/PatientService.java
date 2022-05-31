package com.example.rest.service;

import com.example.rest.entity.Disease;
import com.example.rest.entity.Document_report;
import com.example.rest.entity.Patient;
import com.example.rest.exeption.ExceptionMsg;
import com.example.rest.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository repository;

    PatientService(PatientRepository repository) {
        this.repository = repository;
    }
    @Autowired
    private Document_reportService document_reportService;
    @Autowired
    private DiseaseService diseaseService;

    public List<Patient> findAll() {

        return repository.findAll();
    }

   public Patient findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ExceptionMsg("No Patient with id " + id));
    }

    public Patient createPatient(Patient newPatient) {

        Document_report documentReport = document_reportService.createDocuemntReport("Patient");
        documentReport.setEntity_id(newPatient.getId());

        try {

            if (newPatient.getId() == -1) {
                throw new ExceptionMsg("Patient ID is mandatory.");
            }


            int counter = 0;
            if (newPatient.getDiseases() != null
                    && !newPatient.getDiseases().isEmpty()) {

               if (!diseaseService.allExist(newPatient.getDiseases())) {
                   throw new ExceptionMsg("At least one of the diseases does not exist.");
               }

                for (Disease disease : newPatient.getDiseases()) {

                    Disease d = diseaseService.findByName(disease.getName());
                    // save disease id
                    newPatient.getDiseases().get(counter).setId(d.getId());
                    counter ++;
                }
            }

            Patient saved = repository.save(newPatient);
            document_reportService.save(documentReport);
            return saved;

        } catch (Exception e) {

            document_reportService.documentError(documentReport, e.getMessage());
            throw new ExceptionMsg(e.getMessage());
        }
    }

    public void deletePatient(Long id) {

        Document_report documentReport = document_reportService.createDocuemntReport("Patient");
        documentReport.setEntity_id(id);

        try{

            repository.deleteById(id);

        } catch (Exception e) {

            document_reportService.documentError(documentReport, e.getMessage());
            throw new ExceptionMsg(e.getMessage());
        }
    }

}
