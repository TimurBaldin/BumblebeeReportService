package com.bumblebee_reportservice.services.dto;

public enum MetaDataKey {

    KEY_CONTAINER_NAME("containerName"),
    KEY_HISTORY_FLAG("history"),
    QUERY_KEY_FILE("filename"),
    QUERY_KEY_METADATA_REPORT_TYPE("_contentType");

    private final String value;

    MetaDataKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
