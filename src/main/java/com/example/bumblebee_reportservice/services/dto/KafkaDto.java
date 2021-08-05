package com.example.bumblebee_reportservice.services.dto;

import java.util.List;
import java.util.Map;

public class KafkaDto {
    private Long containerId;
    private String containerName;
    private Boolean isAuthenticated;
    private List<Map<String, List<String>>> data;

    public Long getContainerId() {
        return containerId;
    }

    public void setContainerId(Long containerId) {
        this.containerId = containerId;
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

    public List<Map<String, List<String>>> getData() {
        return data;
    }

    public void setData(List<Map<String, List<String>>> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "KafkaDto{" +
                "containerId=" + containerId +
                ", containerName='" + containerName + '\'' +
                ", isAuthenticated=" + isAuthenticated +
                '}';
    }
}
