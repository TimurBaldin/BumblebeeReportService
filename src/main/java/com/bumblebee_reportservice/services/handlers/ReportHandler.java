package com.bumblebee_reportservice.services.handlers;

public interface ReportHandler<I, O> {

    O buildReport(I data);

}