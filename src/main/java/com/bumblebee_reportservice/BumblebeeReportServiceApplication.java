package com.bumblebee_reportservice;

import com.bumblebee_reportservice.repository.ReportRepository;
import com.bumblebee_reportservice.services.ReportService;
import com.bumblebee_reportservice.services.dto.KafkaDto;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = "com.bumblebee_reportservice")
public class BumblebeeReportServiceApplication {

    @Autowired
    private ReportService reportService;

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context= SpringApplication.run(BumblebeeReportServiceApplication.class, args);
        ReportRepository repository= context.getBean(ReportRepository.class);
        repository.getData("8f9");


    }

    @KafkaListener(topics = "reports_data", groupId = "1")
    public void listenReportsData(String message) {
        KafkaDto dto = new GsonBuilder().create().fromJson(message, KafkaDto.class);
        reportService.createReport(dto);
    }

}
