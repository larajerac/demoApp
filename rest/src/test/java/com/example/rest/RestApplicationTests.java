package com.example.rest;

import com.example.rest.controller.DiseaseController;
import com.example.rest.controller.DoctorController;
import com.example.rest.controller.Document_reportController;
import com.example.rest.controller.PatientController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RestApplicationTests {
	@Autowired
	private DiseaseController diseaseController;
	@Autowired
	private PatientController patientController;
	@Autowired
	private DoctorController doctorController;
	@Autowired
	private Document_reportController document_reportController;

	@Test
	void contextLoads() {
	}

	@Test
	public void contextLoadsDiseaseController() throws Exception {
		assertThat(diseaseController).isNotNull();
	}

	@Test
	public void contextLoadsPatientController() throws Exception {
		assertThat(patientController).isNotNull();
	}

	@Test
	public void contextLoadsDoctorController() throws Exception {
		assertThat(doctorController).isNotNull();
	}

	@Test
	public void contextLoadsDocument_reportController() throws Exception {
		assertThat(document_reportController).isNotNull();
	}

}
