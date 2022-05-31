package com.example.rest.controller;

import com.example.rest.entity.Document_report;
import com.example.rest.service.Document_reportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Document_reportController {

    private Document_reportService document_reportService;

    Document_reportController(Document_reportService document_reportService) {this.document_reportService = document_reportService;}

    @GetMapping("/document_report")
    List<Document_report> findAll() {

        return document_reportService.findAll();
    }

}




