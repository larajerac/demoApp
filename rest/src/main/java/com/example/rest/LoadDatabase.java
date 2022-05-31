package com.example.rest;

import com.example.rest.entity.Disease;
import com.example.rest.repository.DiseaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoadDatabase  implements CommandLineRunner {

    @Autowired
    private DiseaseRepository diseaseRepository;

    @Override
    public void run(String...args) throws Exception {
        List<Disease> diseases = new ArrayList<>();

        diseases.add(new Disease("nice_to_people"));
        diseases.add(new Disease("long_legs"));
        diseases.add(new Disease("used_to_have_dredds"));
        diseases.add(new Disease("chocaholic"));
        diseases.add(new Disease("great_haircut"));

        diseaseRepository.saveAll(diseases);
    }
}

