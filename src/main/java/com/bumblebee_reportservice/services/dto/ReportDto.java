package com.bumblebee_reportservice.services.dto;

public class ReportDto {

    private String type;
    private byte [] data;

    public void setType(String type) {
        this.type = type;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public byte[] getData() {
        return data;
    }
}
