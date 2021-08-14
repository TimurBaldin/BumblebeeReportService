package com.bumblebee_reportservice.services.dto;

import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Optional;

public enum ReportType {

    EXCEL_TYPE(".xlsx", "application/vnd.ms-excel"),
    DEFAULT_TYPE("text", MediaType.TEXT_PLAIN_VALUE);

    private final String format;
    private final String mediaType;

    ReportType(String format, String mediaType) {
        this.format = format;
        this.mediaType = mediaType;
    }

    public String getFormat() {
        return format;
    }

    public String getMediaType() {
        return mediaType;
    }

    public static String getFormatByMediaType(String mediaType) {
        Optional<ReportType> result = Arrays.stream(ReportType.values())
                .filter(s -> s.mediaType.equals(mediaType)).findFirst();
        if (result.isPresent()) {
            return result.get().getFormat();
        } else {
            return DEFAULT_TYPE.getFormat();
        }
    }
}
