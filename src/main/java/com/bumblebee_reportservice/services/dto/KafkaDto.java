package com.bumblebee_reportservice.services.dto;

import java.util.List;
import java.util.Map;

public class KafkaDto {
    private String cuid;
    private String containerName;
    private Boolean isAuthenticated;
    private ReportType reportType;
    private List<Map<String, List<String>>> data;

    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public Boolean getAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public List<Map<String, List<String>>> getData() {
        return data;
    }

    public void setData(List<Map<String, List<String>>> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "KafkaDto{" +
                "cuid='" + cuid + '\'' +
                ", containerName='" + containerName + '\'' +
                ", isAuthenticated=" + isAuthenticated +
                ", reportType=" + reportType +
                '}';
    }
}
