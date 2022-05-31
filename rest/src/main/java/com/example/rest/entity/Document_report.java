package com.example.rest.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Document_report {

    private @Id
    @GeneratedValue Long id;
    private Date ex_start;
    private Long entity_id;
    private String entity;
    @Lob
    @Column
    private String error;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEx_start() {
        return ex_start;
    }

    public void setEx_start(Date ex_start) {
        this.ex_start = ex_start;
    }

    public Long getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(Long entity_id) {
        this.entity_id = entity_id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
