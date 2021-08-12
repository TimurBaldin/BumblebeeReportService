package com.bumblebee_reportservice.repository;

import com.bumblebee_reportservice.services.dto.ReportDto;
import com.bumblebee_reportservice.services.dto.ReportType;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

@Repository("reportRepository")
public class ReportRepository {

    private final GridFsTemplate template;
    private static final String QUERY_KEY_FILE = "filename";
    private static final String QUERY_KEY_METADATA_REPORT_TYPE = "_contentType";

    @Autowired
    public ReportRepository(GridFsTemplate template) {
        this.template = template;
    }

    public void saveData(byte[] stream, ReportType type, String fileName) {
        template.store(new ByteArrayInputStream(stream), fileName, type.getMediaType());
    }

    public ReportDto getData(String cuid) throws IOException {
        //todo добавить проверку на NPE
        ReportDto dto = new ReportDto();
        GridFSFile file = template.findOne(new Query(Criteria.where(QUERY_KEY_FILE).is(cuid)));
        dto.setType(String.valueOf(file.getMetadata().get(QUERY_KEY_METADATA_REPORT_TYPE)));
        dto.setData(template.getResource(file).getInputStream().readAllBytes());
        return dto;
    }

}
