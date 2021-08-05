package com.example.bumblebee_reportservice.services;

import com.example.bumblebee_reportservice.services.dto.KafkaDto;
import com.example.bumblebee_reportservice.services.handlers.ReportHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final ReportHandler<List<Map<String, List<String>>>, byte[]> reportHandler;

    @Autowired
    public ReportService(ReportHandler<List<Map<String, List<String>>>, byte[]> reportHandler) {
        this.reportHandler = reportHandler;
    }

    public void createReport(KafkaDto dto) {
        byte[] rawData = reportHandler.buildReport(dto.getData());
        //todo сохранение данных в БД (Mongo)
    }

}
