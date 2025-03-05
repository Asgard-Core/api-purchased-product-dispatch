package co.com.asgard.core.controller;

import co.com.asgard.core.dto.ReportDTO;
import co.com.asgard.core.service.ReportService;
import co.com.asgard.core.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
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

    /*
    @GetMapping("/pdf")
    public ResponseEntity<Resource> downloadPdfReport() {
        Resource pdfReport = reportService.generatePdfReport();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
                .body(pdfReport);
    }

    @GetMapping("/excel")
    public ResponseEntity<Resource> downloadExcelReport() {
        Resource excelReport = reportService.generateExcelReport();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx")
                .body(excelReport);
    }
    */
}