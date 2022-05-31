package com.example.rest.service;

import com.example.rest.entity.Document_report;
import com.example.rest.repository.Document_ReportRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class Document_reportService {

    private final Document_ReportRepository repository;

    Document_reportService(Document_ReportRepository repository) {
        this.repository = repository;
    }

    public List<Document_report> findAll() {

        return repository.findAll();
    }

    public Document_report createDocuemntReport (String entity) {

        Document_report documentReport = new Document_report();
        documentReport.setEx_start(new Date());
        documentReport.setEntity(entity);

        return documentReport;
    }

    public  void save(Document_report documentReport) {

         repository.save(documentReport);
    }

    public void documentError(Document_report documentReport, String message) {

        documentReport.setError(message);
        save(documentReport);
    }
}
