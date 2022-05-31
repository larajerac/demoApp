package com.example.rest;

import com.example.rest.entity.Disease;
import com.example.rest.entity.Doctor;
import com.example.rest.entity.Patient;
import com.example.rest.service.DiseaseService;
import com.example.rest.service.DoctorService;
import com.example.rest.service.PatientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DoctorTest {

    @Autowired
    private DoctorService service;

    @Autowired
    private PatientService patientService;
    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    protected DoctorTest(DoctorService service) {
        super();
        this.service = service;
    }

    @Test
    public void saveDoctor_thenGetOkId() {

        Doctor doctor = new Doctor(1L, "test");
        service.createDoctor(doctor);

        Doctor doctor1 = service.findById(1L);
        Assertions.assertEquals(1L, doctor1.getId());
    }

    @Test
    public void saveDoctor_thenGetOkDep() {

        Doctor doctor = new Doctor(1L, "test");
        service.createDoctor(doctor);

        Doctor doctor1 = service.findById(1L);
        Assertions.assertEquals("test", doctor1.getDepartment());
    }


    @Test
    public void saveDoctorPatient_thenGetOkPatient() {

        Disease disease = new Disease("test5");

        List<Disease> diseases = new ArrayList<>();
        diseases.add(diseaseService.save(disease));

        Patient patient = new Patient(2L, "mike", "smith", diseases );

        Doctor doctor = new Doctor(3L, "test");

        List<Patient> patients = new ArrayList<>();
        patients.add(patient);

        doctor.setPatients(patients);
        service.createDoctor(doctor);

        Doctor doctor1 = service.findById(3L);
        Assertions.assertEquals(2L, doctor1.getPatients().get(0).getId());
    }

    @Test
    public void saveDoctorPatient_thenGetOkDisease() {

        Disease disease = new Disease("test4");

        List<Disease> diseases = new ArrayList<>();
        diseases.add(diseaseService.save(disease));

        Patient patient = new Patient(1L, "mike", "smith", diseases );

        Doctor doctor = new Doctor(1L, "test");

        List<Patient> patients = new ArrayList<>();
        patients.add(patient);

        doctor.setPatients(patients);
        service.createDoctor(doctor);

        Doctor doctor1 = service.findById(1L);
        Assertions.assertEquals("test4", doctor1.getPatients().get(0).getDiseases().get(0).getName());
    }

    @Test
    public void deleteDoctor_thenGetDeactivated() {

        Doctor doctor = new Doctor(1L, "test");
        service.createDoctor(doctor);
        service.deleteDoctor(1L);

        Doctor doctor1 = service.findById(1L);
        Assertions.assertEquals(false, doctor1.getActive());
    }


}
