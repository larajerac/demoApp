package com.example.rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Patient {

    @Id
    @NotNull
    private Long id;
    private String first_name;
    private String last_name;
    private List<Disease> diseases =  new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY,
            mappedBy = "patients")
    @JsonIgnore
    private List<Patient> patients;

    public Patient (Long id, String first_name, String last_name, List<Disease> diseases) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.diseases = diseases;
    }

    public Patient (Long l) {
        this.id = l;
    }

    public Patient () { }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @javax.persistence.Id
    public Long getId() {
        return id;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "patient_diseases",
            joinColumns = { @JoinColumn(name = "patient_id") },
            inverseJoinColumns = { @JoinColumn(name = "disease_id") })
    public List<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }
}