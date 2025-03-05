package co.com.asgard.core.dto;

import java.time.LocalDateTime;

public class ReportDTO {

    private Long id;
    private String transportista;
    private String cliente;
    private LocalDateTime fechaDespacho;
    private String estadoEntrega;
    private String destino;
    private String detalles;

    // Constructor vacío
    public ReportDTO() {}

    // Constructor con parámetros
    public ReportDTO(Long id, String transportista, String cliente, LocalDateTime fechaDespacho, String estadoEntrega, String destino, String detalles) {
        this.id = id;
        this.transportista = transportista;
        this.cliente = cliente;
        this.fechaDespacho = fechaDespacho;
        this.estadoEntrega = estadoEntrega;
        this.destino = destino;
        this.detalles = detalles;
    }

    // Getters y Setters manuales
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransportista() {
        return transportista;
    }

    public void setTransportista(String transportista) {
        this.transportista = transportista;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getFechaDespacho() {
        return fechaDespacho;
    }

    public void setFechaDespacho(LocalDateTime fechaDespacho) {
        this.fechaDespacho = fechaDespacho;
    }

    public String getEstadoEntrega() {
        return estadoEntrega;
    }

    public void setEstadoEntrega(String estadoEntrega) {
        this.estadoEntrega = estadoEntrega;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}