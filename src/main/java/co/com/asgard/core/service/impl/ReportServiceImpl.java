package co.com.asgard.core.service.impl;

import co.com.asgard.core.dto.ReportDTO;
import co.com.asgard.core.dto.ReportRequestDTO;
import co.com.asgard.core.dto.ReportResponseDTO;
import co.com.asgard.core.model.Report;
import co.com.asgard.core.repository.ReportRepository;
import co.com.asgard.core.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements IReportService {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public ReportResponseDTO generarReporte(ReportRequestDTO request) {
        LocalDateTime fechaInicio = request.getFechaInicio().atStartOfDay();
        LocalDateTime fechaFin = request.getFechaFin().atTime(LocalTime.MAX);

        Long transportistaId = request.getTransportistaId();
        Long clienteId = request.getClienteId();
        String estadoPedido = (request.getEstadoPedido() != null && !request.getEstadoPedido().isEmpty())
                ? request.getEstadoPedido()
                : null;

        System.out.println("Fecha Inicio: " + fechaInicio);
        System.out.println("Fecha Fin: " + fechaFin);
        System.out.println("Transportista ID: " + transportistaId);
        System.out.println("Cliente ID: " + clienteId);
        System.out.println("Estado Pedido: " + estadoPedido);

        List<Report> reportes = reportRepository.findReports(fechaInicio, fechaFin, transportistaId, clienteId, estadoPedido);

        if (reportes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay registros disponibles para el período seleccionado.");
        }

        long entregasExitosas = reportes.stream().filter(r -> "Entregado".equalsIgnoreCase(r.getEstadoEntrega())).count();
        long pedidosRetrasados = reportes.stream().filter(r -> "Retrasado".equalsIgnoreCase(r.getEstadoEntrega())).count();
        int totalPedidos = reportes.size();
        double porcentajeEficiencia = totalPedidos > 0 ? (entregasExitosas * 100.0 / totalPedidos) : 0.0;
        String causaRetraso = pedidosRetrasados > 0 ? "Algunas entregas fueron retrasadas" : "Sin retrasos";

        return new ReportResponseDTO(totalPedidos, (int) entregasExitosas, (int) pedidosRetrasados, causaRetraso, porcentajeEficiencia);
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

}