package com.example.rest.service;

import com.example.rest.entity.Disease;
import com.example.rest.entity.Document_report;
import com.example.rest.exeption.ExceptionMsg;
import com.example.rest.repository.DiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiseaseService {

    private final DiseaseRepository repository;

    DiseaseService(DiseaseRepository repository) {
        this.repository = repository;
    }
    @Autowired
    private Document_reportService document_reportService;

    public List<Disease> findAll() {

        List<Disease> allDiseases = repository.findAll();

        return allDiseases.stream().filter(Disease::getActive).toList();
    }

    public Disease findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ExceptionMsg("No disease with id " + id));
    }

    public Disease findByName(String name) {

        return repository.findByName(name);
    }

    public Disease save(Disease disease) {

        return repository.save(disease);
    }

    public Disease createDisease(Disease newDisease) {

        Document_report documentReport = document_reportService.createDocuemntReport("Disease");

        try {

            if (findByName(newDisease.getName()) != null) {
                newDisease.setId(findByName(newDisease.getName()).getId());
            }
            Disease savedDisease = save(newDisease);
            documentReport.setEntity_id(savedDisease.getId());
            document_reportService.save(documentReport);

            return savedDisease;

        } catch (Exception e) {

            document_reportService.documentError(documentReport, e.getMessage());
            throw new ExceptionMsg(e.getMessage());
        }
    }

    public void deleteDisease(Long id) {

        Document_report documentReport = document_reportService.createDocuemntReport("Disease");
        documentReport.setEntity_id(id);

        try {

            Disease disease = findById(id);
            disease.setActive(false);
            save(disease);

            document_reportService.save(documentReport);

        } catch (Exception e) {

            document_reportService.documentError(documentReport, e.getMessage());
            throw new ExceptionMsg(e.getMessage());
        }
    }


    public Boolean allExist(List<Disease> diseases) {

        boolean exist = true;

        for (Disease disease : diseases) {

            Disease d = findByName(disease.getName());

            if (d == null || !d.getActive()) {
                exist = false;
                break;
            }
        }
        return exist;
    }

}
