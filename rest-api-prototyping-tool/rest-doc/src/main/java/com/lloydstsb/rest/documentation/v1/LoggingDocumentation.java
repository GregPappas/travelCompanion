package com.lloydstsb.rest.documentation.v1;

import com.lloydstsb.rest.v1.logging.SubmitCrashReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoggingDocumentation implements SubmitCrashReportService {
    public void submitApplicationCrashReports(List<String> applicationCrashReports) {
    }
}