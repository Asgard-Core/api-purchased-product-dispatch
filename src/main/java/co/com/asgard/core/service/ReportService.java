package co.com.asgard.core.service;

import co.com.asgard.core.dto.ReportDTO;

import java.util.List;

public interface ReportService {
    List<ReportDTO> getAllReports();

    byte[] generatePdfReport();

    byte[] generateExcelReport();
}