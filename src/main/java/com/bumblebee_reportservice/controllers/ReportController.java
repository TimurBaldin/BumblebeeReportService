package com.bumblebee_reportservice.controllers;

import com.bumblebee_reportservice.services.dto.ReportType;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/checkStatus")
public class ReportController {

    @GetMapping(path = "status/{id}/{name}")
    public String check(@PathVariable("id") String id, @PathVariable("name") String name) {
        //todo проверяем статус в БД
        return "";
    }

    @GetMapping(path = "/report")
    public ResponseEntity<ByteArrayResource> excelReport(@RequestParam ReportType type) {
        //todo берем данные из БД
        byte[] file = null;
        try {
            ByteArrayResource resource = getResource(file);
            return ResponseEntity.ok().contentLength(resource.contentLength())
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).
                    header("Content-disposition", "attachment; filename=" + LocalDateTime.now() + ".xlsx")
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ByteArrayResource getResource(byte[] object) throws IOException {
        return new ByteArrayResource(object);
    }

}
