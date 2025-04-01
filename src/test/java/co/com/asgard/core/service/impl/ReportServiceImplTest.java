package co.com.asgard.core.service.impl;

import co.com.asgard.core.config.LoggerPrinter;
import co.com.asgard.core.dto.ReportDTO;
import co.com.asgard.core.dto.ReportRequestDTO;
import co.com.asgard.core.dto.ReportResponseDTO;
import co.com.asgard.core.model.Report;
import co.com.asgard.core.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private LoggerPrinter loggerPrinter;

    @InjectMocks
    private ReportServiceImpl reportService;

    private ReportRequestDTO requestDTO;
    private Report report;

    @BeforeEach
    void setUp() {
        requestDTO = new ReportRequestDTO();
        requestDTO.setStartDate(LocalDate.of(2024, 1, 1));
        requestDTO.setEndDate(LocalDate.of(2024, 1, 31));
        requestDTO.setCarrierId(1L);
        requestDTO.setClientId(2L);
        requestDTO.setOrderStatus("Delivered");

        report = new Report();
        report.setId(1L);
        report.setDeliveryStatus("Delivered");
    }

    @Test
    void generateReport_ShouldReturnReportResponseDTO_WhenReportsAreFound() {
        when(reportRepository.findReports(any(), any(), any(), any(), any())).thenReturn(List.of(report));

        ReportResponseDTO response = reportService.generateReport(requestDTO);

        assertNotNull(response);
        assertEquals(1, response.getTotalPedidosDespachados());
        assertEquals(1, response.getEntregasExitosas());
        assertEquals(0, response.getPedidosRetrasados());
        assertEquals("No delays", response.getCausaRetraso());
        assertEquals(100.0, response.getPorcentajeEficiencia());
    }

    @Test
    void generateReport_ShouldThrowException_WhenNoReportsFound() {
        when(reportRepository.findReports(any(), any(), any(), any(), any())).thenReturn(Collections.emptyList());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                reportService.generateReport(requestDTO)
        );

        assertEquals("404 NOT_FOUND \"No records available for the selected period.\"", exception.getMessage());
    }

    @Test
    void getAllReports_ShouldReturnListOfReportDTO() {
        when(reportRepository.findAll()).thenReturn(List.of(report));

        List<ReportDTO> reports = reportService.getAllReports();

        assertNotNull(reports);
        assertEquals(1, reports.size());
        assertEquals(report.getId(), reports.get(0).getId());
    }
}