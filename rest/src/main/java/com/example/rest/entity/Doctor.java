package com.example.rest.entity;

import com.sun.istack.NotNull;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Doctor {

    @Id
    @NotNull
    private Long id = -1L;

    private String first_name;

    private String last_name;
    @NotNull
    private Boolean active = true;

    @NotBlank
    private String department;

    public Doctor (Long l, String department) {
        this.id = l;
        this.department = department;
    }

    public Doctor () { }

    private List<Patient> patients =  new ArrayList<>();

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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @javax.persistence.Id
    public Long getId() {
        return id;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "doctor_patient",
            joinColumns = { @JoinColumn(name = "doctor_id") },
            inverseJoinColumns = { @JoinColumn(name = "patient_id") })
    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}