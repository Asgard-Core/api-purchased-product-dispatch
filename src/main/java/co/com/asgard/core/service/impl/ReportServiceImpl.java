package co.com.asgard.core.service.impl;

import co.com.asgard.core.dto.ReportDTO;
import co.com.asgard.core.model.Report;
import co.com.asgard.core.repository.ReportRepository;
import co.com.asgard.core.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /*
    @Override
    public Resource generatePdfReport() {
        return new ByteArrayResource(new byte[0]);
    }

    @Override
    public Resource generateExcelReport() {
        return new ByteArrayResource(new byte[0]);
    }
    */
}