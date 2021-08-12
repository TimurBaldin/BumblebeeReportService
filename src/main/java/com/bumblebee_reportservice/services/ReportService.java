package com.bumblebee_reportservice.services;

import com.bumblebee_reportservice.repository.ReportRepository;
import com.bumblebee_reportservice.services.dto.KafkaDto;
import com.bumblebee_reportservice.services.handlers.ReportHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final ReportHandler<List<Map<String, List<String>>>, byte[]> reportHandler;
    private final ReportRepository repository;

    private static final String KEY_CONTAINER_NAME = "containerName";

    @Autowired
    public ReportService(ReportHandler<List<Map<String, List<String>>>, byte[]> reportHandler,
                         ReportRepository repository) {
        this.reportHandler = reportHandler;
        this.repository = repository;
    }

    public void createReport(KafkaDto dto) {
        byte[] data = reportHandler.buildReport(dto.getData());
        repository.saveData(data, dto.getReportType(), dto.getCuid());
    }

    public void getReportByCuid(String cuid){
        try {
            repository.getData(cuid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileName(String fileName) {
        //todo будет нужен при выгрузке
        return fileName.concat("_").concat(LocalDate.now().toString());
    }
}
