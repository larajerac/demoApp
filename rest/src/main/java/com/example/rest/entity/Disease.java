package com.example.rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public
class Disease {

    private @Id @GeneratedValue Long id;

    @NotBlank
    @Column(unique=true)
    private String name;

    @NotNull
    private Boolean active = true;

    @ManyToMany(fetch = FetchType.LAZY,
            mappedBy = "diseases")

    @JsonIgnore
    private List<Patient> patients_diseases;

    public Disease(String name, Boolean active) {

        this.name = name;
        this.active = active;
    }

    public Disease(String name) {

        this.name = name;
        this.active = true;
    }

    public Disease() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setId(Long id) { this.id = id; }

    public Long getId() { return id; }

}
