package com.bumblebee_reportservice.services.handlers;

import java.io.IOException;

public interface ReportHandler<I, O> {

    O buildReport(I data) throws IOException;

}
