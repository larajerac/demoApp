package com.example.rest;

import com.example.rest.entity.Disease;
import com.example.rest.service.DiseaseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DiseaseTest {

    @Autowired
    private DiseaseService service;

    @Autowired
    protected DiseaseTest(DiseaseService service) {
        super();
        this.service = service;
    }

    @Test
    public void saveDisease_thenGetOk() {

        Disease disease = new Disease("test1");
        service.save(disease);

        Disease disease1 = service.findByName("test1");
        Assertions.assertEquals("test1", disease1.getName());
    }

    @Test
    public void saveDisease_noStatus_getActive() {

        Disease disease = new Disease("test2");
        service.save(disease);

        Disease disease1 = service.findByName("test2");
        Assertions.assertEquals(true, disease1.getActive());
    }

    @Test
    public void deleteDisease_getDeactivated() {

        Disease disease = new Disease("test3");
        Disease savedDisease = service.save(disease);
        service.deleteDisease(savedDisease.getId());

        Disease disease1 = service.findByName("test3");
        Assertions.assertEquals(false, disease1.getActive());
    }

}


