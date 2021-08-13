package com.bumblebee_reportservice.services;

import com.bumblebee_reportservice.repository.ReportRepository;
import com.bumblebee_reportservice.services.dto.KafkaDto;
import com.bumblebee_reportservice.services.dto.ReportDto;
import com.bumblebee_reportservice.services.handlers.ReportHandler;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.bumblebee_reportservice.services.dto.MetaDataKey.KEY_AUTH_FLAG;
import static com.bumblebee_reportservice.services.dto.MetaDataKey.KEY_CONTAINER_NAME;

@Service
public class ReportService {

    private final ReportHandler<List<Map<String, List<String>>>, byte[]> reportHandler;
    private final ReportRepository repository;

    @Autowired
    public ReportService(ReportHandler<List<Map<String, List<String>>>, byte[]> reportHandler,
                         ReportRepository repository) {
        this.reportHandler = reportHandler;
        this.repository = repository;
    }

    public void createReport(KafkaDto dto) {
        byte[] data = reportHandler.buildReport(dto.getData());
        DBObject metaData = new BasicDBObject();
        metaData.put(KEY_AUTH_FLAG.getValue(), dto.getAuthenticated());
        metaData.put(KEY_CONTAINER_NAME.getValue(), dto.getContainerName());
        repository.saveData(data, dto.getReportType(), metaData, dto.getCuid());
    }

    public ReportDto getReportByCuid(String cuid) throws IOException {
        ReportDto dto = repository.getData(cuid);

        if(StringUtils.isEmpty(dto.getFileName())){
          // todo возвращаем исключение, что отчет не найден или не готов
        }
        if (!dto.isAuth()) {
            repository.deleteData(cuid);
        }
        return dto;
    }

    private String getFileName(String fileName) {
        //todo будет нужен при выгрузке
        return fileName.concat("_").concat(LocalDate.now().toString());
    }
}
