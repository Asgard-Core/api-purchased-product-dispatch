package co.com.asgard.core.util;

import co.com.asgard.core.dto.ReportRequestDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ReportEXCELGenerator {

    public static byte[] generarExcel(ReportRequestDTO report) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reporte");

            // Encabezados del reporte
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Fecha Inicio", "Fecha Fin", "Transportista ID", "Cliente ID", "Estado Pedido"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(getHeaderStyle(workbook));
            }

            // Insertar los datos del request
            Row dataRow = sheet.createRow(1);
            dataRow.createCell(0).setCellValue(report.getFechaInicio().toString());
            dataRow.createCell(1).setCellValue(report.getFechaFin().toString());
            dataRow.createCell(2).setCellValue(report.getTransportistaId());
            dataRow.createCell(3).setCellValue(report.getClienteId());
            dataRow.createCell(4).setCellValue(report.getEstadoPedido());

            // Ajustar columnas automáticamente
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Convertir a un array de bytes
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                workbook.write(outputStream);
                return outputStream.toByteArray();
            }
        }
    }

    private static CellStyle getHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

}