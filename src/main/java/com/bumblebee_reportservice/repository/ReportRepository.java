package com.bumblebee_reportservice.repository;

import com.bumblebee_reportservice.services.dto.ReportDto;
import com.bumblebee_reportservice.services.dto.ReportType;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import static com.bumblebee_reportservice.services.dto.MetaDataKey.*;

@Repository("reportRepository")
public class ReportRepository {

    private final GridFsTemplate template;

    @Autowired
    public ReportRepository(GridFsTemplate template) {
        this.template = template;
    }

    public void saveData(byte[] stream, ReportType type, DBObject metaData, String fileName) {
        template.store(new ByteArrayInputStream(stream), fileName, type.getMediaType(), metaData);
    }

    public ReportDto getData(String cuid) throws IOException {
        ReportDto dto = new ReportDto();
        Optional<GridFSFile> file = Optional.ofNullable(template.findOne(getQuery(cuid)));

        if (file.isPresent()) {
            dto.setType(file.get().getMetadata().get(QUERY_KEY_METADATA_REPORT_TYPE.getValue(), String.class));
            dto.setAuth(file.get().getMetadata().get(KEY_HISTORY_FLAG.getValue(), Boolean.class));
            dto.setFileName(file.get().getMetadata().get(KEY_CONTAINER_NAME.getValue(), String.class));
            dto.setData(template.getResource(file.get()).getInputStream().readAllBytes());
        }
        return dto;
    }

    public void deleteData(String cuid) {
        template.delete(getQuery(cuid));
    }

    private Query getQuery(String cuid) {
        return new Query(Criteria.where(QUERY_KEY_FILE.getValue()).is(cuid));
    }

}
