package co.com.asgard.core.controller;

import co.com.asgard.core.dto.ReportDTO;
import co.com.asgard.core.service.ReportService;
import co.com.asgard.core.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${app.api.base-path}")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports")
    public ResponseEntity<List<ReportDTO>> getAllReports(
            @RequestHeader(value = Constants.ID_USER) String idUser
    ) {

        System.out.println(idUser);
        System.out.println(Constants.APP);

        List<ReportDTO> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/pdf")
    public ResponseEntity<Resource> downloadPdfReport() {
        byte[] pdfBytes = reportService.generatePdfReport();
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @GetMapping("/excel")
    public ResponseEntity<Resource> downloadExcelReport() {
        byte[] excelBytes = reportService.generateExcelReport();
        ByteArrayResource resource = new ByteArrayResource(excelBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // o MediaType.APPLICATION_XLSX
                .body(resource);
    }
}