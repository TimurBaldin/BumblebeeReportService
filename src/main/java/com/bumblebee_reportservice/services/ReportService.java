package com.bumblebee_reportservice.services;

import com.bumblebee_reportservice.repository.ReportRepository;
import com.bumblebee_reportservice.services.dto.KafkaDto;
import com.bumblebee_reportservice.services.handlers.ReportHandler;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final ReportHandler<List<Map<String, List<String>>>, byte[]> reportHandler;
    private final ReportRepository repository;

    private static final String KEY_CONTAINER_ID = "containerId";
    private static final String KEY_CONTAINER_NAME = "containerName";

    @Autowired
    public ReportService(ReportHandler<List<Map<String, List<String>>>, byte[]> reportHandler,
                         ReportRepository repository) {
        this.reportHandler = reportHandler;
        this.repository = repository;
    }

    public void createReport(KafkaDto dto) {
        byte[] data = reportHandler.buildReport(dto.getData());

        DBObject metaData = new BasicDBObject();
        metaData.put(KEY_CONTAINER_ID, dto.getContainerId());
        metaData.put(KEY_CONTAINER_NAME, dto.getContainerName());

        repository.saveData(data, dto.getReportType(), metaData, getFileName(dto.getContainerName()));
    }

    private String getFileName(String fileName) {
        return fileName.concat("_").concat(LocalDate.now().toString());
    }
}
