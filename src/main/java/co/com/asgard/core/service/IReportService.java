package co.com.asgard.core.service;

import co.com.asgard.core.dto.ReportDTO;
import co.com.asgard.core.dto.ReportRequestDTO;
import co.com.asgard.core.dto.ReportResponseDTO;

import java.util.List;

public interface IReportService {

    ReportResponseDTO generarReporte(ReportRequestDTO request);

    List<ReportDTO> getAllReports();

}