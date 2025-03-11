package co.com.asgard.core.controller;

import co.com.asgard.core.dto.ReportDTO;
import co.com.asgard.core.dto.ReportRequestDTO;
import co.com.asgard.core.dto.ReportResponseDTO;
import co.com.asgard.core.service.IReportService;
import co.com.asgard.core.util.Constants;
import co.com.asgard.core.util.ReportEXCELGenerator;
import co.com.asgard.core.util.ReportPDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.api.base-path}")
public class ReportController {

    private final IReportService reportService;

    @Autowired
    public ReportController(IReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/generar")
    public ResponseEntity<ReportResponseDTO> generarReporte(
            @RequestBody ReportRequestDTO request,
            @RequestHeader(value = Constants.ID_USER) String idUser) {

        System.out.println("Usuario: " + idUser);
        ReportResponseDTO response = reportService.generarReporte(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reports")
    public ResponseEntity<List<ReportDTO>> getAllReports(@RequestHeader(value = Constants.ID_USER) String idUser) {

        System.out.println("Usuario: " + idUser);
        List<ReportDTO> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    @PostMapping("/exportar/pdf")
    public ResponseEntity<byte[]> exportReportPDF(@RequestBody ReportRequestDTO request) {
        try {
            ReportResponseDTO report = reportService.generarReporte(request);
            byte[] pdfBytes = ReportPDFGenerator.generarPDF(report);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte.pdf");
            headers.setContentType(MediaType.APPLICATION_PDF);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/exportar/excel")
    public ResponseEntity<byte[]> exportReportExcel(@RequestBody ReportRequestDTO request) {
        try {
            // Generar el archivo Excel con los datos del request
            byte[] excelBytes = ReportEXCELGenerator.generarExcel(request);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "reporte.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelBytes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}