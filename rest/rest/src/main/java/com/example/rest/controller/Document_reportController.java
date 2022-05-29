package com.example.rest.controller;

import com.example.rest.entity.Disease;
import com.example.rest.entity.Document_report;
import com.example.rest.exeption.ExceptionMsg;
import com.example.rest.repository.DiseasRepository;
import com.example.rest.repository.Document_ReportRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Document_reportController {
    private final Document_ReportRepository repository;

    Document_reportController(Document_ReportRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/document_report")
    List<Document_report> all() {
        return repository.findAll();
    }

}




