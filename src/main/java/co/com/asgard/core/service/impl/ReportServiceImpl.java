package co.com.asgard.core.service.impl;

import co.com.asgard.core.dto.ReportDTO;
import co.com.asgard.core.model.Report;
import co.com.asgard.core.repository.ReportRepository;
import co.com.asgard.core.service.ReportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<ReportDTO> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream().map(report ->
                new ReportDTO(
                        report.getId(),
                        report.getTransportista() != null ? report.getTransportista().getNombre() : "N/A",
                        report.getCliente() != null ? report.getCliente().getNombre() : "N/A",
                        report.getFechaDespacho(),
                        report.getEstadoEntrega(),
                        report.getDestino(),
                        report.getDetalles()
                )
        ).collect(Collectors.toList());
    }

    public byte[] generatePdfReport() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos); // Usa PdfWriter dentro del try-with-resources
            document.open();

            List<ReportDTO> reports = getAllReports();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (ReportDTO report : reports) {
                document.add(new Paragraph("ID: " + report.getId()));
                document.add(new Paragraph("Transportista: " + report.getTransportista()));
                document.add(new Paragraph("Cliente: " + report.getCliente()));
                document.add(new Paragraph("Fecha Despacho: " + report.getFechaDespacho().format(formatter)));
                document.add(new Paragraph("Estado Entrega: " + report.getEstadoEntrega()));
                document.add(new Paragraph("Destino: " + report.getDestino()));
                document.add(new Paragraph("Detalles: " + report.getDetalles()));
                document.add(new Paragraph("-----------------------------------"));
            }

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public byte[] generateExcelReport() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Reports");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Transportista");
            headerRow.createCell(2).setCellValue("Cliente");
            headerRow.createCell(3).setCellValue("Fecha Despacho");
            headerRow.createCell(4).setCellValue("Estado Entrega");
            headerRow.createCell(5).setCellValue("Destino");
            headerRow.createCell(6).setCellValue("Detalles");

            List<ReportDTO> reports = getAllReports();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            int rowNum = 1;
            for (ReportDTO report : reports) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(report.getId());
                row.createCell(1).setCellValue(report.getTransportista());
                row.createCell(2).setCellValue(report.getCliente());
                row.createCell(3).setCellValue(report.getFechaDespacho().format(formatter));
                row.createCell(4).setCellValue(report.getEstadoEntrega());
                row.createCell(5).setCellValue(report.getDestino());
                row.createCell(6).setCellValue(report.getDetalles());
            }

            workbook.write(baos);
            return baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}