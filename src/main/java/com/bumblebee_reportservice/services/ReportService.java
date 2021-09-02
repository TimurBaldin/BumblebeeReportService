package com.bumblebee_reportservice.services;

import com.bumblebee_reportservice.repository.ReportRepository;
import com.bumblebee_reportservice.services.dto.KafkaDto;
import com.bumblebee_reportservice.services.dto.ReportDto;
import com.bumblebee_reportservice.services.dto.ReportType;
import com.bumblebee_reportservice.services.dto.TestDataDto;
import com.bumblebee_reportservice.services.handlers.ReportHandler;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.bumblebee_reportservice.services.dto.MetaDataKey.KEY_HISTORY_FLAG;
import static com.bumblebee_reportservice.services.dto.MetaDataKey.KEY_CONTAINER_NAME;

@Service
public class ReportService {

    private final ReportHandler<List<TestDataDto>, byte[]> reportHandler;
    private final ReportRepository repository;
    private final String DEFAULT_VALUE = "Report not found, please wait";
    private static final Logger log = LoggerFactory.getLogger(ReportService.class);

    @Autowired
    public ReportService(ReportHandler<List<TestDataDto>, byte[]> reportHandler,
                         ReportRepository repository) {
        this.reportHandler = reportHandler;
        this.repository = repository;
    }

    public void createReport(KafkaDto dto) {
        byte[] data;
        try {
            data = reportHandler.buildReport(dto.getData());
        } catch (IOException e) {
            log.error("Error generating a report for KafkaDto {} ", dto);
            return;
        }
        DBObject metaData = new BasicDBObject();
        metaData.put(KEY_HISTORY_FLAG.getValue(), dto.getHistoryOn());
        metaData.put(KEY_CONTAINER_NAME.getValue(), dto.getContainerName());
        repository.saveData(data, dto.getReportType(), metaData, dto.getCuid());
    }

    public ReportDto getReportByCuid(String cuid) throws IOException {
        ReportDto dto = repository.getData(cuid);
        if (dto.getData() == null) {
            dto.setData(DEFAULT_VALUE.getBytes());
            dto.setType(ReportType.DEFAULT_TYPE.getMediaType());
        } else if (!dto.isAuth()) {
            repository.deleteData(cuid);
        }
        return dto;
    }

}
