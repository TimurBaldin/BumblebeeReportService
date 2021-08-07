package com.example.bumblebee_reportservice.repository;

import com.example.bumblebee_reportservice.services.dto.ReportType;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;

@Repository
public class ReportRepository {

    private final GridFsTemplate template;

    @Autowired
    public ReportRepository(GridFsTemplate template) {
        this.template = template;
    }

    public void saveData(byte[] stream, ReportType type, DBObject metaData, String fileName) {
        template.store(new ByteArrayInputStream(stream), fileName, type.getMediaType(), metaData);
    }

    public void getData() {
        //TODO Возвращаем ресурс по имени файла и его ContainerId
    }

}
