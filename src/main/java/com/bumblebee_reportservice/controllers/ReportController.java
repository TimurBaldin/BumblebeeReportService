package com.bumblebee_reportservice.controllers;

import com.bumblebee_reportservice.services.ReportService;
import com.bumblebee_reportservice.services.dto.ReportDto;
import com.bumblebee_reportservice.services.dto.ReportType;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(path = "/checkStatus")
@Api(value = "Controller for reports", tags = {"Controller for reports"})
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(path = "/report")
    public ResponseEntity<ByteArrayResource> excelReport(@RequestParam String cuid) throws IOException {
        ReportDto dto = reportService.getReportByCuid(cuid);
        ByteArrayResource resource = getResource(dto.getData());

        if (dto.getType().equals(ReportType.DEFAULT_TYPE.getMediaType())) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(dto.getType()))
                    .body(resource);
        }
        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType(dto.getType()))
                .header("Content-disposition", "attachment; filename=" + dto.getFileName()
                        .concat(ReportType.getFormatByMediaType(dto.getType())))
                .body(resource);

    }

    private ByteArrayResource getResource(byte[] object){
        return new ByteArrayResource(object);
    }
}
