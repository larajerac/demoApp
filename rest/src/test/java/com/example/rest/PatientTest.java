package com.example.rest;

import com.example.rest.entity.Disease;
import com.example.rest.entity.Patient;
import com.example.rest.service.DiseaseService;
import com.example.rest.service.PatientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PatientTest {

    @Autowired
    private PatientService service;

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    protected PatientTest(PatientService service) {
        super();
        this.service = service;
    }

    @Test
    public void savePatient_thenGetOkId() {

        Patient patient = new Patient(1L);
        service.createPatient(patient);

        Patient patient1 = service.findById(1L);
        Assertions.assertEquals(1L, patient1.getId());
    }

    @Test
    public void savePatient_thenGetOkName() {

        Patient patient = new Patient(1L, "mike", "smith", null );
        service.createPatient(patient);

        Patient patient1 = service.findById(1L);
        Assertions.assertEquals("mike", patient1.getFirst_name());
        Assertions.assertEquals("smith", patient1.getLast_name());
    }

    @Test
    public void savePatientDiesase_thenGetOkDisease() {

        Disease disease = new Disease("test");

        List<Disease> diseases = new ArrayList<>();
        diseases.add(diseaseService.save(disease));

        Patient patient = new Patient(1L, "mike", "smith", diseases );
        service.createPatient(patient);

        Patient patient1 = service.findById(1L);
        Assertions.assertEquals("test", patient1.getDiseases().get(0).getName());
    }


}
